package com.sys.manager.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 服务关联酒店表(AirportHotel)表实体类
 *
 * @author makejava
 * @since 2024-07-24 17:24:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AirportHotel对象", description = "服务关联酒店表")
public class AirportHotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 服务表id
     */
    @ApiModelProperty(value = "服务表id")
    private Integer airportId;
    
    /**
     * 酒店id
     */
    @ApiModelProperty(value = "酒店id")
    private Integer hotelId;
    
    /**
     * 酒店房间数
     */
    @ApiModelProperty(value = "酒店房间数")
    private Integer roomNum;
    
    /**
     * 创建人
     */
    private Integer creator;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    
    /**
     * 更新人
     */
    private Integer updater;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    private String dataStatus;

    public AirportHotel() {
    }

    public AirportHotel(Integer airportId, Integer hotelId, Integer roomNum) {
        this.airportId = airportId;
        this.hotelId = hotelId;
        this.roomNum = roomNum;
    }

}

