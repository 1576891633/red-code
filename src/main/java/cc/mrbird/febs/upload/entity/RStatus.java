package cc.mrbird.febs.upload.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * @author lizf
 * @date 2018-12-27
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RStatus implements EnumMessage {

    SUCCESS(200, "请求成功"),

    INTERNAL_SERVER_ERROR(500, "服务器异常"),

    FAIL(100, "请求失败"),

    TRADE_PARAM_ERROR(107, "参数异常");

    private Integer value;
    private String message;

    RStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

}
