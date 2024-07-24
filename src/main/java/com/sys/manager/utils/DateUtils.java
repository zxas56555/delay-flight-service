package com.sys.manager.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 
 * @author lichp
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String getCurrentMonthDay() {
        return dateTimeNow("MM/DD");
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到当前年的信息
     * @return
     */
    public static String getYear(){
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy");
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 得到去年同月份一号  00:00:00的时间戳
     * @param CurrentTimeStamp 要查询的月份的时间戳
     * @return
     */
    public static Long getLastPeriodTimeStamp(Long CurrentTimeStamp) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        if (CurrentTimeStamp != null) {
            calendar.setTimeInMillis(CurrentTimeStamp);
        }
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.YEAR, -1);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到去年1月1日零时的时间戳
     * @return
     */
    public static long getLastYearTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-1);
        calendar.set(Calendar.MONTH,0);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取下一个月一号的时间戳
     * @return
     */
    public static long getNextMonthTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取上月一号的时间戳
     * @return
     */
    public static long getLastMonthTimeStamp(Long currentTimeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        calendar.add(Calendar.MONTH, -1);

        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取上个月底的时间戳
     */
    public static long getLastMonthEndTime(Long currentTimeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取传入时间年份的1月1日的时间戳
     * @return
     */
    public static long getParamYearTimeStamp(Long yearTime) {
        Calendar calendar = Calendar.getInstance();
        if (yearTime != null) {
            calendar.setTimeInMillis(yearTime);
        }
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间的一号的时间戳
     * @return
     */
    public static long getMonthTimeStamp(Long currentTimeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取今年一月一号的时间戳
     * @return
     */
    public static long getCurrentYearTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取查询日期的一月一号的时间戳
     * @return
     */
    public static long getYearTime(Long currentTimeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        calendar.set(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取去年12月1日时间戳
     */
    public static long getLastYearDec() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,-1);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取查询年12月31日时间戳
     */
    public static long getEndYearDate(Long currentTimeStamp) {
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取传入时间一年后的时间戳
     */
    public static long getNextYear(Long currentTimeStamp){
        Calendar calendar = Calendar.getInstance();
        if (currentTimeStamp != null) {
            calendar.setTimeInMillis(currentTimeStamp);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.YEAR,+1);
        return calendar.getTimeInMillis();
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s, String patten){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 查询两个时间戳之间的月份个数
     */
    public static List<String> getMonthNumber(Long startDate, Long endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startDate);
        startCal.set(Calendar.DAY_OF_MONTH,1);
        startCal.set(Calendar.HOUR_OF_DAY,0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        List<String> dateList = new ArrayList<>();
        while (startDate <= endDate) {
            dateList.add(startCal.get(Calendar.YEAR)+"年"+(startCal.get(Calendar.MONTH)+1)+"月");
            startCal.add(Calendar.MONTH,+1);
            startDate = startCal.getTimeInMillis();
        }
        return dateList;
    }

    /**
     * 查询两个时间戳之间相隔的年份数目。
     */
    public static List<String> getYearNumber(Long startDate,Long endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startDate);
        startCal.set(Calendar.DAY_OF_MONTH,1);
        startCal.set(Calendar.HOUR_OF_DAY,0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        List<String> dateList = new ArrayList<>();
        while (startDate <= endDate) {
            dateList.add(startCal.get(Calendar.YEAR)+"年");
            startCal.add(Calendar.YEAR,+1);
            startDate = startCal.getTimeInMillis();
        }
        return dateList;
    }

    public static Integer getFrequency(Date startTime, Date endTime, Date secondStartTime, Date secondEndTime) {
        Date now = new Date();
        if (now.after(startTime) && now.before(endTime)) {
            return 1;
        }
        if (now.after(secondStartTime) && now.before(secondEndTime)) {
            return 2;
        }
        return null;
    }

    public static String dateToString(Date date) {
        return DateFormatUtils.format(date, "yyyy.MM.ddHH:mm:dd");
    }

    public static String dateToString(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static Date stringToDate(String str, String pattern) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取指定日期的月的第一天
     * @param day yyyy-MM-dd
     * @return
     */
    public static String getMonthFirstDay(String day) {
        Date date = stringToDate(day, "yyyy-MM-dd");
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //set方法将给定日历字段设置为给定值
        //下面代码作用是将日历设置为给定日期月份天数的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取指定日期的月的最后一天
     * @param day yyyy-MM-dd
     * @return
     */
    public static String getMonthEndDay(String day) {
        Date date = stringToDate(day, "yyyy-MM-dd");
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //getActualMaximum方法用来返回给定日历字段属性的最大值
        //endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) 代码作用为返回日历该月份的最大值
        //set方法，将该日历的给定月份设置为最大天数
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取指定日期的周的第一天（周一）
     * @param day yyyy-MM-dd
     * @return
     */
    public static String getWeekFirstDay(String day) {
        Date date = stringToDate(day, "yyyy-MM-dd");
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //获取传入日期属于星期几，根据星期几进行不同处理
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //周日：需要减去6天为周一。（当传入日期为周日时，若我们直接设置日历天数为周一，则日期会变为下一周的周一，而非当前周）
        if (dayOfWeek == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -6);
        } else {
            //周二 至 周六：直接获取周一即可
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return DateUtils.dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取指定日期的周的最后一天（周日）
     * @param day yyyy-MM-dd
     * @return
     */
    public static String getWeekEndDay(String day) {
        String weekFirstDay = getWeekFirstDay(day);
        Date date = stringToDate(weekFirstDay, "yyyy-MM-dd");
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        return DateUtils.dateToString(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取某年某月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDay(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //设置年份
        cal.set(Calendar.MONTH, month - 1);  //设置月份
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); //获取某月最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);   //设置日历中月份的最小天数
        //格式化日期
        return dateToString(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取某年某月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //设置年份
        cal.set(Calendar.MONTH, month - 1);  //设置月份
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);    //设置日历中月份的最大天数
        //格式化日期
        return dateToString(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取某年某周的第一天
     * @param time
     * @return
     */
    public static String getYearWeekFirstDay(String time){
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]);
        int week = Integer.parseInt(split[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,0,1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            //1月1日为周日，以周一为开始，第一周只有1天
            week = week - 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //设置年份
        cal.set(Calendar.WEEK_OF_YEAR, week);  //设置周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (cal.get(Calendar.YEAR) < year) {
            return year + "-01-01";
        }
        //格式化日期
        return dateToString(cal.getTime(), "yyyy-MM-dd");
    }

    public static void main(String[] args) {
        String a = getYearWeekFirstDay("2022-54");
        String b = getYearWeekEndDay("2022-54");
        System.out.println(a);
        System.out.println(b);
    }

    /**
     * 获取某年某周的最后一天
     * @param time
     * @return
     */
    public static String getYearWeekEndDay(String time){
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]);
        int week = Integer.parseInt(split[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,0,1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            //1月1日为周日，以周一为开始，第一周只有1天
            week = week - 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //设置年份
        cal.set(Calendar.WEEK_OF_YEAR, week);  //设置周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.DAY_OF_MONTH, 6);
        if (cal.get(Calendar.YEAR) > year) {
            return year + "-12-31";
        }
        //格式化日期
        return dateToString(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 获取上一月
     * @return
     */
    public static String getLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        return DateUtils.dateToString(calendar.getTime(), "yyyy-MM");
    }

}
