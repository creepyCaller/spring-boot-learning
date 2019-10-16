package com.liuzhousteel.sbldemo.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.web.servlet.error.ErrorController;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api("用于处理错误的控制器，将转发至错误码对应的提示错误页")
public class siteErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public String handleError(HttpServletResponse response, Model model) {
        model.addAttribute("status", response.getStatus());
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "error/error";
    }

}
