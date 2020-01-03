package cc.mrbird.febs.code.service.impl;

import cc.mrbird.febs.code.config.IpConfiguration;
import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.mapper.CategroyCodeMapper;
import cc.mrbird.febs.code.mapper.CodeMapper;
import cc.mrbird.febs.code.service.ICategroyCodeService;
import cc.mrbird.febs.code.util.ThreadUtil;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.wx.response.CodeResponse;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CategroyCodeServiceImpl extends ServiceImpl<CategroyCodeMapper, CodeCategroy> implements ICategroyCodeService {

    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    IpConfiguration ip;
    private static final Double minReward = 0.3;

    /**
     * 通过分类ID查询商品分类
     *
     * @param cId
     * @return
     */
    @Override
    public CodeResponse findCategroyCode(String cId) {
        CodeResponse categroy = this.baseMapper.findCategroyCode(cId);
        if (categroy == null) {
            throw new FebsException("二维码cId不存在");
        }
        if (StrUtil.isNotEmpty(categroy.getUrl())) {
            //本地调试用这行代码
//            categroy.setUrl(ip.getIpAndPort() + "/download/image/" + categroy.getUrl());
            categroy.setUrl("http://47.112.38.218:8086/download/image/" + categroy.getUrl());
        }
        return categroy;
    }

    /**
     * 新增二维码
     *
     * @param code
     */
    @Override
    public void createCode(CodeCategroy code) {
        if (code.getReward().compareTo(new BigDecimal(minReward)) < 0) {
            throw new FebsException("奖金最小值为0.3元");
        }
        code.setId(String.valueOf(IdWorker.getId()));
        this.baseMapper.insert(code);
        //重新开启一个线程执行二维码插入
        ThreadUtil util = new ThreadUtil(code,codeMapper);
        util.start();
    }

    /**
     * 查询二维码列表
     *
     * @param code
     * @param request
     * @return
     */
    @Override
    public IPage<CodeCategroy> findCodeList(CodeCategroy code, QueryRequest request) {
        Page<CodeCategroy> page = new Page<>(request.getPageNum(), request.getPageSize());
//        SortUtil.handlePageSort(request, page, "create_date", FebsConstant.ORDER_DESC, false);
        IPage<CodeCategroy> codeList = this.baseMapper.findCodeList(page, code);
        for (CodeCategroy c : codeList.getRecords()) {
            if (c.getStatus() == null) {
                c.setPrintNumber(0);
            }
        }
        return codeList;
    }

    /**
     * 删除二维码
     *
     * @param codeIds
     */
    @Override
    public void deleteCodes(String[] codeIds) {
        //删除二维码分类表
        List<String> list = Arrays.asList(codeIds);
        this.removeByIds(list);
        //删除二维码表
        for (String str : list) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("categroy_id",str);
            codeMapper.delete(wrapper);
        }

    }

    /**
     * 查询二维码统计
     *
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> findCodeCount(Code code) {
        return null;
    }
}
