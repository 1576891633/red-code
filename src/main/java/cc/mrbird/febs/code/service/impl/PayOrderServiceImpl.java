package cc.mrbird.febs.code.service.impl;

import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.entity.PayOrder;
import cc.mrbird.febs.code.mapper.PayOrderMapper;
import cc.mrbird.febs.code.service.IPayOrderService;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MrBird
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {


    /**
     * 插入微信支付记录
     * @param order
     */
    @Override
    public void insertOrder(PayOrder order) {
        this.baseMapper.insert(order);
    }

    /**
     * 后台查询红包领取记录
     * @param order
     * @param request
     * @return
     */
    @Override
    public IPage<PayOrder> findRecordList(PayOrder order, QueryRequest request) {
        Page<PayOrder> page = new Page<>(request.getPageNum(), request.getPageSize());
        return baseMapper.findRecordList(page,order);
    }
}
