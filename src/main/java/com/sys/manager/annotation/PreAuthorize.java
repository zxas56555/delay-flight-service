package com.sys.manager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 *
 * @author lichp
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {
    /**
     * 验证用户是否具备某权限
     */
    public String hasPerm() default "";

    /**
     * 验证用户是否具有以下任意一个权限
     */
    public String[] hasAnyPerm() default {};

}
