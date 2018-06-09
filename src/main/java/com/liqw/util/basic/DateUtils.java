package com.liqw.util.basic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liqw on 2018/6/9.
 */
public class DateUtils {
    public static void main(String[] args) {

        Date date = new Date();
        Date date1 = addSecound(date, -30);
        System.err.println("=>" + format(date));
        System.err.println("=>" + format(date1));
    }

    public static Date addSecound(Date date, int secound) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, secound);
        return calendar.getTime();
    }

    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
