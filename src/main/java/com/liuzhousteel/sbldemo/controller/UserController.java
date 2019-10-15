package com.liuzhousteel.sbldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @GetMapping(value = "/{username}")
    public String userInfo(@PathVariable(value = "username") @ModelAttribute(value = "username") String username) {
        // 怎么把username传到info页，info页再通过它使用Ajax从UsersController::getUserInfo加载用户信息，model?
        return "user/info";
    }

}
