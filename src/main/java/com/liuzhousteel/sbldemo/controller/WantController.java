package com.liuzhousteel.sbldemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard/want/")
@Api("自定义的接口测试页面，用于视图转发，在使用Swagger后放弃")
public class WantController {

    @ApiOperation("自定义的接口测试页面，用于视图转发，在使用Swagger后放弃")
    @GetMapping(value = "/post")
    public String post() {
        return "/dashboard/want/post";
    }

    @ApiOperation("自定义的接口测试页面，用于视图转发，在使用Swagger后放弃")
    @GetMapping(value = "/put")
    public String put() {
        return "/dashboard/want/put";
    }

    @ApiOperation("自定义的接口测试页面，用于视图转发，在使用Swagger后放弃")
    @GetMapping(value = "/delete")
    public String delete() {
        return "/dashboard/want/delete";
    }

    @ApiOperation("自定义的接口测试页面，用于视图转发，在使用Swagger后放弃")
    @GetMapping(value = "/get")
    public String get() {
        return "/dashboard/want/get";
    }

}
