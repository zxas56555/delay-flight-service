/*
 *
 * UserInfo.java
 * Copyright(C) 2017-2020 fendo公司
 * @date 2020-07-13
 */
package com.sys.manager.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {

    private Integer id;
    private String name;
    private String userName;
    private String gender;
    private String avatar;
    private String phone;
    private String mail;
    private Integer roleId;
    private String unitName;
    private String unitId;
    private String status;
    private String cardId;
    private String permKeys;
    private List<String> menuList;
    private List<String> sysMenuNameList;
    public User() {
    }
}