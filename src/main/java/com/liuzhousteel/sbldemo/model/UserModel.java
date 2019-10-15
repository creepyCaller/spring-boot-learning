package com.liuzhousteel.sbldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户模型，登陆成功后存在session中或者作为寻找特定用户信息Ajax请求返回的信息
 * status == 0 ? 登录失败 ：登陆成功
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private int id;

    private String username;

    private String email;

    private int status;

}