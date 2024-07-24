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
 * 酒店信息表(HotelInfo)表实体类
 *
 * @author makejava
 * @since 2024-07-23 13:34:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "HotelInfo对象", description = "酒店信息表")
public class HotelInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 酒店名称
     */
    @ApiModelProperty(value = "酒店名称")
    private String hotelName;
    
    /**
     * 星级
     */
    @ApiModelProperty(value = "星级")
    private String starLevel;
    
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contacts;
    
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    
    /**
     * 酒店地址
     */
    @ApiModelProperty(value = "酒店地址")
    private String address;
    
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

