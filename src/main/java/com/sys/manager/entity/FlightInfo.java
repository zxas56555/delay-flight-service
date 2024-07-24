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
 * 航班信息表(FlightInfo)表实体类
 *
 * @author makejava
 * @since 2024-07-23 13:33:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FlightInfo对象", description = "航班信息表")
public class FlightInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 航空公司
     */
    @ApiModelProperty(value = "航空公司")
    private String flightCompany;
    
    /**
     * 航班号
     */
    @ApiModelProperty(value = "航班号")
    private String flightNum;
    
    /**
     * 航班日期
     */
    @ApiModelProperty(value = "航班日期")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date flightDate;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date flightStartDate;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date flightEndDate;
    
    /**
     * 航班状态(延误/正常)
     */
    @ApiModelProperty(value = "航班状态(延误/正常)")
    private String flightStatus;
    
    /**
     * 起飞时间
     */
    @ApiModelProperty(value = "起飞时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date startTime;
    
    /**
     * 到达时间
     */
    @ApiModelProperty(value = "到达时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date arriveTime;
    
    /**
     * 起飞城市
     */
    @ApiModelProperty(value = "起飞城市")
    private String startCity;
    
    /**
     * 到达城市
     */
    @ApiModelProperty(value = "到达城市")
    private String arriveCity;
    
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

    /**
     * 登机口
     */
    @ApiModelProperty(value = "登机口")
    private String boardingGate;

    /**
     * 登机时间
     */
    @ApiModelProperty(value = "登机时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date boardingTime;

}

