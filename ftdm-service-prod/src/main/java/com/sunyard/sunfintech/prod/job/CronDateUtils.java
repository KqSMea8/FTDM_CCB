package com.sunyard.sunfintech.prod.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PengZY on 2017/6/4.
 * 该类提供Quartz的cron表达式与Date之间的转换
 */
public class CronDateUtils {

    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /***
     * @return  加5分钟
     */
    public static String getAddCron(){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date afterDate = new Date(new Date().getTime() + 300000);
        return sdf.format(afterDate);
    }

    /***
     * @return  cron类型的日期
     */
    public static String getCron(String jobName){
        int sum = 120;
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        Date afterDate = new Date(new Date().getTime() + sum * 1000);
        formatTimeStr = sdf.format(afterDate);
        System.out.println("job【"+jobName+"】获取到job下次启动时间为："+formatTimeStr);
        return formatTimeStr;
    }


    /***
     *
     * @param cron Quartz cron的类型的日期
     * @return  Date日期
     */

    public static Date getDate(final String cron) {

        if(cron == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;// 此处缺少异常处理,自己根据需要添加
        }
        return date;
    }

    public static void main(String[] args){


            System.out.println(getCron("123"));

    }

}
