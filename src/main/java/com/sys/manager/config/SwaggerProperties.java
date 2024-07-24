package com.sys.manager.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lichp
 * @version 1.0.0  2020/10/28 10:43
 * @since JDK1.8
 */
@Configuration("com.sys.manager.config")
@Data
public class SwaggerProperties {

    /**
     * swagger enabled
     */
    @Value("${sys.swagger.enabled}")
    private boolean enabled;

    /**
     * Swagger basePackage
     */
    @Value("${sys.swagger.base-package}")
    private String basePackage;

    /**
     * Swagger title
     */
    @Value("${sys.swagger.title}")
    private String title;

    /**
     * Swagger description
     */
    @Value("${sys.swagger.description}")
    private String description;

    /**
     * Swagger version
     */
    @Value("${sys.swagger.version}")
    private String version;

    /**
     * Swagger version
     */
    @Value("${sys.swagger.author}")
    private String author;

}
