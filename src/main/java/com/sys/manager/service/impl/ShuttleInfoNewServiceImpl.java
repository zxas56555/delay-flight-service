package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AirportService;
import com.sys.manager.entity.AirportShuttle;
import com.sys.manager.entity.HotelInfo;
import com.sys.manager.entity.ShuttleInfoNew;
import com.sys.manager.mapper.*;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.ShuttleInfoNewService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShuttleInfoNewServiceImpl extends ServiceImpl<ShuttleInfoNewMapper, ShuttleInfoNew> implements ShuttleInfoNewService {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private ShuttleInfoNewMapper shuttleInfoNewMapper;

    @Autowired
    private AirportShuttleMapper airportShuttleMapper;

    @Autowired
    private AirportServiceMapper airportServiceMapper;

    @Autowired
    private PassengerInfoMapper passengerInfoMapper;

    @Autowired
    private AirportHotelMapper airportHotelMapper;

    @Override
    public R<?> selecetShuttleNew(ShuttleInfoNew shuttleInfoNew, Integer page, Integer pageSize) {
        IPage<ShuttleInfoNew> iPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<ShuttleInfoNew> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(shuttleInfoNew.getShuttleNo()), ShuttleInfoNew::getShuttleNo, shuttleInfoNew.getShuttleNo())
                .like(StringUtils.isNotEmpty(shuttleInfoNew.getName()), ShuttleInfoNew::getName, shuttleInfoNew.getName())
                .eq(shuttleInfoNew.getSeatNum() != null, ShuttleInfoNew::getSeatNum, shuttleInfoNew.getSeatNum())
                .like(StringUtils.isNotEmpty(shuttleInfoNew.getHeadPeople()), ShuttleInfoNew::getHeadPeople, shuttleInfoNew.getHeadPeople())
                .eq(ShuttleInfoNew::getDataStatus, "1");
        iPage = shuttleInfoNewMapper.selectPage(iPage, wrapper);
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> selectDictNew(String name) {
        LambdaQueryWrapper<ShuttleInfoNew> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotEmpty(name), ShuttleInfoNew::getName, name)
                .eq(ShuttleInfoNew::getDataStatus, "1");
        List<ShuttleInfoNew> list = shuttleInfoNewMapper.selectList(wrapper);
        return R.ok(list);
    }

    @Override
    public R<?> saveShuttleNew(ShuttleInfoNew shuttleInfoNew) {
        Integer loginUserId = securityService.getLoginUserId();
        if (shuttleInfoNew.getId() == null) {
            shuttleInfoNew.setCreator(loginUserId);
            shuttleInfoNewMapper.insert(shuttleInfoNew);
        } else {
            shuttleInfoNew.setUpdater(loginUserId);
            shuttleInfoNewMapper.updateById(shuttleInfoNew);
        }
        return R.ok();
    }

    @Override
    public R<?> delShuttleNew(Integer id) {
        ShuttleInfoNew shuttleInfoNew = shuttleInfoNewMapper.selectById(id);
        Integer loginUserId = securityService.getLoginUserId();
        shuttleInfoNew.setDataStatus(UUID.getUUID());
        shuttleInfoNew.setUpdater(loginUserId);
        shuttleInfoNewMapper.updateById(shuttleInfoNew);
        return R.ok();
    }

    @Override
    public R<?> isGoNew(AirportShuttle airShuttle) {
        Integer loginUserId = securityService.getLoginUserId();
        AirportShuttle airportShuttle = airportShuttleMapper.selectById(airShuttle.getId());
        airportShuttle.setIsGo(airShuttle.getIsGo());
        airportShuttle.setUpdater(loginUserId);
        airportShuttleMapper.updateById(airShuttle);
        return R.ok();
    }

    @Override
    public R<?> airShuttleInfoNew(Integer airShuttleId) {
        Map<String, Object> map = new HashMap<>();
        AirportShuttle airportShuttle = airportShuttleMapper.selectById(airShuttleId);
        Integer airportId = airportShuttle.getAirportId();
        Integer shuttleId = airportShuttle.getShuttleId();
        ShuttleInfoNew shuttleInfoNew = shuttleInfoNewMapper.selectById(shuttleId);
        AirportService airportService = airportServiceMapper.selectById(airportId);
        map.put("seatNum", shuttleInfoNew.getSeatNum());
        int count = passengerInfoMapper.selectPassengerCount(airportService.getFlightNum(), airportService.getServiceTime(),
                airportService.getFlightType(), shuttleId);
        map.put("residue", shuttleInfoNew.getSeatNum() - count);
        List<HotelInfo> hotels = airportHotelMapper.selectHotelByAirId(airportId);
        map.put("hotels", hotels);
        map.put("airport", "长沙黄花机场");
        map.put("isGo", airportShuttle.getIsGo());
        return R.ok(map);
    }
}