package cc.mrbird.febs.wx.response;

import cc.mrbird.febs.code.entity.CodeCategroy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MrBird
 */
@Data
public class CodeResponse extends CodeCategroy {

    @ApiModelProperty("二维码Id")
    private String cId;

    @ApiModelProperty("二维码状态：0未消耗1已消耗")
    private Integer status;


}
