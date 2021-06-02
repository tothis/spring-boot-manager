package com.example.entity.common;

import com.example.exception.MessageType;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回数据
 *
 * @author 李磊
 */
@Getter
@Setter
public class Result<T> {

    private static final Result OK;

    static {
        final Result r = new Result<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        OK = r;
    }

    private long code;

    private String message;

    private T data;

    public static <T> Result<T> ok(final T data) {
        if (data == null) {
            return OK;
        }
        final Result<T> r = new Result<>();
        r.setCode(MessageType.OK.getCode());
        r.setMessage(MessageType.OK.getMessage());
        r.setData(data);
        return r;
    }
}
