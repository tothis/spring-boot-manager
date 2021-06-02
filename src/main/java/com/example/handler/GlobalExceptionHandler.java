package com.example.handler;

import com.example.entity.common.Result;
import com.example.exception.GlobalException;
import com.example.exception.MessageType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 异常捕获
 *
 * @author 李磊
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Result catchGlobalException(final GlobalException e) {
        final Result r = new Result();
        r.setCode(e.getCode());
        r.setMessage(e.getMessage());
        return r;
    }

    @ExceptionHandler(RuntimeException.class)
    public Result catchRuntimeException(final RuntimeException e) {
        e.printStackTrace();
        final Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }

    /**
     * 校验 controller 方法实体参数
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result paramException(final MethodArgumentNotValidException e) {
        final BindingResult result = e.getBindingResult();
        final StringBuilder message = new StringBuilder(MessageType.PARAMETER_ERROR.getMessage());
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> message.append(' ' + error.getField()
                + "->" + error.getDefaultMessage()));
        }
        final Result r = new Result();
        r.setCode(MessageType.PARAMETER_ERROR.getCode());
        r.setMessage(message.toString());
        return r;
    }

    /**
     * 校验 controller 方法非实体参数
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result paramException(final ConstraintViolationException e) {
        final StringBuilder message = new StringBuilder(MessageType.PARAMETER_ERROR.getMessage());
        final Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        constraintViolations.forEach(item -> message.append(item.getMessage()));

        final Result r = new Result();
        r.setCode(MessageType.PARAMETER_ERROR.getCode());
        r.setMessage(message.toString());
        return r;
    }

    @ExceptionHandler(Exception.class)
    public Result catchException(final Exception e) {
        e.printStackTrace();
        final Result r = new Result();
        r.setCode(MessageType.SYSTEM_ERROR.getCode());
        r.setMessage(MessageType.SYSTEM_ERROR.getMessage());
        return r;
    }
}
