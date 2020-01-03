package cc.mrbird.febs.wx;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.service.ICategroyCodeService;
import cc.mrbird.febs.code.service.ICodeService;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.wx.config.WxPayProperties;
import cc.mrbird.febs.wx.entity.BaseRestResponse;
import cc.mrbird.febs.wx.entity.ResultEntity;
import cc.mrbird.febs.wx.response.CodeResponse;
import cc.mrbird.febs.wx.service.WxService;
import cc.mrbird.febs.wx.util.CodeUtil;
import cc.mrbird.febs.wx.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * <pre>
 * @author Binary Wang
 */
@Slf4j
@RestController
@RequestMapping("/pay")
@Api("微信统一操作接口")
public class WxController {

    @Autowired
    private ICodeService service;

    @Autowired
    private WxPayProperties properties;

    @Autowired
    private WxService wxService;

    @Autowired
    private ICategroyCodeService codeService;


    @GetMapping("/loginUrl")
    @ApiOperation(value = "微信公众号授权登录")
    public String loginInit(@RequestParam @ApiParam("回调地址") String backUrl,
                            HttpServletRequest request,
                            HttpServletResponse response) throws UnsupportedEncodingException {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
//        backUrl = "http://192.168.0.22:8087/pay/weChatPayBackCall";
//        String backUrl = "http://code.kingyon.cn/goods.html";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + properties.getAppId() +
                "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8") +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        return "redirect:" + url;
    }

    @GetMapping("/reward")
    @ApiOperation(value = "企业付款到用户零钱")
    public FebsResponse payReward(HttpServletRequest request,
                                  @RequestParam @ApiParam("二维码Id") String cId,
                                                @RequestParam @ApiParam("openId") String openId,
                                                @RequestParam(required = false) @ApiParam("微信名称") String userName,
                                                @RequestParam(required = false) @ApiParam("佣金,最小值为0.3") String reward) {
        String ip = CodeUtil.getIpAddr(request);
        CodeResponse response = codeService.findCategroyCode(cId);
        //判断二维码是否失效
        Code code = service.getById(cId);
        if (code.getStatus() == 1) {
            throw new FebsException("二维码红包已领取");
        }
        ResultEntity iResult = wxService.payReward(cId,openId,response.getReward().toString(),ip,userName);

        return new FebsResponse().success().data(iResult);
    }


    /**
     * 获取access_token和openId
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/getToken")
    @ApiOperation(value = "获取access_token和openId")
    public BaseRestResponse<Map<String,Object>> getToken(@RequestParam String code){
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppId()+
                "&secret=" +properties.getSecret()+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = null;
        try {
            result = HttpClientUtil.send(url,null,null);
        } catch (Exception e) {
            log.error("发起网络请求错误");
            throw new FebsException("发起网络请求错误");
        }
        Map<String,Object> data = JSONObject.parseObject(result);
        return new BaseRestResponse<Map<String,Object>>().data(data);
    }

    /**
     * 获取用户基本信息
     *
     */
    @ApiOperation("获取用户基本信息")
    @RequestMapping(value = "/userInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public BaseRestResponse<Map<String,Object>> weChatPayBackCall(@RequestParam @ApiParam("code") String code) throws Exception {

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + properties.getAppId()+
                "&secret=" +properties.getSecret()+
                "&code=" +code+
                "&grant_type=authorization_code";
        //获取openId和accessToken
        String result = null;
        result = HttpClientUtil.send(url,null,null);
        System.out.println(result);
        Map<String,Object> data = JSONObject.parseObject(result);
        if (null != data.get("errcode") && data.get("errcode").toString().equals("40163")) {
            throw new FebsException("code已使用");
        }
        String openid=data.get("openid").toString();
        String accessToken=data.get("access_token").toString();
        //获取用户基本信息
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
        String userInfo = HttpClientUtil.send(userInfoUrl,null,null);
        Map<String,Object> userInfoData = JSONObject.parseObject(userInfo);
        userInfoData.put("access_token",accessToken);
        return new BaseRestResponse<Map<String,Object>>().data(userInfoData);
    }
    
}


