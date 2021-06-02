package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dict;
import com.example.entity.vo.dict.DictListResponse;
import com.example.entity.vo.dict.DictSelectResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 李磊
 */
public interface DictMapper extends BaseMapper<Dict> {
    /**
     * 查询列表
     *
     * @return -
     */
    @Select("SELECT id, type, name, value FROM dict WHERE state = 0")
    List<DictListResponse> selectDictList();

    /**
     * 根据 type 获取字典
     *
     * @param type -
     * @return -
     */
    @Select("SELECT name, value FROM dict WHERE state = 0 and type = #{type}")
    List<DictSelectResponse> selectSelectList(String type);

    /**
     * 根据 type 和 value 获取 name
     *
     * @param type  -
     * @param value -
     * @return -
     */
    @Select("SELECT name FROM dict WHERE state = 0 and type = #{type} and value = #{value}")
    String selectName(@Param("type") String type, @Param("value") String value);
}
