package com.liuzhousteel.sbldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard/want/")
public class WantController {

    @GetMapping(value = "/post")
    public String post() {
        return "/dashboard/want/post";
    }

    @GetMapping(value = "/put")
    public String put() {
        return "/dashboard/want/put";
    }

    @GetMapping(value = "/delete")
    public String delete() {
        return "/dashboard/want/delete";
    }

    @GetMapping(value = "/get")
    public String get() {
        return "/dashboard/want/get";
    }

}
