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
 * 旅客信息表(PassengerInfo)表实体类
 *
 * @author makejava
 * @since 2024-07-23 13:35:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PassengerInfo对象", description = "旅客信息表")
public class PassengerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;
    
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String cardId;
    
    /**
     * 航班号
     */
    @ApiModelProperty(value = "航班号")
    private String flightNum;
    
    /**
     * 乘机日期
     */
    @ApiModelProperty(value = "乘机日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date flightDate;

    @TableField(exist = false)
    private String flightDateStr;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date flightStartDate;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date flightEndDate;
    
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
    
    /**
     * 舱位
     */
    @ApiModelProperty(value = "舱位")
    private String shippingSpace;
    
    /**
     * 座位号
     */
    @ApiModelProperty(value = "座位号")
    private String seat;
    
    /**
     * 始发站
     */
    @ApiModelProperty(value = "始发站")
    private String startCity;
    
    /**
     * 目的站
     */
    @ApiModelProperty(value = "目的站")
    private String arriveCity;
    
    /**
     * 入住酒店id
     */
    @ApiModelProperty(value = "入住酒店id")
    private Integer hotelId;

    @TableField(exist = false)
    @ApiModelProperty(value = "入住酒店名称")
    private String hotelName;
    
    /**
     * 是否已处理
     */
    @ApiModelProperty(value = "是否已处理")
    private String isDeal;
    
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
     * 接机摆渡车id
     */
    @ApiModelProperty(value = "接机摆渡车id")
    private String receiveShuttleId;

    /**
     * 送机摆渡车id
     */
    @ApiModelProperty(value = "送机摆渡车id")
    private String sendShuttleId;

}

