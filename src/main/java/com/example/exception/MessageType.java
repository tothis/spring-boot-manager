package com.example.exception;

import lombok.Getter;

/**
 * 消息类型
 * <p>
 * 100_100_100 项目_模块_功能
 *
 * @author 李磊
 */
@Getter
public enum MessageType {

    OK(0, "请求成功"),
    SYSTEM_ERROR(-1, "系统错误"),
    PARAMETER_ERROR(-2, "参数错误");

    private final long code;
    private final String message;

    MessageType(final long code, final String message) {
        this.code = code;
        this.message = message;
    }
}
