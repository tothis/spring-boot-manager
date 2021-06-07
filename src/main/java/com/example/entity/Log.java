package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志
 *
 * @author 李磊
 */
@Data
public class Log {

    private Long id;

    /**
     * 路径
     */
    private String path;

    /**
     * 方法
     */
    private String method;

    /**
     * 参数
     */
    private String param;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
