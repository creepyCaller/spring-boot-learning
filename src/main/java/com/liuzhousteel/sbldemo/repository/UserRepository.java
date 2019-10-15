package com.liuzhousteel.sbldemo.repository;

import com.liuzhousteel.sbldemo.domain.User;

// 继承JpaRepository,在泛型中指定实体类和唯一标识符的封装类型
public interface UserRepository extends BaseRepository<User, Integer> {

    // 使用"findBy" + A + "And" + B, 将会自动生成查询语句：select * from user where A and B对应的方法，返回一个实体类
    User findByUsername(String username);

}
