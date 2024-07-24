package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.enums.ResultStatusEnum;
import com.sys.manager.mapper.ComDictionaryMapper;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.ComDictionaryService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.entity.ComDictionary;
import com.sys.manager.pojo.DictionaryByTypeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Service
public class ComDictionaryServiceImpl extends ServiceImpl<ComDictionaryMapper, ComDictionary> implements ComDictionaryService {

    @Autowired
    private ComDictionaryMapper comDictionaryMapper;

    @Autowired
    private SecurityService securityService;

    @Override
    public R<?> findDictionaryInfo(ComDictionary dictionary, Integer page, Integer size) {
        LambdaQueryWrapper<ComDictionary> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper
                .eq(dictionary.getId() != null, ComDictionary::getId, dictionary.getId())
                .eq(StringUtils.isNotBlank(dictionary.getCode()), ComDictionary::getCode, dictionary.getCode())
                .eq(StringUtils.isNotBlank(dictionary.getType()), ComDictionary::getType, dictionary.getType())
                .eq(dictionary.getPid() != null, ComDictionary::getPid, dictionary.getPid())
                .eq(ComDictionary::getStatus, "1")
                .isNotNull(ComDictionary::getCode);
        IPage<ComDictionary> iPage = new Page<>(page, size);
        iPage = this.baseMapper.selectPage(iPage, lambdaQueryWrapper);
        return R.ok(IPages.buildDataMap(iPage));
    }

    /**
     * 通过type查询字典
     *
     * @param dictionaryByTypeParam
     * @return
     */
    @Override
    public R<?> findDictionaryListByType(DictionaryByTypeParam dictionaryByTypeParam) {
        Map<String, List<ComDictionary>> resultMap = queryDictionary(dictionaryByTypeParam);
        return R.ok(resultMap);
    }

    /**
     * 根据条件查询字典表,供内部调用.
     *
     * @param dictionaryByTypeParam
     * @return
     */
    private Map<String, List<ComDictionary>> queryDictionary(DictionaryByTypeParam dictionaryByTypeParam) {
        Map<String, List<ComDictionary>> resultMap = new HashMap<>();
        List<String> typeList = new ArrayList<>();
        if (dictionaryByTypeParam.getList() == null || dictionaryByTypeParam.getList().size() <= 0) {
            typeList = comDictionaryMapper.queryAllType();
        } else {
            typeList = dictionaryByTypeParam.getList();
        }
        for (String dictType : typeList) {
            List<ComDictionary> queryList = new ArrayList<>();
            List<ComDictionary> otherList = new ArrayList<>();
            ComDictionary comDictionary = new ComDictionary();
            comDictionary.setType(dictType);
            comDictionary.setStatus("1");
            Wrapper<ComDictionary> wrapper = Wrappers.query(comDictionary);
            queryList = this.comDictionaryMapper.selectList(wrapper);
            changeTree(queryList,otherList);
            resultMap.put(dictType,otherList);
        }
        return resultMap;
    }

    private void changeTree(List<ComDictionary> queryList,List<ComDictionary> resultList) {
        // 先找到所有的一级菜单
        for (ComDictionary comDictionary : queryList) {
            // 一级菜单没有parentId
            if (comDictionary.getPid() == null || comDictionary.getPid() == 0) {
                resultList.add(comDictionary);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (ComDictionary comDictionary : resultList) {
            comDictionary.setPid(0);
            comDictionary.setChild(getChild(comDictionary.getId(), queryList));
        }
    }

    /**
     * 递归查找子菜单
     *
     *
     * @param id       当前菜单id
     * @param dictionaryList 要查找的列表
     * @return
     */
    private List<ComDictionary> getChild(Integer id, List<ComDictionary> dictionaryList) {
        // 子菜单
        List<ComDictionary> childList = new ArrayList<>();
        for (ComDictionary comDictionary : dictionaryList) {
            if (comDictionary.getPid() != null && comDictionary.getPid().equals(id)) {
                childList.add(comDictionary);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (ComDictionary comDictionary : childList) {
            // 直接递归
            comDictionary.setChild(getChild(comDictionary.getId(), dictionaryList));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public R<?> insertDictionary(ComDictionary dictionary) {
        Integer userId = securityService.getLoginUserId();
        dictionary.setCreator(userId);
        comDictionaryMapper.insert(dictionary);
        return R.okMsg("新增成功");
    }

    @Override
    public R<?> updateDictionary(ComDictionary dictionary) {
        try {
            Integer userId = securityService.getLoginUserId();
            dictionary.setUpdater(userId);
            comDictionaryMapper.updateById(dictionary);
            return R.okMsg("更新成功");
        } catch (Exception e) {
            return R.fail(ResultStatusEnum.UPDATE_FAIL);
        }
    }

    @Override
    public R<?> deleteDictionary(Integer id) {
        Integer adminId = securityService.getLoginUserId();
        if (comDictionaryMapper.updateStatusByIdAndPid(id, adminId) > 0) {
            return R.okMsg("更新成功");
        } else {
            return R.fail(700, "未找到该条数据的信息.");
        }
    }

}
