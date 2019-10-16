package com.liuzhousteel.sbldemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard")
@Api("后台接口，请求接口将转发至对应页面")
public class DashboardController {

    @GetMapping(value = "/manager")
    public String manager() {
        return "dashboard/manage";
    }

    @GetMapping(value = {"/want", "/want/", "/want/{page}"})
    @ApiOperation(value = "获取请求并转发至订单页", notes = "获取第{page}（可为空）页的的订单列表")
    @ApiImplicitParam(name = "page", dataType = "Integer", paramType = "query")
    public String want(@PathVariable(value = "page", required = false) @ModelAttribute(name="page") Integer page) {
        return "/dashboard/want";
    }

}