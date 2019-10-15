package com.liuzhousteel.sbldemo.util;

import java.util.Date;

public class TimeUtil {

    private static Date date;

    static {
        date = new Date();
    }

    public static long getTime() {
        return date.getTime();
    }

}
