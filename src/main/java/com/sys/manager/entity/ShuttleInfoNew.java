package com.sys.manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 摆渡车信息表(ShuttleInfo)表实体类
 *
 * @author makejava
 * @since 2024-07-23 13:35:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ShuttleInfoNew对象", description = "新摆渡车信息表")
public class ShuttleInfoNew implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String shuttleNo;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 座位数
     */
    @ApiModelProperty(value = "座位数")
    private Integer seatNum;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String headPeople;

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

    @TableField(exist = false)
    private String isGo;

}

