package com.liuzhousteel.sbldemo.service;

import com.liuzhousteel.sbldemo.domain.Want;
import com.liuzhousteel.sbldemo.repository.WantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WantServiceTest {

    @Autowired
    WantRepository wantRepository;

    @Test
    public void save() {
        Want want = new Want(25, "99式B型", 1, 0.5, "加满油", new Date(), 1);
        System.out.println(wantRepository.save(want).toString());

    }
}