package com.springboot.cloud.nailservice.nail.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.Date;

/**
 * 提供处理字符串的工具方法。
 */
public class MyStringsUtil {

    /**
     * 获取当前时间的时间戳作为后缀。
     *
     * @return 当前时间的时间戳，格式为yyyyMMddHHmmss
     */
    public static String getTimeSuffix() {
//        // 创建日期格式化对象，指定日期格式为yyyyMMddHHmmss
//        SimpleDateFormat sdf = new SimpleDateFormat();
        // 创建日期格式化对象，指定日期格式为yyyyMMddHHmmssSSS（SSS表示毫秒）
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmssSSS");
        // 获取当前时间
        Date date = new Date();
        // 格式化日期对象，并返回格式化后的字符串
        return sdf.format(date);
    }

    /**
     * 生成一个 UUID，并移除其中的分隔符“-”。
     *
     * @return 不包含分隔符的 UUID 字符串
     */
    public static String getUUID() {
        // 生成一个 UUID
        UUID uuid = UUID.randomUUID();
        // 将 UUID 转换为字符串，并移除其中的分隔符“-”
        return uuid.toString().replaceAll("-", "");
    }
}
