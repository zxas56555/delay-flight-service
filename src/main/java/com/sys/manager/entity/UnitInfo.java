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
 * 单位表
 * @author qmy
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UnitInfo对象", description="单位表")
public class UnitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "unit_id",type = IdType.AUTO)
    private Integer unitId;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("pid")
    private Integer pid;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建人")
    private Integer creator;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private Integer updater;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("成立时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date setupTime;

    @ApiModelProperty("邮政编码")
    private String postalCode;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("单位性质")
    private String unitNature;

    @ApiModelProperty("显示顺序")
    private Integer displayOrder;

    @ApiModelProperty("单位类别")
    private String unitType;

    @ApiModelProperty("单位编号")
    private String unitCode;

    @TableField(exist = false)
    private List<UnitInfo> child;

    @TableField(exist = false)
    @ApiModelProperty(value = "成员Ids")
    private String adminIds;

    @Override
    public String toString() {
        return "UnitInfo{" +
                "unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", pid=" + pid +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updater=" + updater +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", setupTime=" + setupTime +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", contacts='" + contacts + '\'' +
                ", unitNature='" + unitNature + '\'' +
                ", displayOrder=" + displayOrder +
                ", unitType='" + unitType + '\'' +
                ", unitCode='" + unitCode + '\'' +
                '}';
    }
}
