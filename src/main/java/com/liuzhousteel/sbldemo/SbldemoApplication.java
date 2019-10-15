package com.liuzhousteel.sbldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.liuzhousteel.sbldemo.filter")
public class SbldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbldemoApplication.class, args);
    }

}
