package com.example.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 李磊
 */
@Configuration
public class JacksonConfig {

    @Value("${spring.jackson.date-format}")
    private String pattern;

    @Bean
    Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        return builder -> {
            // JS 数字精度小于 Java long，返回数据时把long类型属性转为字符串类型Jackson。
            builder.serializerByType(long.class, ToStringSerializer.instance);
            // 基本类型和基本类型包子类型需分开设置
            builder.serializerByType(Long.class, ToStringSerializer.instance);

            // 配置 LocalDateTime
            builder.serializerByType(LocalDateTime.class
                , new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)));
            builder.deserializerByType(LocalDateTime.class
                , new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern)));
        };
    }
}
