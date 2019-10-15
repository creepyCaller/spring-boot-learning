package com.liuzhousteel.sbldemo.service.impl;

import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.model.LoginModel;
import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.model.UserModel;
import com.liuzhousteel.sbldemo.repository.UserRepository;
import com.liuzhousteel.sbldemo.service.UserService;
import com.liuzhousteel.sbldemo.util.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResultModel> login(LoginModel loginModel, HttpSession session) {
        User loginUser = userRepository.findByUsername(loginModel.getUsername());
        if (loginUser != null) {
            if (BCrypt.checkpw(loginModel.getPassword(), loginUser.getPassword())) {
                session.setAttribute("user", loginUser);// 放置用户Bean至域
                if (null != loginModel.getRemember()) {
                    this.remember(loginUser);// 记住登录状态
                }
                return new ResponseEntity<>(ResultModel.ok(1,loginUser), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
    }

    @Transactional //事务
    @Override
    public void register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); //使用BCrypt算法加密密码
        userRepository.save(user);
    }

    @Override
    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username) == null;
    }

    @Override
    public void remember(User user) {

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserModel getUserModel(String username) {
        User user = this.findByUsername(username);
        return user == null ? null : new UserModel(user.getId(), user.getUsername(), user.getEmail(), user.getStatus());
    }

}
