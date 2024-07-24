package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.HotelInfo;

/**
 * 酒店信息表(HotelInfo)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 13:34:52
 */
public interface HotelInfoService extends IService<HotelInfo> {

    R<?> selectHotel(HotelInfo hotelInfo, Integer page, Integer size);

    R<?> selectDict(String name);

    R<?> saveHotel(HotelInfo hotelInfo);

    R<?> delHotel(Integer id);

    R<?> deal(Integer passengerId);

}

