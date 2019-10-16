package com.liuzhousteel.sbldemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import com.liuzhousteel.sbldemo.domain.Want;
import com.liuzhousteel.sbldemo.repository.WantRepository;
import com.liuzhousteel.sbldemo.service.WantService;
import com.liuzhousteel.sbldemo.util.WantValueProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Optional;

@Service
public class WantServiceImpl implements WantService {

    private final WantRepository wantRepository;

    public WantServiceImpl(WantRepository wantRepository) {
        this.wantRepository = wantRepository;
    }

    @Override
    public Page<Want> findAll(int currentPageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(currentPageNumber, pageSize);
        return wantRepository.findAll(pageable);
    }

    @Override
    public Want findById(int id) {
        return wantRepository.findById(id).orElseGet(() -> new Want(0, "error", 0, (double) 0f, "none", new Date(), 0));
    }

    @Override
    public void delete(Want want) {
        wantRepository.delete(want);
    }

    @Override
    public Want save(Want want) {
        return wantRepository.save(want);
    }

    /**
     * @param id 前端传入的path变量id
     * @param want 待更新的数据
     * @return 更新后的POJO
     */
    @Override
    public Want update(int id, Want want) {
        Want oldWant = this.findById(id); // 通过id获取表项对应的POJO
        System.out.println("待合并的POJO： " + want.toString());
        WantValueProvider wantValueProvider = new WantValueProvider(); // 实例化一个ValueProvider
        wantValueProvider.setValueProvider(want); // 设置值提供者为want
        BeanUtil.fillBean(oldWant, wantValueProvider, CopyOptions.create().ignoreNullValue()); // 复制目标为oldWant，选项选择无视空值（即不对want的空值操作）
        oldWant.setId(id); // 设置id
        System.out.println("合并后的POJO： " + oldWant.toString());
        return wantRepository.save(oldWant);
    }

}
