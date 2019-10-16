package com.liuzhousteel.sbldemo.config;

import com.liuzhousteel.sbldemo.Interceptor.DashboardInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器，拦截除了dashboard下对want的操作之外的所有请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new DashboardInterceptor());
        registration.addPathPatterns("/dashboard/**");
        registration.excludePathPatterns("/dashboard/want", "/dashboard/want/", "/dashboard/want/**");
    }
}
