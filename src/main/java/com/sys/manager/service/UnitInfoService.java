package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.UnitInfo;

import java.util.Date;

/**
 * @author qmy
 * @since 2023-01-31
 */
public interface UnitInfoService extends IService<UnitInfo> {

    R<?> findUnitInfo(UnitInfo unitInfo, Integer page, Integer size, Date startTime, Date endTime);

    R<?> findUnitInfoTree();

    R<?> saveOrUpdateUnitInfo(UnitInfo unitInfo);

    R<?> deleteUnitInfo(Integer id);

    R<?> queryChildrenUnit(Integer unitId);

}
