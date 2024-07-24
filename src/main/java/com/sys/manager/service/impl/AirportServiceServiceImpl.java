package com.sys.manager.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.ShuttleInfo;
import com.sys.manager.mapper.AirportServiceMapper;
import com.sys.manager.entity.AirportService;
import com.sys.manager.mapper.ShuttleInfoMapper;
import com.sys.manager.pojo.HotelDetail;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.AirportServiceService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Override
    public R<?> selectAll(AirportService airportService, Integer page, Integer size) {
        IPage<AirportService> iPage = new Page<>(page, size);
        List<String> shuttleIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(airportService.getShuttleIds())) {
            shuttleIds = Arrays.asList(airportService.getShuttleIds().split(","));
        }
        iPage = airportServiceMapper.selectAll(iPage, airportService, shuttleIds);

        //摆渡车
        LambdaQueryWrapper<ShuttleInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShuttleInfo::getDataStatus, "1");
        List<ShuttleInfo> list = shuttleInfoMapper.selectList(wrapper);
        Map<Integer, String> shuttleMap = list.stream().collect(Collectors.toMap(ShuttleInfo::getId, ShuttleInfo::getName));

        List<AirportService> records = iPage.getRecords();
        for (AirportService record : records) {
            if (StringUtils.isNotEmpty(record.getShuttleIds())) {
                List<String> shuttleNames = new ArrayList<>();
                String[] split = record.getShuttleIds().split(",");
                for (String shuttleId : split) {
                    String shuttleName = shuttleMap.get(Integer.parseInt(shuttleId));
                    shuttleNames.add(shuttleName);
                }
                record.setShuttleIds(StringUtils.join(shuttleNames, ","));
            }
            if (StringUtils.isNotEmpty(record.getHotelDetail())) {
                List<HotelDetail> hotelDetails = JSONUtil.toList(record.getHotelDetail(), HotelDetail.class);
                record.setHotels(hotelDetails);
            }
        }
        iPage.setRecords(records);
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> saveAirport(AirportService airportService) {
        Integer loginUserId = securityService.getLoginUserId();
        if (airportService.getId() == null) {
            airportService.setCreator(loginUserId);
            airportServiceMapper.insert(airportService);
        } else {
            airportService.setUpdater(loginUserId);
            airportServiceMapper.updateById(airportService);
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
        return R.ok();
    }

}

