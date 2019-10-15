package com.liuzhousteel.sbldemo.service;

import com.liuzhousteel.sbldemo.domain.Want;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WantService {

    Page<Want> findAll(int currentPageNumber, int pageSize);

    Want findById(int id);

    void delete(Want want);

    Want save(Want want);

}
