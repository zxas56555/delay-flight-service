package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.ComDictionary;
import com.sys.manager.pojo.DictionaryByTypeParam;
import com.sys.manager.service.ComDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@RestController
@RequestMapping("/api/comDictionary")
@Api(tags = "字典管理接口")
public class ComDictionaryController {

    @Autowired
    private ComDictionaryService dictionaryService;

    @GetMapping
    @ApiOperation(value = "查询字典", httpMethod = "GET")
    public R<?> findDictionaryInfo(ComDictionary dictionary,
                                   @RequestParam(required = false, defaultValue = "1") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return dictionaryService.findDictionaryInfo(dictionary, page, size);
    }

    @PostMapping("/queryByType")
    @ApiOperation(value = "通过type查询字典", httpMethod = "POST")
    public R<?> findDictionaryListByType(@RequestBody @Validated DictionaryByTypeParam dictionaryByTypeParam) {
        return dictionaryService.findDictionaryListByType(dictionaryByTypeParam);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增字典", httpMethod = "POST")
    public R<?> insertDictionary(@RequestBody @Validated ComDictionary dictionary) {
        return dictionaryService.insertDictionary(dictionary);
    }

    @PostMapping("/upd")
    @ApiOperation(value = "修改字典", httpMethod = "POST")
    public R<?> updateDictionary(@RequestBody ComDictionary dictionary) {
        return dictionaryService.updateDictionary(dictionary);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除字典", httpMethod = "POST")
    public R<?> deleteDictionary(@RequestParam Integer id) {
        return dictionaryService.deleteDictionary(id);
    }

}

