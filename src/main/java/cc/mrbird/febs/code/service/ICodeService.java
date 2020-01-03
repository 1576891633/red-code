package cc.mrbird.febs.code.service;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author MrBird
 */
public interface ICodeService extends IService<Code> {

    /**
     * 查询二维码列表
     * @param request
     * @return
     */
    IPage<Code> findCodeList(String id, QueryRequest request);

    /**
     * 新增二维码
     * @param code
     */
    void createCode(Code code);

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
    Map<String, Object> findCodeCount(CodeCategroy code);

    Map<String, Object> findCodeListNoPage(String id);

    /**
     * 更新二维码状态为失效
     * @param cId
     */
    void updateStatus(String cId);
}
