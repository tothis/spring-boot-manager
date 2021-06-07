package com.example.filter;

import cn.hutool.core.io.IoUtil;
import com.example.entity.Log;
import com.example.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * 记录操作日志
 *
 * @author 李磊
 */
@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {

    private final LogMapper logMapper;

    public LogFilter(final LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    /**
     * 获取 body 参数
     */
    private static String bodyParam(HttpServletRequest request) throws IOException {
        try (final ServletInputStream in = request.getInputStream()) {
            return IoUtil.read(in, StandardCharsets.UTF_8);
        }
    }

    /**
     * 获取 form 表单参数
     */
    private static String formParam(HttpServletRequest request) {
        final StringBuilder builder = new StringBuilder();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final Set<String> keySet = parameterMap.keySet();
        for (String key : keySet) {
            final String[] values = parameterMap.get(key);
            for (final String value : values) {
                builder.append(key).append('=')
                    .append(value).append('&');
            }
        }
        final int length = builder.length();
        // 删除最后一个 & 字符
        return (length > 0 ? builder.deleteCharAt(length - 1) : builder).toString();
    }

    /**
     * 获取 multipart form 表单参数
     */
    private static String multipartFormParam(HttpServletRequest request) throws ServletException, IOException {
        final StringBuilder builder = new StringBuilder();
        for (final Part part : request.getParts()) {
            // 不记录文件参数
            if (part.getContentType() == null) {
                final long size = part.getSize();
                try (final InputStream in = part.getInputStream()) {
                    builder.append(part.getName()).append('=')
                        .append(IoUtil.read(in, StandardCharsets.UTF_8)).append('&');
                }
            }
        }
        final int length = builder.length();
        // 删除最后一个 & 字符
        return (length > 0 ? builder.deleteCharAt(length - 1) : builder).toString();
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response
        , final FilterChain chain) throws IOException, ServletException {

        final String type = request.getContentType();
        String param = null;
        if (type == null) {
            // URL 参数
            param = request.getQueryString();
            chain.doFilter(request, response);
        } else if (type.indexOf(MediaType.MULTIPART_FORM_DATA_VALUE) > -1) {
            param = multipartFormParam(request);
            chain.doFilter(request, response);
        } else {
            switch (type) {
                // form 表单参数
                case MediaType.APPLICATION_FORM_URLENCODED_VALUE:
                    param = formParam(request);
                    chain.doFilter(request, response);
                    break;
                // JSON 表单参数
                case MediaType.APPLICATION_JSON_VALUE:
                    final BodyServletRequestWrapper paramsRequest = new BodyServletRequestWrapper(request);
                    param = bodyParam(paramsRequest);
                    chain.doFilter(paramsRequest, response);
                    break;
                default:
                    break;
            }
        }

        final Log entity = new Log();
        entity.setMethod(request.getMethod());
        entity.setPath(request.getServletPath());
        entity.setParam(param);
        logMapper.insert(entity);
    }
}

class BodyServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyServletRequestWrapper(final HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        try (final ByteArrayInputStream in = new ByteArrayInputStream(body)) {
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(final ReadListener readListener) {
                }

                @Override
                public int read() {
                    return in.read();
                }
            };
        }
    }
}
