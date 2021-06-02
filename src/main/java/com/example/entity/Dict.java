package com.example.entity;

import com.example.entity.common.BaseEntity;
import lombok.Data;

/**
 * 字典
 *
 * @author 李磊
 */
@Data
public class Dict extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 值
     */
    private String value;
}
