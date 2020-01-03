package cc.mrbird.febs.code.entity;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author MrBird
 */
@Data
@TableName("t_code_categroy")
@Excel("二维码分类表")
public class CodeCategroy {

    /**
     * ID
     */
    @TableId(value = "id")
    @ApiModelProperty("二维码分类Id")
    private String id;

    /**
     * 创建时间
     */
    @TableField("create_date")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    @ApiModelProperty("二维码创建时间")
    private Date createDate;

    /**
     * 二维码名称
     */
    @TableField("name")
    @NotEmpty(message = "二维码名称不能为空")
    @ApiModelProperty("二维码名称")
    private String name;

    /**
     * 属性
     */
    @TableField("property")
    @ApiModelProperty("二维码属性")
    private String property;

    /**
     * 奖金
     */
    @TableField("reward")
    @NotNull(message = "二维码奖金不能为空")
    @ApiModelProperty("二维码奖金")
    private BigDecimal reward;

    /**
     * 内容
     */
    @TableField("content")
    @ApiModelProperty("二维码内容")
    private String content;

    @TableField("url")
    @ApiModelProperty("二维码图标地址")
    private String url;

    @TableField(exist = false)
    private String createTimeFrom;

    @TableField(exist = false)
    private String createTimeTo;

    @NotNull(message = "二维码个数不能为空")
    @ApiModelProperty("二维码个数")
    private Integer number;

    @TableField(exist = false)
    private Integer printNumber;

    @TableField(exist = false)
    private Integer status;


}
