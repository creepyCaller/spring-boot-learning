package com.liuzhousteel.sbldemo.controller;

import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.model.UserModel;
import com.liuzhousteel.sbldemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@Api("用户控制器，用于对用户资源的GET、POST、PUT、DELETE操作")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}")
    @ApiOperation(value = "获取用户名为{username}的用户信息")
    @ApiImplicitParam(name = "username", dataType = "String", paramType = "path")
    public ResponseEntity<ResultModel> getUserInfo(@PathVariable(value = "username") String username) {
        UserModel userModel = userService.getUserModel(username);
        if (userModel == null) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(1, userModel), HttpStatus.OK);
    }

}
