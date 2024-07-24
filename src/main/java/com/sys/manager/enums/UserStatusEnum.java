package com.sys.manager.enums;

/**
 * 用户状态
 *
 * @author lichp
 */
public enum UserStatusEnum {
    DELETED("0", "删除"), DISABLE("1", "禁用"), OK("2", "正常"), NOCREATE("3", "未创建");

    private final String code;
    private final String info;

    UserStatusEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
