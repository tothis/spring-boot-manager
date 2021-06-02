package com.example.entity.vo.dict;

import lombok.Data;

/**
 * @author 李磊
 */
@Data
public class DictListResponse {

    private long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;
}
