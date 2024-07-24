package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.FlightInfo;

/**
 * 航班信息表(FlightInfo)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 13:33:59
 */
public interface FlightInfoService extends IService<FlightInfo> {

    R<?> selectFlight(FlightInfo flightInfo, Integer page, Integer size);

    R<?> selectDict(String name);

    R<?> saveFlight(FlightInfo flightInfo);

    R<?> delFlight(Integer id);

}

