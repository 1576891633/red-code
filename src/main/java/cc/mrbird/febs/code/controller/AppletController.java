package cc.mrbird.febs.code.controller;

import cc.mrbird.febs.code.service.ICategroyCodeService;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.upload.entity.R;
import cc.mrbird.febs.wx.response.CodeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="pigeon@kingyon.com">allen</a>
 * @datetime 2019/10/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("wx")
@Api("公众号调用接口")
public class AppletController extends BaseController {


    @Autowired
    private ICategroyCodeService codeService;

    /**
     * 通过id查询某个商品详情
     * @param cId
     * @return
     */
    @ApiOperation(value = "通过id查询某个商品详情")
    @GetMapping("code/detail")
    public R<CodeResponse> codeList(@RequestParam @ApiParam("二维码id") String cId) {
        CodeResponse response = codeService.findCategroyCode(cId);
        return new R<>(response);
    }

}
