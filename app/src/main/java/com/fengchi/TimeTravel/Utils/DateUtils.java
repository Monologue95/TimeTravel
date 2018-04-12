package com.fengchi.TimeTravel.Utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Java日期处理工具类
 *
 * @author baron
 */
public class DateUtils {
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat format_Year = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat format_Y_M_D_H_m =new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat format_Y_M_D_H_m_S = new SimpleDateFormat("yyyy-MM-dd HH: mm: ss");
    public static SimpleDateFormat format_Y_M_D_00_00_01 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat format_Y_M_D_23_59_59 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //获取当前时间
    public static String getNow() {
        return format_Y_M_D_H_m_S.format(new Date());
    }

    //获取当天日期
    public static String getToday() {
        return format.format(new Date()) + "";
    }

    //获取当年第一天
    public static String getFirstDayThisYear() {

        return format_Year.format(new Date()) + "-01-01";
    }

    //获取前月的第一天
    public static String getFirstDayForwardMonth() {
        //获取当前日期
        Calendar cal_1 = Calendar.getInstance();
        cal_1.add(Calendar.MONTH, -1);
        //设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(cal_1.getTime());
        return firstDay;
    }

    //获取前月的最后一天
    public static String getEndDayForwardMonth() {
        Calendar cale = Calendar.getInstance();
        //设置为1号,当前日期既为本月第一天
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = format.format(cale.getTime());
        return lastDay;
    }

    //获取当前月第一天
    public static String getFirstDayNowMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        return first;
    }


    //获取当前月第一天
    public static String getFirstDayNowMonth(SimpleDateFormat format_this) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format_this.format(c.getTime());
        return first;
    }

    //获取当前月最后一天
    public static String getEndDayNowMonth(SimpleDateFormat format_this) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format_this.format(ca.getTime());
        return last;
    }

    //获取某月最后一天
    public static String getEndDayOneMonth(SimpleDateFormat format_this, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        String last = format_this.format(lastDate);
        return last;
    }

    //获取某月第一天
    public static String getFirstDayOneMonth(SimpleDateFormat format_this, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        String first = format_this.format(firstDate);
        return first;
    }

    public static String getDateFromTimeStamp(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));   // 时间戳转换成时间
        return sd;
    }

    public static long getDateTrim(int year, int month, int day) {
        String dateString=" "+year;
        if(month<10){
            dateString=dateString+"0"+month;
        }else{
            dateString=dateString+month;
        }
        if(day<10){
            dateString=dateString+"0"+day;
        }else{
            dateString=dateString+day;
        }

        long timestamp=0;
        DateFormat df = new SimpleDateFormat(" yyyyMMdd");
        Date date = null;
        try {
            date = df.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            timestamp = cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.error("Date",e.toString());
        }
        return timestamp;
    }

    /*
    比较日期大小
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }
    /*
   计算相差天数
    */
    public static int getGapCount(String startDate, String endDate) {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=new GregorianCalendar();

        Date d1= null;
        Date d2= null;
        try {
            d1 = df.parse(startDate);
            d2=df.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) ((d2.getTime()-d1.getTime())/(60*60*1000*24));
    }
}
