package com.liuzhousteel.sbldemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.model.*;
import com.liuzhousteel.sbldemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/auth")
@Api("认证控制器，用于用户的登录/登出/注册/注销等操作")
public class AuthController {

    private final HttpSession session;

    private final UserService userService;

    private ObjectMapper mapper;

    public AuthController(UserService userService, HttpSession session) {
        this.mapper = new ObjectMapper();
        this.userService = userService;
        this.session = session;
    }

    @ApiOperation(value = "跳转至登录页", notes="请求后转发至templates/auth/login.html")
    @GetMapping(value = "/login")
    public String toLogin() {
        return "auth/login";
    }

    @ApiOperation(value = "跳转至注册页页", notes="请求后转发至templates/auth/register.html")
    @GetMapping(value = "/register")
    public String toRegister() {
        return "auth/register";
    }

    @ApiOperation(value = "登出", notes="请求后从session中移除user，并重定向至templates/index.html")
    @GetMapping(value = "/logout")
    public String logout() {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @ApiOperation(value ="登录接口", notes = "用于登录的API，一个正确的传入参数是这样的：{\"username\":\"root\",\"password\":\"123456\",\"remember\":\"on\"}")
    @ApiImplicitParam(name = "param", dataType = "String", paramType = "query")
    @PostMapping(value = "/login")
    public ResponseEntity<ResultModel> login(@RequestParam(name = "param") String param) throws IOException {
        LoginModel loginModel = mapper.readValue(param, LoginModel.class); // 前端传过来的JSON转对象
        return userService.login(loginModel, session);
    }

    @ApiOperation(value ="注册接口", notes = "用于注册的API，一个正确的传入参数是这样的：{\"username\":\"demo\",\"email\":\"demo@demo.com\",\"password\":\"123456\"}")
    @ApiImplicitParam(name = "param", dataType = "String", paramType = "query")
    @PostMapping(value = "/register")
    public ResponseEntity<ResultModel> register(@RequestParam(name = "param") String param) throws IOException {
        User user = mapper.readValue(param, User.class);
        if (!userService.checkUsername(user.getUsername())) {
            // 如果存在这个用户则返回error 0
            return new ResponseEntity<>(ResultModel.error(2), HttpStatus.OK);
        }
        try {
            userService.register(user);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        session.setAttribute("user", user);
        return new ResponseEntity<>(ResultModel.ok(1, user), HttpStatus.OK);
    }

}
