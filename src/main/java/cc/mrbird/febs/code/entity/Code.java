package cc.mrbird.febs.code.entity;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

/**
 * @author MrBird
 */
@Data
@TableName("t_two_code")
@Excel("二维码表")
public class Code {

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    @TableId(value = "categroy_id")
    private String categroyId;

    /**
     * 创建时间
     */
    @TableField("create_date")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createDate;

    @TableField("status")
    private Integer status;


}
