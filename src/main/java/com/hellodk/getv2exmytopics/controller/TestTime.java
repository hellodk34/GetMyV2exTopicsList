package com.hellodk.getv2exmytopics.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hellodk
 * @date 2021-07-25 23:29
 */
public class TestTime {

    public static void main(String[] args) {
        String time = "2021-07-23T03:43:21Z";

        time = time.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");
        Date d = null;
        try {
            d = format.parse(time);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        //此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String date = sf.format(d);
//        System.out.println(d);//这里输出的是date类型的时间
//        System.out.println(d.getTime() / 1000);//这里输出的是以秒为单位的long类型的时间。如果需要一毫秒为单位，可以不用除1000.
        System.out.println(sf.format(d));//这里输出的是字符串类型的时间
    }

}
