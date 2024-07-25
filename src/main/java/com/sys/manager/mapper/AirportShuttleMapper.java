package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.manager.entity.AirportShuttle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务与摆渡车关联表(AirportShuttle)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-24 17:24:19
 */
public interface AirportShuttleMapper extends BaseMapper<AirportShuttle> {

    void delByAirportId(@Param("airportId") Integer id);

    List<AirportShuttle> selectByAirId(@Param("airportId") Integer id);
}

