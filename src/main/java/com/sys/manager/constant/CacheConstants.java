package com.sys.manager.constant;

/**
 * 缓存的key 常量
 * @author  lichp
 * @version 1.0.0  2020/10/20 11:07
 * @since JDK1.8
 */
public class CacheConstants {
    /**
     * 令牌自定义标识
     */
    public static final String HEADER = "Auth-Token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 当前登录系统缓存前缀
     */
    public final static String LOGIN_SYSTEM_TYPE = "login_system_type:";

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";
}
