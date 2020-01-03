package cc.mrbird.febs.code.mapper;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.Count;
import cc.mrbird.febs.code.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author MrBird
 */
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    IPage<PayOrder> findRecordList(Page<PayOrder> page, @Param("order") PayOrder order);
}
