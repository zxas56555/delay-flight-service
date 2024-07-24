package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.manager.entity.AirportService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接机/送机服务表(AirportService)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-23 11:48:21
 */
public interface AirportServiceMapper extends BaseMapper<AirportService> {

    IPage<AirportService> selectAll(IPage<AirportService> iPage,
                                    @Param("airport") AirportService airportService,
                                    @Param("shuttleIds") List<String> shuttleIds);

}

