package com.example.entity.vo.dict;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 李磊
 */
@AllArgsConstructor
@Data
public class DictSelectResponse {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;
}
