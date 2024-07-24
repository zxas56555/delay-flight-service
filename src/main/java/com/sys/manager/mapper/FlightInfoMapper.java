package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.manager.entity.FlightInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 航班信息表(FlightInfo)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-23 13:33:59
 */
public interface FlightInfoMapper extends BaseMapper<FlightInfo> {

    IPage<FlightInfo> selectFlight(IPage<FlightInfo> iPage,
                                   @Param("flight") FlightInfo flightInfo);

}

