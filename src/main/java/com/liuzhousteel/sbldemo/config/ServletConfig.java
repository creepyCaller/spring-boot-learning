package com.liuzhousteel.sbldemo.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet配置类
 * 配置Servlet在Filter所在的包进行组件扫描
 */
@Configuration
@ServletComponentScan("com.liuzhousteel.sbldemo.filter")
public class ServletConfig {
}
