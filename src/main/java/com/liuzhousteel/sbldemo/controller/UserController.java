package com.liuzhousteel.sbldemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
@Api("访问用户的API")
public class UserController {

    /**
     * 访问{ServerRoot}/user/{username}，
     * 转发至用户详情页，
     * 详情页用model传入的用户名在GET {ServerRoot}/users/{username}，
     * 异步加载用户实体对应JSON对应对象，
     * 再由Vue.js绘制至DOM组件
     * @param username 地址传入的请求用户名
     * @return 需要映射的页面地址到视图解析器
     */
    @GetMapping(value = "/{username}")
    @ApiOperation(value = "获取用户名为{username}的详细信息")
    @ApiImplicitParam(name = "username", dataType = "String", paramType = "path")
    public String userInfo(@PathVariable(value = "username") @ModelAttribute(value = "username") String username) {
        // 怎么把username传到info页，info页再通过它使用Ajax从UsersController::getUserInfo加载用户信息，model?
        return "user/info";
    }

}
