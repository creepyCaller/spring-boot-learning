package com.liuzhousteel.sbldemo.config;

import com.liuzhousteel.sbldemo.Interceptor.DashboardInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(new DashboardInterceptor());
//        registration.addPathPatterns("/dashboard/**");
//        registration.excludePathPatterns("/", "/index", "/user/toLogin", "/user/login", "/user/toRegister", "/user/register");
    }
}
