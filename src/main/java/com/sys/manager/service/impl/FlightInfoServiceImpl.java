package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.ShuttleInfo;
import com.sys.manager.mapper.FlightInfoMapper;
import com.sys.manager.entity.FlightInfo;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.FlightInfoService;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 航班信息表(FlightInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 13:33:59
 */
@Service
public class FlightInfoServiceImpl extends ServiceImpl<FlightInfoMapper, FlightInfo> implements FlightInfoService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private FlightInfoMapper flightInfoMapper;

    @Override
    public R<?> selectFlight(FlightInfo flightInfo, Integer page, Integer size) {
        IPage<FlightInfo> iPage = new Page<>(page, size);
        iPage = flightInfoMapper.selectFlight(iPage, flightInfo);
        return R.ok(iPage);
    }

    @Override
    public R<?> selectDict(String name) {
        LambdaQueryWrapper<FlightInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(name), FlightInfo::getFlightNum, name)
                .eq(FlightInfo::getDataStatus, "1");
        List<FlightInfo> list = flightInfoMapper.selectList(wrapper);
        return R.ok(list);
    }

    @Override
    public R<?> saveFlight(FlightInfo flightInfo) {
        Integer loginUserId = securityService.getLoginUserId();
        if (flightInfo.getId() == null) {
            flightInfo.setCreator(loginUserId);
            flightInfoMapper.insert(flightInfo);
        } else {
            flightInfo.setUpdater(loginUserId);
            flightInfoMapper.updateById(flightInfo);
        }
        return R.ok();
    }

    @Override
    public R<?> delFlight(Integer id) {
        FlightInfo flight = flightInfoMapper.selectById(id);
        Integer loginUserId = securityService.getLoginUserId();
        flight.setDataStatus(UUID.getUUID());
        flight.setUpdater(loginUserId);
        flightInfoMapper.updateById(flight);
        return R.ok();
    }
}

