package com.liuzhousteel.sbldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.liuzhousteel.sbldemo")
public class SbldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbldemoApplication.class, args);
    }

}
