package com.springboot.cloud.nsclcservice.nsclc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月08日 7:52 PM
 **/
public class MyStringsUtil {
    public static String getTimeSuffix() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
