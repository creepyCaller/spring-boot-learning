package com.liuzhousteel.sbldemo.model;

import lombok.Data;

/**
 * 登录动作中从表单提交过来的JSON数据对应的POJO
 */

@Data
public class LoginModel {

    private String username;

    private String password;

    private String remember;

}