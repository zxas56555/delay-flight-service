//package com.sys.manager.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author qmy
// * @since 2023-03-03
// */
//@Configuration
////动态配置数据源
//@RefreshScope
//public class DynamicDatasourceConfig {
//
//    @Value("${spring.datasource.druid.url}")
//    private String url;
//
//    @Value("${spring.datasource.druid.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.datasource.druid.username}")
//    private String userName;
//
//    @Value("${spring.datasource.druid.password}")
//    private String password;
//
//    /**
//     * 将自定义的DataSource添加到容器中
//     * 两个标签@Bean+@RefreshScope结合使用可以覆盖原生spring组件在容器中的同名Bean
//     * @Return
//     */
//    @Bean
//    @RefreshScope
//    public DruidDataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(url);
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//
//}
