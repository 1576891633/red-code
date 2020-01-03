package cc.mrbird.febs.code.service.impl;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.entity.Count;
import cc.mrbird.febs.code.mapper.CodeMapper;
import cc.mrbird.febs.code.service.ICodeService;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author MrBird
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements ICodeService {

    /**
     * 查询二维码列表
     *
     * @param request
     * @return
     */
    @Override
    public IPage<Code> findCodeList(String id, QueryRequest request) {
        Page<Code> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "create_date", FebsConstant.ORDER_DESC, false);
        return this.baseMapper.findCodeList(page, id);
    }

    /**
     * 查询二维码统计
     *
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> findCodeCount(CodeCategroy code) {
        Count count = this.baseMapper.findCodeCount(code.getCreateTimeFrom(),code.getCreateTimeTo());
        List<Count> list = new ArrayList<>();
        list.add(count);
        Map<String, Object> map = new HashMap<>();
        map.put("rows",list);
        return map;
    }

    @Override
    public Map<String, Object> findCodeListNoPage(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("categroy_id",id);
        List list = this.baseMapper.selectList(wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("rows", list);
        data.put("total", list.size());
        return data;
    }

    /**
     * 更新二维码状态为失效
     *
     * @param cId
     */
    @Override
    public void updateStatus(String cId) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.set("status",1);
        wrapper.eq("id",cId);
        this.baseMapper.update(null,wrapper);
    }

    /**
     * 新增二维码
     *
     * @param code
     */
    @Override
    public void createCode(Code code) {
//        Integer number = code.getNumber();
//        for (int i = 0 ;i < number; i++) {
//            code.setId(String.valueOf(IdWorker.getId()));
//            code.setStatus(0);
//            save(code);
//        }
    }

    /**
     * 删除二维码
     *
     * @param codeIds
     */
    @Override
    public void deleteCodes(String[] codeIds) {
        List<String> list = Arrays.asList(codeIds);
        // 删除用户
        this.removeByIds(list);
    }
}
