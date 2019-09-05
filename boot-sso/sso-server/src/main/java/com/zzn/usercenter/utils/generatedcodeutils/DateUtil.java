package com.zzn.usercenter.utils.generatedcodeutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author GaoWei
 * @describe 时间格式工具类
 * @time 2017/12/26,17:59
 */
public class DateUtil {
    /**
     * 获取格式化时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
        Date date = new Date ();
        return sdf.format (date);
    }

    /**
     * 获取格式化时间yyyyMMdd
     *
     * @return
     */
    public static String getNowTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = new Date ();
        return sdf.format (date);
    }

    /**
     * 获取格式化时间yyMMdd
     *
     * @return
     */
    public static String formatTimeForYyMmDd() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyMMdd");
        Date date = new Date ();
        return sdf.format (date);
    }

    /**
     * 获取格式化时间yyMMddHHmmss
     *
     * @return
     */
    public static String formatTimeForyyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyMMddHHmmss");
        Date date = new Date ();
        return sdf.format (date);
    }

    /**
     * long转Date
     *
     * @return
     */
    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat (formatType).format (data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate (currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString (date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat (formatType);
        Date date = null;
        date = formatter.parse (strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date (currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString (dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate (sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate (strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong (date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime ();
    }


    public static String dateTimeFormatStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat (format);
        return dateFormat.format (date);
    }


    /**
     * 获取前N天的日期时间
     *
     * @param day 前N天天数
     * @return 日期时间格式的字符串
     * @throws ParseException
     */
    public static String getFrontDay(Date dNow, int day, String format) throws ParseException {
        Date dBefore = new Date ();
        Calendar calendar = Calendar.getInstance ();  //得到日历
        calendar.setTime (dNow);//把当前时间赋给日历
        calendar.add (Calendar.DAY_OF_MONTH, -day);  //设置为前N天
        dBefore = calendar.getTime ();   //得到前一天的时间
        String result = DateUtil.dateTimeFormatStr (dBefore, format);
        return result;
    }

    /**
     * 获取前N小时的日期时间
     *
     * @param hours 前N小时
     * @return 日期时间格式的字符串
     * @throws ParseException
     */
    public static Date getFrontHours(Date dNow, int hours){
        Date dBefore;
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (dNow);
        calendar.add (Calendar.HOUR_OF_DAY, - hours);
        dBefore = calendar.getTime ();
        return dBefore;
    }

    /**
     * 获取前N分钟的日期时间
     *
     * @param minutes 前N分钟
     * @return 日期时间格式的字符串
     * @throws ParseException
     */
    public static String getFrontMinutes(Date dNow, int minutes,String format){
        Date dBefore=null;
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (dNow);
        calendar.add (Calendar.MINUTE, - minutes);
        dBefore = calendar.getTime ();
        String result = DateUtil.dateTimeFormatStr (dBefore, format);
        return result;
    }


    /**
     * 获取后N分钟的日期时间
     *
     * @param minutes 前N分钟
     * @return 日期时间格式的字符串
     * @throws ParseException
     */
    public static String getNextMinutes(Date dNow, int minutes,String format){
        Date dBefore=null;
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (dNow);
        calendar.add (Calendar.MINUTE, + minutes);
        dBefore = calendar.getTime ();
        String result = DateUtil.dateTimeFormatStr (dBefore, format);
        return result;
    }
    /**
     * 获取前N天的日期时间
     *
     * @param day 前N天天数
     * @return 日期时间格式的字符串
     * @throws ParseException
     */
    public static String getNextDay(Date dNow, int day, String format) throws ParseException {
        Date dBefore = null;
        //得到日历
        Calendar calendar = Calendar.getInstance ();
        //把当前时间赋给日历
        calendar.setTime (dNow);
        //设置为前N天
        calendar.add (Calendar.DAY_OF_MONTH, + day);
        //得到后一天的时间
        dBefore = calendar.getTime ();
        String result = DateUtil.dateTimeFormatStr (dBefore, format);
        return result;
    }

    /**
     * 获取倒计时
     * @param dt
     * @param day
     * @return
     * @throws ParseException
     */
    public static String getSurplusTime(Date dt , int day){
        long surplusTime = dt.getTime() + 24 * 60 * 60 * 1000 * day;
        if(surplusTime > System.currentTimeMillis()){
            final int MINUTE = 60;
            final int HOUR = 60 * 60;
            int countTime = (int) ((surplusTime - System.currentTimeMillis()) / 1000);
            String hour = (countTime / HOUR) > 0 ?
                    (countTime / HOUR) > 10 ? (countTime / HOUR)+"" : "0"+(countTime / HOUR) : "00";
            String minute = (countTime % HOUR / MINUTE) > 0 ?
                    (countTime % HOUR / MINUTE) > 10 ? (countTime % HOUR / MINUTE)+"" : "0"+(countTime % HOUR / MINUTE) : "00";
            String second = (countTime % MINUTE) > 0 ? (countTime % MINUTE) > 10 ? (countTime % MINUTE) + "" : "0" + (countTime % MINUTE) : "00";
            return new StringBuffer(hour).append(":").append(minute).append(":").append(second).toString();
        }else {
            return "00:00:00";
        }
    }


    public static String getTomorrowTime() {
        Date date = new Date ();//取时间
        Calendar calendar = new GregorianCalendar ();
        calendar.setTime (date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add (GregorianCalendar.DATE, 1);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime ();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        String dateString = formatter.format (date);
        return dateString;
    }


    /**
     * 根据当前时间计算往后延的指定时间
     *
     * @param cur  当前要操作的时间
     * @param day  天数
     * @param hour 小时
     * @param min  分钟
     * @return
     */
    public static Date getNewDateByDelay(Date cur, int day, int hour, int min) {
        Calendar c = Calendar.getInstance ();
        c.setTime (cur);   //设置时间
        c.add (Calendar.DATE, day); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        c.add (Calendar.HOUR, hour);
        c.add (Calendar.MINUTE, min);
        Date date = c.getTime (); //结果
        return date;
    }

    /**
     * 年月日字符串转换date
     *
     * @param str
     * @return
     */
    public static Date getDateByStr(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse (str);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * date转年月日字符粗
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getStrByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return sdf.format (date);
    }


    /**
     * 查询第二天的零点
     *
     * @return
     */
    public static Date todayFirstDate() {
        Calendar calendar = Calendar.getInstance ();
        calendar.add (Calendar.DAY_OF_MONTH, +1);
        calendar.set (Calendar.HOUR_OF_DAY, 0);
        calendar.set (Calendar.MINUTE, 0);
        calendar.set (Calendar.SECOND, 0);
        calendar.set (Calendar.MILLISECOND, 0);
        return calendar.getTime ();
    }

    /**
     * 查询第二天的23点59分59秒
     *
     * @return
     */
    public static Date todayLastDate() {
        Calendar calendar = Calendar.getInstance ();
        calendar.add (Calendar.DAY_OF_MONTH, +1);
        calendar.set (Calendar.HOUR_OF_DAY, 23);
        calendar.set (Calendar.MINUTE, 59);
        calendar.set (Calendar.SECOND, 59);
        calendar.set (Calendar.MILLISECOND, 999);
        return calendar.getTime ();
    }


    /**
     * 获取上一个月
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance ();
        cal.add (Calendar.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat ("yyyy-MM");
        String lastMonth = dft.format (cal.getTime ());
        return lastMonth;
    }

    /**
     * 描述:获取下一个月.
     *
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance ();
        cal.add (Calendar.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat ("yyyy-MM");
        String preMonth = dft.format (cal.getTime ());
        return preMonth;
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String getCurrentStartMonth() {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
        Calendar c = Calendar.getInstance ();
        c.add (Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set (Calendar.DAY_OF_MONTH, 1);
        return format.format (c.getTime ());
    }

    /**
     * 获取当前月最后一天
     *
     * @return
     */
    public static String getCurrentEndMonth() {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance ();
        ca.set (Calendar.DAY_OF_MONTH, ca.getActualMaximum (Calendar.DAY_OF_MONTH));
        return format.format (ca.getTime ());
    }

    /**
     * 查询当天零点
     *
     * @return
     */
    public static String todayFirstCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance ();
        calendar.set (Calendar.HOUR_OF_DAY, 0);
        calendar.set (Calendar.MINUTE, 0);
        calendar.set (Calendar.SECOND, 0);
        calendar.set (Calendar.MILLISECOND, 0);
        return format.format (calendar.getTime ());
    }

    /**
     * 查询当天的23点59分59秒
     *
     * @return
     */
    public static String todayLastCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance ();
        calendar.set (Calendar.HOUR_OF_DAY, 23);
        calendar.set (Calendar.MINUTE, 59);
        calendar.set (Calendar.SECOND, 59);
        calendar.set (Calendar.MILLISECOND, 999);
        return format.format (calendar.getTime ());
    }



    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentYearMoth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentYearMothDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentYearMothDayHms() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取单前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return 1 + Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 获得该月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 描述:获取下一个月的时间.
     *
     * @return
     */
    public static String getPerFirstMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }



    /**
     * 获取当前日期是星期几<br>
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 自定义 格式化规则
     * @param date
     * @param pattern
     * @return
     */
    public static String getFormatTimeForDefinition(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        return format;
    }

    /**
     * 获取给定日期的 0点0分0秒
     * @param date
     * @return
     */
    public static String getAppointDayFirstTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(zero);
    }

    /**
     * 日期的 天数 加法
     * @param date
     * @param day
     * @return
     */
    public static Date getAddDay(Date date,int day){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE, day);
        Date addDay = ca.getTime();
        return addDay;
    }

    /**
     * @Author: xuhui
     * 根据日期获取当天是周几
     * @param datetime 日期
     * @return 周几
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }

    /**
     * @Author: xuhui
     * 获取某段时间内有哪些星期？(周一 二 ...日等等)的日期
     * @param dataBegin 开始日期
     * @param dataEnd 结束日期
     * @para weekDays 获取周几，1－6代表周一到周六。0代表周日  , int weekDays
     * @return 返回日期List  0代表周日   1－6代表周一到周六
     */
    public static List<Integer> getDayOfWeekWithinDateInterval(String dataBegin, String dataEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Integer> weekResult = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        String[] dateInterval = {dataBegin, dataEnd};
        Date[] dates = new Date[dateInterval.length];
        for (int i = 0; i < dateInterval.length; i++) {
            String[] ymd = dateInterval[i].split("[^\\d]+");
            cal.set(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]));
            dates[i] = cal.getTime();
        }
        for (Date date = dates[0]; date.compareTo(dates[1]) <= 0; ) {
            cal.setTime(date);
            int weekday = cal.get(Calendar.DAY_OF_WEEK)-1;
            cal.add(Calendar.DATE, 1);//按1天递增
            date = cal.getTime();
            weekResult.add(weekday);

        }
        //去除重复的星期下标
        HashSet weekSet = new HashSet(weekResult);
        weekResult.clear();
        weekResult.addAll(weekSet);
        return weekResult;
    }



//    public static void main(String[] args) throws ParseException {
//        System.out.println (DateUtil.todayFirstCurrentDate ());
//        System.out.println (DateUtil.todayLastCurrentDate ());
//        System.out.println(getSurplusTime(getDateByStr("2019-04-10 13:23:01") , 1));
//    }

    /**
     * 获取门店营业时间
     * @return
     */
    public static String getStoreWorkTime(Date business,Date closed) {
        StringBuffer storeWorkTime = new StringBuffer();
        SimpleDateFormat formatter = new SimpleDateFormat ("HH:mm");
        String start = formatter.format (business);
        String end = formatter.format (closed);
        storeWorkTime.append(start).append("-").append(end);
        return storeWorkTime.toString();
    }
}
