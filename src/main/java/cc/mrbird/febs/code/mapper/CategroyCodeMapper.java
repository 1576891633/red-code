package cc.mrbird.febs.code.mapper;

import cc.mrbird.febs.code.entity.Code;
import cc.mrbird.febs.code.entity.CodeCategroy;
import cc.mrbird.febs.code.entity.Count;
import cc.mrbird.febs.wx.response.CodeResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author MrBird
 */
public interface CategroyCodeMapper extends BaseMapper<CodeCategroy> {

    /**
     * 查询二维码列表
     * @param code
     * @return
     */
    IPage<CodeCategroy> findCodeList(Page page, @Param("code") CodeCategroy code);

    /**
     * 按时间统计二维码
     * @param createTimeFrom
     * @param createTimeTo
     * @return
     */
    Count findCodeCount(@Param("startDate") String createTimeFrom, @Param("endDate") String createTimeTo);

    /**
     * 查询商品详情
     * @param cId
     * @return
     */
    CodeResponse findCategroyCode(@Param("cId") String cId);
}
