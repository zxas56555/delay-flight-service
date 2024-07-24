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
import java.util.List;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ComDictionary对象", description="字典表")
public class ComDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "code项")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "父编号")
    private Integer pid;

    @ApiModelProperty(value = "目录级别")
    private Integer levels;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "状态 0：不可用，1：可用")
    private String status;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = " 创建人")
    private Integer creator;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Integer updater;

    @ApiModelProperty(value = "子级字典")
    @TableField(exist = false)
    private List<ComDictionary> child;

}
