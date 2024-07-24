package com.sys.manager.exception;

import com.sys.manager.enums.ResultStatusEnum;
import lombok.Getter;

/**
 * 自定制异常类
 * @create 2019-07-14 10:33
 * @author lichp
 */
@Getter
public class CustomException extends RuntimeException {
    private int code;
    private String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
    }

    public CustomException setEnum(ResultStatusEnum resultStatusEnum){
        return new CustomException(resultStatusEnum.getCode(),resultStatusEnum.getMessage());
    }
}