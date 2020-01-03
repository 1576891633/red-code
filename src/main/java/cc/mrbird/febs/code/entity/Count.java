package cc.mrbird.febs.code.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author <a href="pigeon@kingyon.com">allen</a>
 * @datetime 2019/10/29
 */
@Data
public class Count {

    private Integer giveOut = 0;

    private BigDecimal receive = new BigDecimal(0);
}
