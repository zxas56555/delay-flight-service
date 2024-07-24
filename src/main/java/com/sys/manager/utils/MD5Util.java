package com.sys.manager.utils;

import org.springframework.util.DigestUtils;

/**
 * md5工具类
 *
 * @author lichp
 */
public class MD5Util {

    //盐，用于混交md5
    private final static String SALT = "asdwqAsd12_qS";

    /**
     * 默认得盐
     *
     * @param str 1
     * @return java.lang.String
     * @author lichp
     * @version 1.0.0  2020/10/27 8:44
     * @since JDK1.8
     */
    public static String getMD5Salt(String str) {
        String base = str + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    /**
     * @param str  1
     * @param salt 盐
     * @return java.lang.String
     * @author lichp
     * @version 1.0.0  2020/10/27 8:44
     * @since JDK1.8
     */
    public static String getMD5CusSalt(String str, String salt) {
        String base = str + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    /**
     * 不加盐
     *
     * @param str 1
     * @return java.lang.String
     * @author lichp
     * @version 1.0.0  2020/10/27 8:44
     * @since JDK1.8
     */
    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}