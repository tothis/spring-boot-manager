package com.example.entity.common;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 李磊
 */
@Data
public class PageQueryEntity {

    /**
     * 开始页数
     */
    @Min(value = 1, message = "开始页数必须大于0")
    @NotNull(message = "开始页数必填")
    private Long pageNum;

    /**
     * 每页数据数量
     */
    @Min(value = 1, message = "每页数据数量最小为1")
    @Max(value = 1000, message = "每页数据数量最大为1000")
    @NotNull(message = "每页数据数量必填")
    private Long pageSize;

    /**
     * 计算分页页数
     */
    public void calcPageNum() {
        // 计算数据库开始页数
        pageNum = (pageNum - 1) * pageSize;
    }
}
