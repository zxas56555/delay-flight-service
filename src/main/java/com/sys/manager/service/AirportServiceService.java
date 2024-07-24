package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AirportService;

/**
 * 接机/送机服务表(AirportService)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 11:48:21
 */
public interface AirportServiceService extends IService<AirportService> {

    R<?> selectAll(AirportService airportService, Integer page, Integer size);

    R<?> saveAirport(AirportService airportService);

    R<?> delAirport(Integer id);

}

