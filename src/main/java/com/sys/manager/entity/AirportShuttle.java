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
 * 服务与摆渡车关联表(AirportShuttle)表实体类
 *
 * @author makejava
 * @since 2024-07-24 17:24:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AirportShuttle对象", description = "服务与摆渡车关联表")
public class AirportShuttle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 服务表id
     */
    @ApiModelProperty(value = "服务表id")
    private Integer airportId;
    
    /**
     * 摆渡车id
     */
    @ApiModelProperty(value = "摆渡车id")
    private Integer shuttleId;
    
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
     * 是否发车
     */
    @ApiModelProperty(value = "是否发车")
    private String isGo;

    public AirportShuttle() {
    }

    public AirportShuttle(Integer airportId, Integer shuttleId, String isGo) {
        this.airportId = airportId;
        this.shuttleId = shuttleId;
        this.isGo = isGo;
    }

}

