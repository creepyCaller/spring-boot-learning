package com.liuzhousteel.sbldemo.service;

import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.model.LoginModel;
import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.model.UserModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService extends BaseService {

    ResponseEntity<ResultModel> login(LoginModel loginModel, HttpSession session);

    void register(User user);

    boolean checkUsername(String username);

    void remember(User user);

    User findByUsername(String username);

    UserModel getUserModel(String username);

}
