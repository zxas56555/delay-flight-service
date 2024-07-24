package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.mapper.UnitInfoMapper;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.UnitInfoService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.text.UUID;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.entity.UnitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qmy
 * @since 2023-01-31
 */
@Service
public class UnitInfoServiceImpl extends ServiceImpl<UnitInfoMapper, UnitInfo> implements UnitInfoService {

    @Autowired
    private UnitInfoMapper unitInfoMapper;

    @Autowired
    private SecurityService securityService;

    @Override
    public R<?> findUnitInfo(UnitInfo unitInfo, Integer page, Integer size, Date startTime, Date endTime) {
        LambdaQueryWrapper<UnitInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(unitInfo.getUnitId() != null, UnitInfo::getUnitId, unitInfo.getUnitId())
                .like(StringUtils.isNotEmpty(unitInfo.getUnitName()), UnitInfo::getUnitName, unitInfo.getUnitName())
                .eq(StringUtils.isNotEmpty(unitInfo.getRemark()), UnitInfo::getRemark, unitInfo.getRemark())
                .ge(startTime != null, UnitInfo::getSetupTime, startTime)
                .le(endTime != null, UnitInfo::getSetupTime, endTime)
                .like(StringUtils.isNotEmpty(unitInfo.getUnitName()), UnitInfo::getUnitName, unitInfo.getUnitName())
                .like(StringUtils.isNotEmpty(unitInfo.getContacts()), UnitInfo::getContacts, unitInfo.getContacts())
                .eq(StringUtils.isNotEmpty(unitInfo.getUnitNature()), UnitInfo::getUnitNature, unitInfo.getUnitNature())
                .eq(StringUtils.isNotEmpty(unitInfo.getUnitType()), UnitInfo::getUnitType, unitInfo.getUnitType())
                .like(StringUtils.isNotEmpty(unitInfo.getUnitCode()), UnitInfo::getUnitCode, unitInfo.getUnitCode())
                .eq(UnitInfo::getStatus, "1")
                .orderByAsc(UnitInfo::getDisplayOrder);

        IPage<UnitInfo> iPage = new Page<>(page, size);
        iPage = unitInfoMapper.selectPage(iPage, wrapper);
        if (size == -1) {
            return R.ok(IPages.buildDataMap(iPage.getRecords(), iPage.getRecords().size()));
        }
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> findUnitInfoTree() {
//        Integer unitId = securityService.getUnitId();
        List<UnitInfo> unitInfos = unitInfoMapper.selectChildrenByPid(0);
        UnitInfo unitInfo = unitInfos.get(0);
        getChildren(unitInfo);
        List<UnitInfo> tree = new ArrayList<>();
        tree.add(unitInfo);
        return R.ok(tree);
    }

    private void getChildren(UnitInfo unitInfo) {
        R<?> r = queryChildrenUnit(unitInfo.getUnitId());
        List<UnitInfo> childrenList = (List<UnitInfo>) r.getData();
        for (UnitInfo children : childrenList) {
            getChildren(children);
        }
        unitInfo.setChild(childrenList);
    }

    @Override
    public R<?> saveOrUpdateUnitInfo(UnitInfo unitInfo) {
        Integer userId = securityService.getLoginUserId();
        if (unitInfo.getUnitId() == null) {
            unitInfo.setCreator(userId);
            unitInfo.setCreateTime(new Date());
            unitInfoMapper.insert(unitInfo);
            return R.ok("新增成功");
        } else {
            unitInfo.setUpdater(userId);
            unitInfo.setUpdateTime(new Date());
            unitInfoMapper.updateById(unitInfo);
            return R.ok("更新成功");
        }
    }

    @Override
    public R<?> deleteUnitInfo(Integer id) {
        UnitInfo unitInfo = unitInfoMapper.selectById(id);
        unitInfo.setStatus(UUID.getUUID());
        unitInfoMapper.updateById(unitInfo);
        return R.ok("删除成功");
    }

    @Override
    public R<?> queryChildrenUnit(Integer unitId) {
        List<UnitInfo> children = unitInfoMapper.selectChildrenByPid(unitId);
        return R.ok(children);
    }

//    /**
//     * 查询该unit所有孩子节点id（包括子节点和孙子节点等。）
//     *
//     * @param unitId
//     * @return
//     */
//    private List<UnitInfo> queryAllChild(Integer unitId) {
//        Set<UnitInfo> categorySet = Sets.newHashSet();
//        findChildCategory(categorySet, unitId);
//        List<UnitInfo> categoryIdList = Lists.newArrayList();
//        if (unitId != null) {
//            categoryIdList.addAll(categorySet);
//        }
//        return categoryIdList;
//    }

    //递归算法,算出子节点
    private void findChildCategory(Set<UnitInfo> categorySet, Integer unitId) {
        UnitInfo category = unitInfoMapper.selectById(unitId);
        // 退出递归
        if (category != null) {
            categorySet.add(category);
        }
        List<UnitInfo> categoryList = unitInfoMapper.selectChildrenByPid(unitId);
        for (UnitInfo categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getUnitId());
        }
    }

}
