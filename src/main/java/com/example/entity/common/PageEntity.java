package com.example.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页查询对象
 *
 * @author 李磊
 */
@Data
@AllArgsConstructor
public class PageEntity<T> {
    private final List<T> data;
    private final long count;
}
