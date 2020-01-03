package cc.mrbird.febs.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author MrBird
 */
@Data
@TableName("t_pay_order")
@Excel("微信支付订单表")
public class PayOrder {

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    @TableId(value = "open_id")
    private String openId;

    @TableId(value = "user_name")
    private String userName;

    @TableId(value = "code_id")
    private String codeId;

    @TableId(value = "reward")
    private BigDecimal reward;

    private Integer status;

    @TableField(exist = false)
    private String createTimeFrom;

    @TableField(exist = false)
    private String createTimeTo;

    @TableField(exist = false)
    private String codeName;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    @TableField("partner_trade_no")
    private String partnerTradeNo;



}
