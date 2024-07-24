package com.sys.manager.pojo;

import com.sys.manager.entity.AdminInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminInfoResult extends AdminInfo {

    private String genderName;

    private String roleName;

    private String creatorName;

    private String updaterName;

    private String jobPostName;

    private BigDecimal score;

    private Integer index;

}
