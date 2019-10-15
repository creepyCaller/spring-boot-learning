package com.liuzhousteel.sbldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @GetMapping(value = "/manager")
    public String manager() {
        return "dashboard/manage";
    }

    @GetMapping(value = {"/want", "/want/", "/want/{page}"})
    public String want(@PathVariable(value = "page", required = false) Integer page, Model model) {
        model.addAttribute("page", page == null ? 1 : page);
        return "/dashboard/want";
    }

}