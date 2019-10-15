package com.liuzhousteel.sbldemo.controller;

import com.liuzhousteel.sbldemo.domain.Want;
import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.service.WantService;
import com.liuzhousteel.sbldemo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Optional;
import java.util.Timer;

@Controller
@RequestMapping("/wants")
public class WantsController {

    private static final int PAGE_SIZE = 10;

    private final WantService wantService;

    public WantsController(WantService wantService) {
        this.wantService = wantService;
    }

    /**
     * 异步加载want
     * @param page 第 {page} 页
     * @return 包含第 {page} 页的订单信息的JSON字符串
     * */
    @GetMapping(value = "/page/{page}")
    public ResponseEntity<ResultModel> getWantPage(@PathVariable(value = "page") Integer page) {
        Page<Want> pager = null;
        if (page < 1) {
            // 如果请求的页号小于1，则到第一页
            pager = wantService.findAll(0, PAGE_SIZE);
        } else {
            pager = wantService.findAll(page - 1, PAGE_SIZE);
            int total = pager.getTotalPages();
            if (page > total) {
                // 如果请求的页号大于总页数，则到最后一页
                pager = wantService.findAll(total - 1, PAGE_SIZE);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(1, pager), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResultModel> getWant(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(ResultModel.ok(1, wantService.findById(id)), HttpStatus.OK);
    }

}