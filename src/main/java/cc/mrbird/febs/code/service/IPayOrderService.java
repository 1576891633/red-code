package cc.mrbird.febs.code.service;

import cc.mrbird.febs.code.entity.PayOrder;
import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author MrBird
 */
public interface IPayOrderService extends IService<PayOrder> {

    void insertOrder(PayOrder order);

    IPage<PayOrder> findRecordList(PayOrder order, QueryRequest request);
}
