package cc.mrbird.febs.upload.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author pengyi
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息
     */
    private String msg = RStatus.SUCCESS.getMessage();

    /**
     * 状态码
     */
    private int code = RStatus.SUCCESS.getValue();

    /**
     * 数据
     */
    private T data;

    public R() {
    }

    public R(RStatus status) {
        this.msg = status.getMessage();
        this.code = status.getValue();
    }

    public R(RStatus status, String msg) {
        this(status);
        this.msg = msg;
    }

    public R(RStatus status, T data) {
        this(status);
        this.data = data;
    }

    public R(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public R(int code, String msg, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public R(T data) {
        this.data = data;
    }

    public R<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public R<T> code(int code) {
        this.code = code;
        return this;
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }

    public R success() {
        this.msg = RStatus.SUCCESS.getMessage();
        this.code = RStatus.SUCCESS.getValue();
        return this;
    }

    public R error() {
        this.msg = RStatus.FAIL.getMessage();
        this.code = RStatus.FAIL.getValue();
        return this;
    }
}
