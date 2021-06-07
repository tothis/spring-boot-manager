package com.example.entity.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author 李磊
 */
@Data
public class BaseEntity implements Serializable {

    private Long id;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 修改者
     */
    @TableField(fill = FieldFill.UPDATE, insertStrategy = FieldStrategy.NEVER)
    private Long updateBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE, insertStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /**
     * 状态 0:正常 1:已删除
     */
    @TableField(fill = FieldFill.INSERT, select = false)
    private Byte state;
}
