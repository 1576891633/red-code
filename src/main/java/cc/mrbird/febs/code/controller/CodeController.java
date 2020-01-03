package cc.mrbird.febs.code.controller;

import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.entity.PayOrder;
import cc.mrbird.febs.code.service.ICategroyCodeService;
import cc.mrbird.febs.code.service.ICodeService;
import cc.mrbird.febs.code.service.IPayOrderService;
import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.annotation.Limit;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author <a href="pigeon@kingyon.com">allen</a>
 * @datetime 2019/10/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("code")
public class CodeController extends BaseController {


    @Autowired
    private ICategroyCodeService codeService;
    @Autowired
    private ICodeService service;
    @Autowired
    private IPayOrderService orderService;

    /**
     * 查询二维码分类列表
     * @param code
     * @param request
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("code:view")
    public FebsResponse userList(CodeCategroy code, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(codeService.findCodeList(code, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 查询二维码消费记录
     * @param request
     * @return
     */
    @GetMapping("record/list")
    @RequiresPermissions("code:view")
    public FebsResponse recordList(PayOrder order,QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(orderService.findRecordList(order, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 通过分类ID查询二维码列表
     * @param request
     * @return
     */
    @GetMapping("two/list")
    @RequiresPermissions("code:view")
    public FebsResponse twoCodeList(@RequestParam("id") String id, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(service.findCodeList(id, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 通过分类ID查询二维码列表不分页
     * @return
     */
    @GetMapping("two/list/nopage")
    @RequiresPermissions("code:view")
    public FebsResponse twoCodeListNoPage(@RequestParam("id") String id) {
        Map<String, Object> dataTable = service.findCodeListNoPage(id);
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("count/list")
//    @RequiresPermissions("code:view")
    public FebsResponse codeCount(CodeCategroy code) {
        Map<String, Object> dataTable = service.findCodeCount(code);
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("code:add")
    @Limit(key = "codeAdd", period = 3, count = 1, name = "新增二维码", prefix = "limit")
    @ControllerEndpoint(operation = "新增二维码", exceptionMessage = "新增二维码失败")
    public FebsResponse addUser(@Valid CodeCategroy code) {
        this.codeService.createCode(code);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{ids}")
    @RequiresPermissions("code:delete")
    @ControllerEndpoint(operation = "删除二维码", exceptionMessage = "删除二维码失败")
    public FebsResponse deleteUsers(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] codeIds = ids.split(StringPool.COMMA);
        this.codeService.deleteCodes(codeIds);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("code:update")
    @ControllerEndpoint(operation = "修改二维码", exceptionMessage = "修改二维码失败")
    public FebsResponse updateUser(CodeCategroy code) {
        if (code.getId() == null) {
            throw new FebsException("二维码ID为空");
        }
        this.codeService.updateById(code);
        return new FebsResponse().success();
    }

}
