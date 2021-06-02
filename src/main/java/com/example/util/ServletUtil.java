package com.example.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李磊
 */
@UtilityClass
public class ServletUtil {

    public ServletRequestAttributes requestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public HttpServletRequest request() {
        return requestAttributes().getRequest();
    }

    public HttpServletResponse response() {
        return requestAttributes().getResponse();
    }
}
