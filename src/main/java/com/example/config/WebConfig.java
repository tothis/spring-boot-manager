package com.example.config;

import com.example.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李磊
 */
@Configuration
public class WebConfig {
    @Bean
    FilterRegistrationBean<LogFilter> logFilterRegistrationBean(final LogFilter filter) {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        return registration;
    }
}
