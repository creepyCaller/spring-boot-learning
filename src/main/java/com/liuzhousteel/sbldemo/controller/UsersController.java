package com.liuzhousteel.sbldemo.controller;

import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.model.UserModel;
import com.liuzhousteel.sbldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<ResultModel> getUserInfo(@PathVariable(value = "username") String username) {
        UserModel userModel = userService.getUserModel(username);
        if (userModel == null) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(1, userModel), HttpStatus.OK);
    }

}
