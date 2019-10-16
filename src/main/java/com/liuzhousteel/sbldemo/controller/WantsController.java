package com.liuzhousteel.sbldemo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuzhousteel.sbldemo.domain.User;
import com.liuzhousteel.sbldemo.domain.Want;
import com.liuzhousteel.sbldemo.model.ResultModel;
import com.liuzhousteel.sbldemo.service.WantService;
import com.liuzhousteel.sbldemo.util.TimeUtil;
import com.liuzhousteel.sbldemo.util.WantValueProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.lang.reflect.Type;

@Controller
@RequestMapping("/wants")
@Api("订单控制器，用于对订单资源的GET、POST、PUT、DELETE操作")
public class WantsController {

    private static final int PAGE_SIZE = 10;

    private final WantService wantService;

    private ObjectMapper mapper;

    public WantsController(WantService wantService) {
        this.mapper = new ObjectMapper();
        this.wantService = wantService;
    }

    /**
     * 异步加载want
     * @param page 第 {page} 页
     * @return 包含第 {page} 页的订单信息的JSON字符串
     * */
    @GetMapping(value = "/page/{page}")
    @ApiOperation(value = "获取第{page}页的订单列表", notes = "返回Page<T>对象")
    @ApiImplicitParam(name = "page", required = false, dataType = "Integer", paramType = "path")
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
    @ApiOperation(value = "获取id为{id}的订单详情", notes = "返回更新后的订单，标准的返回格式：{\"id\":1,\"name\":\"62LT\",\"amount\":0,\"price\":0.0,\"remark\":\"无\",\"date\":\"2019-10-09T10:23:56.000+0000\",\"status\":0}")
    @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path") // paramType = "path"用于@PathVariable注解的参数的获取
    public ResponseEntity<ResultModel> getWant(@PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(ResultModel.ok(1, wantService.findById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "更新id为{id}的订单", notes = "返回更新后的订单，标准的返回格式：{\"id\":1,\"name\":\"62LT\",\"amount\":0,\"price\":0.0,\"remark\":\"无\",\"date\":\"2019-10-09T10:23:56.000+0000\",\"status\":0}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "param", dataType = "String", paramType = "query")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResultModel> updateWant(@PathVariable(value = "id") Integer id, @RequestParam("param") String param) {
        Want want = null;
        try {
            want = mapper.readValue(param, Want.class); // 传入的JSON字符串转为对象
        } catch (IOException e) {
            return new ResponseEntity<>(ResultModel.error(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(1, wantService.update(id, want)), HttpStatus.OK);
    }

    @ApiOperation(value = "删除id为{id}的订单", notes = "无返回")
    @ApiImplicitParam(name = "id", dataType = "Integer", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResultModel> deleteWant(@PathVariable(value = "id") Integer id) {
        wantService.delete(new Want(id));
        return new ResponseEntity<>(ResultModel.ok(1), HttpStatus.OK);
    }

}