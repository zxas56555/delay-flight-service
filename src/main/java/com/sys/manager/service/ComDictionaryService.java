package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.ComDictionary;
import com.sys.manager.pojo.DictionaryByTypeParam;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
public interface ComDictionaryService extends IService<ComDictionary> {

    R<?> findDictionaryInfo(ComDictionary dictionary, Integer page, Integer size);

    R<?> findDictionaryListByType(DictionaryByTypeParam dictionaryByTypeParam);

    R<?> insertDictionary(ComDictionary dictionary);

    R<?> updateDictionary(ComDictionary dictionary);

    R<?> deleteDictionary(Integer id);

}
