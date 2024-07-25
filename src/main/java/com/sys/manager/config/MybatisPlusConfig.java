package com.sys.manager.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author lichp
 * @version 1.0.0  2020/10/22 9:47
 * @since JDK1.8
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public DruidConfig druidConfig() {
        return new DruidConfig();
    }

    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }


    /**
     * 拦截器配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(this.paginationInterceptor());
        return interceptor;
    }

    private PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        paginationInterceptor.setOverflow(false);
        /**
         * 注意! 此处要设置数据库类型.
         */
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }
}
