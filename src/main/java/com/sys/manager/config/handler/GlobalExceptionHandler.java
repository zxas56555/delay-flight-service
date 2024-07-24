package com.sys.manager.config.handler;


import com.sys.manager.domain.R;
import com.sys.manager.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * description: 全局异常处理
 * create: 2022/01/24
 * @author Ray
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public R<?> handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("[CustomException 出错路径：]" + request.getRequestURI());
        e.printStackTrace();
        return R.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public R<?> handleBindException(BindException e, HttpServletRequest request) {
        log.error("[BindException 出错路径：]" + request.getRequestURI());
        e.printStackTrace();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuffer = new StringBuilder();
        allErrors.stream().filter(Objects::nonNull).forEach((objectError) -> stringBuffer.append("[").append(objectError.getDefaultMessage()).append("]"));
        return R.fail(500, stringBuffer.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("[MethodArgumentNotValidException 出错路径：]" + request.getRequestURI());
        e.printStackTrace();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuffer = new StringBuilder();
        allErrors.stream().filter(Objects::nonNull).forEach((objectError) -> stringBuffer.append("[").append(objectError.getDefaultMessage()).append("]"));
        return R.fail(500, stringBuffer.toString());
    }

    @ExceptionHandler(value = Exception.class)
    public R<?> handleException(Exception e, HttpServletRequest request) {
        log.error("[Exception 出错路径：]" + request.getRequestURI());
        e.printStackTrace();
        return R.fail(500, e.getMessage());
    }
}
