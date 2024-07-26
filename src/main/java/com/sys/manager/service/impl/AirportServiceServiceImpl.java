package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.*;
import com.sys.manager.mapper.*;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.AirportServiceService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 接机/送机服务表(AirportService)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 11:48:21
 */
@Service
public class AirportServiceServiceImpl extends ServiceImpl<AirportServiceMapper, AirportService> implements AirportServiceService {

    @Autowired
    private AirportServiceMapper airportServiceMapper;

    @Autowired
    private ShuttleInfoMapper shuttleInfoMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Autowired
    private AirportShuttleMapper airportShuttleMapper;

    @Autowired
    private AirportHotelMapper airportHotelMapper;

    @Override
    public R<?> selectAll(AirportService airportService, Integer page, Integer size) {
        IPage<AirportService> iPage = new Page<>(page, size);
        List<String> shuttleIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(airportService.getShuttleIds())) {
            shuttleIds = Arrays.asList(airportService.getShuttleIds().split(","));
        }
        List<Integer> ids = airportServiceMapper.selectIds(airportService, shuttleIds);
        if (ids == null || ids.isEmpty()) {
            return R.ok(IPages.buildDataMap(new ArrayList(), 0));
        }
        iPage = airportServiceMapper.selectAll(iPage, ids);

        //摆渡车
        LambdaQueryWrapper<ShuttleInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShuttleInfo::getDataStatus, "1");
        List<ShuttleInfo> list = shuttleInfoMapper.selectList(wrapper);
        Map<Integer, ShuttleInfo> shuttleMap = new HashMap<>();
        for (ShuttleInfo shuttleInfo : list) {
            shuttleMap.put(shuttleInfo.getId(), shuttleInfo);
        }

        // 酒店
        LambdaQueryWrapper<HotelInfo> wrapper2 = Wrappers.lambdaQuery();
        wrapper2.eq(HotelInfo::getDataStatus, "1");
        List<HotelInfo> list2 = hotelInfoMapper.selectList(wrapper2);
        Map<Integer, HotelInfo> hotelMap = new HashMap<>();
        for (HotelInfo hotelInfo : list2) {
            hotelMap.put(hotelInfo.getId(), hotelInfo);
        }

        List<AirportService> records = iPage.getRecords();
        for (AirportService record : records) {
            if (StringUtils.isNotEmpty(record.getShuttleIds())) {
                List<AirportShuttle> airportShuttles = airportShuttleMapper.selectByAirId(record.getId());
                Map<Integer, String> collect = airportShuttles.stream().collect(Collectors.toMap(AirportShuttle::getShuttleId, AirportShuttle::getIsGo));
                List<String> shuttleNames = new ArrayList<>();
                List<ShuttleInfo> shuttleList = new ArrayList<>();
                String[] split = record.getShuttleIds().split(",");
                for (String shuttleId : split) {
                    ShuttleInfo shuttleInfo = shuttleMap.get(Integer.parseInt(shuttleId));
                    shuttleNames.add(shuttleInfo.getName());
                    shuttleInfo.setIsGo(collect.get(Integer.parseInt(shuttleId)));
                    shuttleList.add(shuttleInfo);
                }
                record.setShuttleNames(StringUtils.join(shuttleNames, ","));
                record.setShuttles(shuttleList);
            }
            if (StringUtils.isNotEmpty(record.getHotelIds())) {
                List<AirportHotel> airportHotels = airportHotelMapper.selectByAirId(record.getId());
                Map<Integer, Integer> collect = airportHotels.stream().collect(Collectors.toMap(AirportHotel::getHotelId, AirportHotel::getRoomNum));
                List<String> hotelNames = new ArrayList<>();
                List<HotelInfo> hotelList = new ArrayList<>();
                String[] split = record.getHotelIds().split(",");
                for (String hotelId : split) {
                    HotelInfo hotelInfo = hotelMap.get(Integer.parseInt(hotelId));
                    hotelNames.add(hotelInfo.getHotelName());
                    hotelInfo.setRoomNum(collect.get(Integer.parseInt(hotelId)));
                    hotelList.add(hotelInfo);
                }
                record.setHotelNames(StringUtils.join(hotelNames, ","));
                record.setHotels(hotelList);
            }
        }
        iPage.setRecords(records);
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> saveAirport(AirportService airportService) {
        Integer loginUserId = securityService.getLoginUserId();
        Integer id = airportService.getId();
        if (id == null) {
            airportService.setCreator(loginUserId);
            airportServiceMapper.insert(airportService);
            id = airportService.getId();
        } else {
            airportService.setUpdater(loginUserId);
            airportServiceMapper.updateById(airportService);
            //删除旧关联
            airportShuttleMapper.delByAirportId(id);
            airportHotelMapper.delByAirportId(id);
        }
        // 摆渡车
        String[] split = airportService.getShuttleIds().split(",");
        for (String shuttleId : split) {
            AirportShuttle airportShuttle = new AirportShuttle(id, Integer.parseInt(shuttleId), "0");
            airportShuttleMapper.insert(airportShuttle);
        }
        // 酒店
        List<HotelInfo> hotels = airportService.getHotels();
        for (HotelInfo hotelInfo : hotels) {
            AirportHotel airportHotel = new AirportHotel(id, hotelInfo.getId(), hotelInfo.getRoomNum());
            airportHotelMapper.insert(airportHotel);
        }
        return R.ok();
    }

    @Override
    public R<?> delAirport(Integer id) {
        Integer loginUserId = securityService.getLoginUserId();
        AirportService airportService = airportServiceMapper.selectById(id);
        airportService.setDataStatus(UUID.getUUID());
        airportService.setUpdater(loginUserId);
        airportServiceMapper.updateById(airportService);
        //删除关联
        airportShuttleMapper.delByAirportId(id);
        airportHotelMapper.delByAirportId(id);
        return R.ok();
    }

}

