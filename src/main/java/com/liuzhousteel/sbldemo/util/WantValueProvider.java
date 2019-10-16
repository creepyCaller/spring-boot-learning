package com.liuzhousteel.sbldemo.util;

import cn.hutool.core.bean.copier.ValueProvider;
import com.liuzhousteel.sbldemo.domain.Want;
import java.lang.reflect.Type;

public class WantValueProvider implements ValueProvider<String> {

    private Want valueProvider = null; // 指定值提供者为Wantl类型

    /**
     * 需要在service里设置好
     * @param valueProvider 值提供者，系前端传来的JSON转的bean
     */
    public void setValueProvider(Want valueProvider) {
        this.valueProvider = valueProvider;
    }

    /**
     * Key-Value
     * @param s Key，属性名
     * @param type 数据类型
     * @return Value，属性值
     */
    @Override
    public Object value(String s, Type type) {
        switch (s) {
            case "id":
                return this.valueProvider.getId();
            case "name":
                return this.valueProvider.getName();
            case "amount":
                return this.valueProvider.getAmount();
            case "price":
                return this.valueProvider.getPrice();
            case "remark":
                return this.valueProvider.getRemark();
            case "date":
                return this.valueProvider.getDate();
            case "status":
                return this.valueProvider.getStatus();
        }
        return null;
    }

    /**
     *
     * @param s 待确认是否包含在Bean里的属性名
     * @return  是否包含有名为s的属性 ? true : false
     */
    @Override
    public boolean containsKey(String s) {
        switch (s) {
            case "id":
            case "name":
            case "amount":
            case "price":
            case "remark":
            case "date":
            case "status":
                return true;
        }
        return false;
    }
}
