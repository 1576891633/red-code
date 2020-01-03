package cc.mrbird.febs.code.service;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.wx.response.CodeResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author MrBird
 */
public interface ICategroyCodeService extends IService<CodeCategroy> {

    /**
     * 查询二维码列表
     * @param code
     * @param request
     * @return
     */
    IPage<CodeCategroy> findCodeList(CodeCategroy code, QueryRequest request);

    /**
     * 新增二维码
     * @param code
     */
    void createCode(CodeCategroy code);

    /**
     * 删除二维码
     * @param codeIds
     */
    void deleteCodes(String[] codeIds);

    /**
     * 查询二维码统计
     * @param code
     * @return
     */
    Map<String, Object> findCodeCount(Code code);

    /**
     * 通过二维码ID查询商品分类
     * @param cId
     * @return
     */
    CodeResponse findCategroyCode(String cId);

}
