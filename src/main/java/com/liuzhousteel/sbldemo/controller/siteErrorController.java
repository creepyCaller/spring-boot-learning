package com.liuzhousteel.sbldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.web.servlet.error.ErrorController;
import javax.servlet.http.HttpServletResponse;

@Controller
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
