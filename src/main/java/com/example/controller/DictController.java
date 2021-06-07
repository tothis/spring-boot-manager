package com.example.controller;

import com.example.entity.common.PageQuery;
import com.example.entity.common.PageResult;
import com.example.entity.vo.dict.DictListResponse;
import com.example.entity.vo.dict.DictSaveRequest;
import com.example.entity.vo.dict.DictSelectResponse;
import com.example.service.DictService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 字典
 *
 * @author 李磊
 */
@RestController
@RequestMapping("dict")
public class DictController {

    private final DictService service;

    public DictController(final DictService service) {
        this.service = service;
    }

    /**
     * 保存
     *
     * @param r -
     */
    @PostMapping
    void save(@Valid @RequestBody final DictSaveRequest r) {
        service.save(r);
    }

    /**
     * 删除
     *
     * @param id -
     */
    @DeleteMapping("{id}")
    void delete(@PathVariable final long id) {
        service.removeById(id);
    }

    /**
     * 分页查询
     *
     * @param r -
     * @return -
     */
    @PostMapping("page")
    PageResult<DictListResponse> page(@Valid @RequestBody final PageQuery r) {
        return service.selectPage(r);
    }

    /**
     * 根据 type 获取字典
     *
     * @param type -
     * @return -
     */
    @GetMapping("select/{type}")
    List<DictSelectResponse> select(@PathVariable final String type) {
        return service.selectSelectList(type);
    }
}
