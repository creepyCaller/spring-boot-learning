package com.liuzhousteel.sbldemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.model.*;
import com.liuzhousteel.sbldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;

    private ObjectMapper mapper;

    public AuthController(UserService userService) {
        this.mapper = new ObjectMapper();
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String toLogin() {
        return "auth/login";
    }

    @GetMapping(value = "/register")
    public String toRegister() {
        return "auth/register";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResultModel> login(@RequestParam(name = "param") String param, HttpSession session) throws IOException {
        LoginModel loginModel = mapper.readValue(param, LoginModel.class); // 前端传过来的JSON转对象
        return userService.login(loginModel, session);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResultModel> register(@RequestParam(name = "param") String param, HttpSession session) throws IOException {
        User user = mapper.readValue(param, User.class);
        try {
            userService.register(user);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        session.setAttribute("user", user);
        return new ResponseEntity<>(ResultModel.ok(1, user), HttpStatus.OK);
    }

}
