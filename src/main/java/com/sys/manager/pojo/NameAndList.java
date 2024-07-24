package com.sys.manager.pojo;

import lombok.Data;

import java.util.List;

@Data
public class NameAndList {
    private Integer unitId;
    private String unitName;
    private List<NameAndList> children;
}
