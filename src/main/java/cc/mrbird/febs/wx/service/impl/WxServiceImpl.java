package cc.mrbird.febs.wx.service.impl;

import cc.mrbird.febs.code.config.IpConfiguration;
import cc.mrbird.febs.code.entity.PayOrder;
import cc.mrbird.febs.code.service.ICodeService;
import cc.mrbird.febs.code.service.IPayOrderService;
import cc.mrbird.febs.wx.config.WxPayProperties;
import cc.mrbird.febs.wx.entity.ResultEntity;
import cc.mrbird.febs.wx.entity.TransfersDto;
import cc.mrbird.febs.wx.service.WxService;
import cc.mrbird.febs.wx.util.WechatpayUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author MrBird
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WxServiceImpl implements WxService {

    @Autowired
    private WxPayProperties properties;

    @Autowired
    private IpConfiguration ipConfiguration;

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private ICodeService service;

    private static final Double minReward = 0.3;

    @Override
    public ResultEntity payReward(String cId, String openId, String reward,String ip,String userName) {
//
//        if (NumberUtil.compare(minReward,Double.parseDouble(reward)) > 0) {
//            throw new FebsException("佣金最小值为0.3元");
//        }
        //创建订单
        PayOrder order = new PayOrder();
        order.setId(String.valueOf(IdWorker.getId()));
        order.setCodeId(cId);
        order.setUserName(userName);
        order.setOpenId(openId);
        order.setReward(new BigDecimal(reward));
        order.setStatus(1);
        payOrderService.insertOrder(order);

        // 微信商户秘钥, 根据实际情况填写
        String appkey = properties.getMchKey();
        // 微信商户证书路径, 根据实际情况填写
        String certPath = "/data/server/wxCert/apiclient_cert.p12";
//        String certPath = ClassLoader.getSystemResource("wxCert/apiclient_cert.p12").getPath();
        log.info("certPath:{}",certPath);
        // 微信接口请求参数, 根据实际情况填写
        TransfersDto model = new TransfersDto();
        // 申请商户号的appid或商户号绑定的appid
        model.setMch_appid(properties.getAppId());
        // 商户号
        model.setMchid(properties.getMchId());
        // 商户名称
        model.setMch_name("眉山市东坡区胃乐食品坊");
        // 商户appid下，某用户的openid
        model.setOpenid(openId);
        model.setAmount(Double.parseDouble(reward));
        model.setDesc("扫二维码领红包");
        model.setSpbill_create_ip(ip);
        // 微信官方API文档 https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
        ResultEntity iResult = WechatpayUtil.doTransfers(appkey, certPath, model);


        if (iResult.isSuccess()) {
            //修改二维码状态
            service.updateStatus(cId);
            //修改订单记录
            PayOrder updateOrder = new PayOrder();
            updateOrder.setId(order.getId());
            updateOrder.setPartnerTradeNo(model.getPartner_trade_no());
            updateOrder.setStatus(2);
            payOrderService.updateById(updateOrder);
        }
        return iResult;
    }
}
