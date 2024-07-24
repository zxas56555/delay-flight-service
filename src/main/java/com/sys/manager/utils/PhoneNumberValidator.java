package com.sys.manager.utils;

public class PhoneNumberValidator {
    public static boolean validatePhoneNumber(String phoneNumber) {
        // 1. 定义手机号的正则表达式
        String regex = "^1[3456789]\\d{9}$";

        // 2. 使用正则表达式进行手机号校验
        if (phoneNumber.matches(regex)) {
            // 手机号格式正确
            return true;
        } else {
            // 手机号格式错误
            return false;
        }
    }
}
