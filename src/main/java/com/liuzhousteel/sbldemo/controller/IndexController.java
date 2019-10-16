package com.liuzhousteel.sbldemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api("主页控制器，用于访问主页")
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    @ApiOperation(value = "访问主页的地址", notes = "/或/index，将转发至templates/index.html")
    public String toIndex() {
        return "index";
    }

}
