package cc.mrbird.febs.code.mapper;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.Count;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author MrBird
 */
public interface CodeMapper extends BaseMapper<Code> {

    /**
     * 查询二维码列表
     * @return
     */
    IPage<Code> findCodeList(Page page,@Param("id")String id);

    /**
     * 按时间统计二维码
     * @param createTimeFrom
     * @param createTimeTo
     * @return
     */
    Count findCodeCount(@Param("startDate") String createTimeFrom, @Param("endDate") String createTimeTo);
}
