package com.sys.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ray
 */
@SpringBootApplication
@MapperScan("com.sys.manager.mapper")
public class SysManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysManagerApplication.class, args);
    }


}
