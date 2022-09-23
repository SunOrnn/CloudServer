package com.ornn.wallet.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 */
@Slf4j
public class DateUtils {
    // 线性局部变量
    public static final ThreadLocal<SimpleDateFormat> sfl = new ThreadLocal<SimpleDateFormat>() {

        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        };
    };

    public static final ThreadLocal<SimpleDateFormat> sf2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static final ThreadLocal<SimpleDateFormat> sf3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    /**
     * 时间格式化方法
     * @param date
     * @param format
     * @return
     */
    public static String getStringByFormat(Date date, ThreadLocal<SimpleDateFormat> format) {
        return format.get().format(date);
    }
}
