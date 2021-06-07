package com.example.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constant.RedisKeyConstant;
import com.example.entity.Dict;
import com.example.entity.common.PageQuery;
import com.example.entity.common.PageResult;
import com.example.entity.vo.dict.DictListResponse;
import com.example.entity.vo.dict.DictSaveRequest;
import com.example.entity.vo.dict.DictSelectResponse;
import com.example.mapper.DictMapper;
import com.example.util.BeanUtil;
import com.example.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    /**
     * 保存
     *
     * @param r -
     */
    public void save(final DictSaveRequest r) {
        final Dict entity = new Dict();
        BeanUtil.copyProperties(r, entity);

        final LambdaQueryWrapper<Dict> query = Wrappers.<Dict>lambdaQuery()
            .eq(Dict::getName, r.getName());
        Integer integer = baseMapper.selectCount(query);

        if (entity.getId() == null) {
            baseMapper.insert(entity);
        } else {
            baseMapper.updateById(entity);
        }
        // 保存至缓存
        RedisUtil.hashPut(RedisKeyConstant.DICT + entity.getType(), entity.getValue(), entity.getName());
    }

    /**
     * 分页查询
     *
     * @param r -
     * @return -
     */
    public PageResult<DictListResponse> selectPage(final PageQuery r) {
        final Page<Dict> page = baseMapper.selectPage(new Page<>(r.getPageNumber()
            , r.getPageSize()), null);
        return BeanUtil.toPage(page, DictListResponse.class);
    }

    /**
     * 根据 type 获取字典
     *
     * @param type -
     * @return -
     */
    public List<DictSelectResponse> selectSelectList(final String type) {
        final String dictKey = RedisKeyConstant.DICT + type;
        final Map<String, String> all = RedisUtil.hashGetAll(dictKey);
        final List<DictSelectResponse> result;
        if (MapUtil.isEmpty(all)) {
            result = baseMapper.selectSelectList(type);
            // 缓存
            for (final DictSelectResponse item : result) {
                RedisUtil.hashPut(dictKey, item.getValue(), item.getName());
            }
        } else {
            result = new ArrayList<>();
            for (final Map.Entry<String, String> entry : all.entrySet()) {
                result.add(new DictSelectResponse(entry.getValue(), entry.getKey()));
            }
        }
        return result;
    }

    /**
     * 根据 type 和 value 获取 name
     *
     * @param type  -
     * @param value -
     * @return -
     */
    public String selectName(final String type, final String value) {
        final String dictKey = RedisKeyConstant.DICT + type;
        String name = RedisUtil.hashGet(dictKey, value);
        if (StrUtil.isBlank(name)) {
            name = baseMapper.selectName(type, value);
            // 缓存数据
            RedisUtil.hashPut(dictKey, value, name);
        }
        return name;
    }

    /**
     * 删除
     *
     * @param id -
     */
    public void removeById(final long id) {
        final Dict entity = baseMapper.selectById(id);
        // 删除缓存
        RedisUtil.hashDelete(RedisKeyConstant.DICT + entity.getType()
            , entity.getValue());
        baseMapper.deleteById(id);
    }
}
