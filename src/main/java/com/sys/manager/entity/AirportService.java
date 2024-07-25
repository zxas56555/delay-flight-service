package com.sys.manager.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sys.manager.domain.R;
import com.sys.manager.pojo.HotelDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 接机/送机服务表(AirportService)表实体类
 *
 * @author makejava
 * @since 2024-07-23 11:48:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AirportService对象", description = "接机/送机服务表")
public class AirportService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 航班id
     */
    @ApiModelProperty(value = "航班id")
    private Integer flightId;
    
    /**
     * 航班号
     */
    @ApiModelProperty(value = "航班号")
    private String flightNum;

    @TableField(exist = false)
    @ApiModelProperty(value = "航班状态")
    private String flightStatus;
    
    /**
     * 服务时间
     */
    @ApiModelProperty(value = "服务时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date serviceTime;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date serviceStartTime;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date serviceEndTime;
    
    /**
     * 服务人数
     */
    @ApiModelProperty(value = "服务人数")
    private Integer serviceNum;

    @TableField(exist = false)
    private String shuttleIds;

    @TableField(exist = false)
    private String shuttleNames;

    @TableField(exist = false)
    private List<ShuttleInfo> shuttles;

    @TableField(exist = false)
    private String hotelIds;

    @TableField(exist = false)
    private String hotelNames;

    @TableField(exist = false)
    private List<HotelInfo> hotels;
    
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

}

