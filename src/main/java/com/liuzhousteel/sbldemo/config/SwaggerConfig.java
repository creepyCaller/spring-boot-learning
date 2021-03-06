package com.liuzhousteel.sbldemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置生成RESTful api的测试接口
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.liuzhousteel.sbldemo.controller")).paths(PathSelectors.any()).build();
    }

    /**
     * 配置API上显示的信息
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger Test").contact(new Contact("creepyCaller", "https://github.com/creepyCaller", "")).version("0.0.1").description("API 描述文档").build();
    }

}
