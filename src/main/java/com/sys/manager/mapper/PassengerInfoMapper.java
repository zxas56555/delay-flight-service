package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.manager.entity.PassengerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 旅客信息表(PassengerInfo)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-23 13:35:08
 */
public interface PassengerInfoMapper extends BaseMapper<PassengerInfo> {

    IPage<PassengerInfo> selectPassenger(IPage<PassengerInfo> iPage,
                                         @Param("passenger") PassengerInfo passenger);

    List<PassengerInfo> selectOrderByHotel(@Param("passenger") PassengerInfo passenger);

    PassengerInfo selectExist(@Param("flightNum") String flightNum,
                              @Param("flightDateStr") String flightDateStr,
                              @Param("cardId") String cardId);

    int updDeal(@Param("id") Integer passengerId);

}

