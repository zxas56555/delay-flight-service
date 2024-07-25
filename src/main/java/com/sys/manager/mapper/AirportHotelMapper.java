package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.manager.entity.AirportHotel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务关联酒店表(AirportHotel)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-24 17:24:05
 */
public interface AirportHotelMapper extends BaseMapper<AirportHotel> {

    void delByAirportId(@Param("airportId") Integer id);

    List<AirportHotel> selectByAirId(@Param("airportId") Integer id);

}

