package com.liuzhousteel.sbldemo.service.impl;

import com.liuzhousteel.sbldemo.domain.Want;
import com.liuzhousteel.sbldemo.repository.WantRepository;
import com.liuzhousteel.sbldemo.service.WantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class WantServiceImpl implements WantService {

    private final WantRepository wantRepository;

    public WantServiceImpl(WantRepository wantRepository) {
        this.wantRepository = wantRepository;
    }

    @Override
    public Page<Want> findAll(int currentPageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(currentPageNumber, pageSize);
        return wantRepository.findAll(pageable);
    }

    @Override
    public Want findById(int id) {
        return wantRepository.findById(id).orElseGet(() -> new Want(0, "error", 0, 0f, "none", new Date(), 0));
    }

    @Override
    public void delete(Want want) {
        wantRepository.delete(want);
    }

    @Override
    public Want save(Want want) {
        return wantRepository.save(want);
    }

}
