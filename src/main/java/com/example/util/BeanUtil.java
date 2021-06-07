package com.example.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.common.PageResult;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 */
@UtilityClass
public class BeanUtil extends BeanUtils {
    /**
     * 复制 List 实体
     *
     * @param list -
     * @param type -
     * @return -
     */
    @SneakyThrows
    public <A, B> List<B> convertList(final List<A> list, final Class<B> type) {
        final List<B> result = new ArrayList<>();
        for (A a : list) {
            final B response = type.newInstance();
            BeanUtils.copyProperties(a, response);
            result.add(response);
        }
        return result;
    }

    public <T> PageResult<T> toPage(final Page page, final Class<T> type) {
        final List<T> list = BeanUtil.convertList(page.getRecords(), type);
        return new PageResult<>(list, page.getTotal());
    }
}
