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
 * 菜单表
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysMenu对象", description="菜单表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限标识")
    private String perm;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件地址")
    private String component;

    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "父菜单")
    private Integer parentId;

    @ApiModelProperty(value = "排序")
    private String mOrder;

    @ApiModelProperty(value = "是否隐藏 ture false")
    private String hidden;

    @ApiModelProperty(value = "活动菜单")
    private String activeMenu;

    @ApiModelProperty(value = "缓存地址")
    private String rootPaths;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否启用 1启用/0废弃")
    private String status;

    @ApiModelProperty(value = "菜单所属的系统标识")
    private String systemCodes;

    private String menuType;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private Integer creator;

    @ApiModelProperty(value = "修改人")
    private Integer updater;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<SysMenu> children;

}
