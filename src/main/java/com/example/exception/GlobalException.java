package com.example.exception;

import lombok.Getter;

/**
 * 基础异常
 *
 * @author 李磊
 */
@Getter
public class GlobalException extends RuntimeException {

    private final long code;

    private final String message;

    public GlobalException(final MessageType type) {
        this.code = type.getCode();
        this.message = type.getMessage();
    }
}
