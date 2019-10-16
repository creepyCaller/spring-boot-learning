package com.liuzhousteel.sbldemo.service;

import com.liuzhousteel.sbldemo.domain.Want;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WantService extends BaseService  {

    Page<Want> findAll(int currentPageNumber, int pageSize);

    Want findById(int id);

    void delete(Want want);

    Want save(Want want);

    /**
     * @param id 前端传入的path变量id
     * @param want 待更新的数据
     * @return 更新后的POJO
     */
    Want update(int id, Want want);

}
