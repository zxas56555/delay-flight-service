package com.sys.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 响应结果状态枚举类
 * @author lichp
 * @create 2019-07-14 10:22
 */
@NoArgsConstructor @AllArgsConstructor
public enum ResultStatusEnum {

    AUTHENTICATION_FAILED(320, "token失效，请重新登录！"),
    NO_ACCESS_TO_THIS_INTERFACE(320, "无权访问此接口！"),
    FAILED_TO_GENERATE_TOKEN(321, "生成token失败！"),
    ACCOUNT_PASSWORD_INCORRECT(322, "账号或密码错误！"),
    ACCOUNT_NOT_CREATE(323, "账号未创建！"),
    EMPOWER_NOT_ALLOW(330, "设备未授权！"),
    EMPOWER_EXPIRE(331, "设备授权已过期！"),
    EMPOWER_FAIL(332, "设备授权码错误！"),
    HAS_BEEN_PULLED_BLACK(324, "已被删除或禁用，无法登录！"),
    USERNAME_MAIL_IS_EXIST(341, "登录名称已经被注册!"),
    USERNAME_IS_BLANK(342, "登录名称为空!"),
    VERIFICATION_CODE_EXPIRED(350,"验证码已过期，请重新获取。"),
    VERIFICATION_CODE_FAILURE(351,"验证码输入错误。"),
    OPERATE_FAIL(360,"修改毕业生信息失败。"),
    DATA_IS_EMPTY(370,"查询到的结果为空"),
    SYSTEM_ABNORMAL(500, "系统繁忙，请稍后重试！"),
    UPLOAD_EXCEPTION(501, "文件上传异常！"),
    EXPORT_EXCEPTION(502, "文件导出异常！"),
    INCORRECT_FILE_FORMAT(503, "文件格式不正确！"),
    PARAMETER_CANNOT_BE_EMPTY(504, "参数不能为空，操作失败！"),
    NO_TEMP_UPLOADFILEPATH(505,"未配置文件上传临时存储路径"),
    USER_DOES_NOT_EXIST(507, "用户不存在，操作失败！"),
    IMPORT_COMPANY_FORMAT_ERROR(521,"Excel表格格式错误！"),
    IMPORT_COMPANY_FAIL(522,"部分数据导入失败"),
    INSERT_FAIL(600,"新增失败"),
    DuplicateKeyException(601,"该条信息已经存在,请勿重复添加"),
    UPDATE_FAIL(700,"更新失败"),
    DELETE_FAIL(800,"删除失败"),
    YEAR_IS_CLOSE(1001,"该年度暂未开启");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;
}
