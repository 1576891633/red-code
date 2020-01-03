package cc.mrbird.febs.code.controller;

import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.entity.PayOrder;
import cc.mrbird.febs.code.service.ICategroyCodeService;
import cc.mrbird.febs.code.service.IPayOrderService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author MrBird
 */
@Controller("codeView")
public class ViewController {

    @Autowired
    private ICategroyCodeService codeService;
    @Autowired
    private IPayOrderService orderService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "code/print")
    public String print() {
        return FebsUtil.view("code/count/print");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "code/code")
    public String layout() {
        return FebsUtil.view("code/code");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "code/count/code")
    public String list() {
        return FebsUtil.view("code/count/code");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "code/record/record")
    public String recordList() {
        return FebsUtil.view("code/record/record");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/record/detail/{id}")
    public String systemWorksDetail(@PathVariable String id, Model model) {
        PayOrder byId = orderService.getById(id);
        model.addAttribute("order", byId);
        return FebsUtil.view("code/record/recordDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "code/add")
    public String add() {
        return FebsUtil.view("code/codeAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "sys/code/update/{id}")
    @RequiresPermissions("code:update")
    public String systemUserUpdate(@PathVariable String id, Model model) {
        resolveUserModel(id, model, false);
        return FebsUtil.view("code/codeUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "sys/code/detail/{id}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String id, Model model) {
        resolveUserModel(id, model, true);
        return FebsUtil.view("code/codeDetail");
    }

    private void resolveUserModel(String id, Model model, Boolean transform) {
        CodeCategroy code = codeService.getById(id);
        model.addAttribute("code", code);
    }

}
