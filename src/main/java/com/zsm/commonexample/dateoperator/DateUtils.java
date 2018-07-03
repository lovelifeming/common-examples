package com.zsm.commonexample.dateoperator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/18 10:35.
 * @Modified By:
 */
public class DateUtils
{
    private static final String DATE_LONG = "yyyy-MM-dd HH:mm:ss";      //2018-07-02 20:08:02

    private static final String DATE_SHORT = "yyyy-MM-dd";              //2018-07-02

    private static final String DATE_LONG_EM = "EEE MMM dd HH:mm:ss z yyyy";    //Mon Jul 02 20:08:02 CST 2018

    private static final String DATE_SHORT_EM = "EEE MMM dd yyyy";      //Mon Jul 02 2018

    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    private static final String GMT_8 = "GMT+8";

    /**
     * 初始化时间格式化对象 SimpleDateFormat
     */
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_LONG);

    /**
     * 格式化时间
     *
     * @param date 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatString(Date date)
    {
        /**
         * "2018-7-1 18:10:30" 输出结果：
         * 06:10:30 下午
         * 18:10:30
         */
        //输出日期格式为%tr，转化为12小时制
        System.out.println(String.format("%tr", date));
        //输出日期格式为%tT，转化为24小时制
        System.out.println(String.format("%tT", date));

        return simpleDateFormat.format(date);
    }

    /**
     * 根据时间样式格式化时间
     *
     * @param date
     * @param type yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatString(Date date, String type)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转换为时间
     *
     * @param source 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date toLongDate(String source)
        throws ParseException
    {
        return simpleDateFormat.parse(source);
    }

    /**
     * 字符串转换为时间
     *
     * @param source 默认格式：yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date toShortDate(String source)
        throws ParseException
    {
        return new SimpleDateFormat("yyyy-MM-dd").parse(source);
    }

    /**
     * 字符串根据样式转换为时间
     *
     * @param source
     * @param type   格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date toDate(String source, String type)
        throws ParseException
    {
        return new SimpleDateFormat(type).parse(source);
    }

    /**
     * 返回给定时间在一个月中的对应日期值
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回日期对应的小时
     *
     * @param date
     * @return
     */
    public static int getHourOfDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取给定时间的前一个小时的时间的方法
     *
     * @param date
     * @return
     */
    public static Date getPreviousHourOfDate(Date date)
    {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

    /**
     * 返回当前时间的N小时的时间
     *
     * @param date
     * @param preHour
     * @return
     */
    public static Date getPreHourOfDate(Date date, int preHour)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -preHour + 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date preHourDate = calendar.getTime();
        return preHourDate;
    }

    /**
     * 返回传入时间之前N天的时间节点
     *
     * @param date
     * @param preDayNum
     */
    public static Date getPreDayDate(Date date, int preDayNum)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -preDayNum);
        Date preDate = calendar.getTime();
        return preDate;
    }

    /**
     * 计算两个日期之间相差多少天
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int intervalBetweenDays(Date startDate, Date endDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startDateTime = calendar.getTime().getTime();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long endDateTime = calendar.getTime().getTime();
        return (int)((endDateTime - startDateTime) / (1000 * 3600 * 24));
    }

    /**
     * 计算两个时间之间相差多少小时
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int intervalBetweenHours(Date startDate, Date endDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startDateTime = calendar.getTime().getTime();
        calendar.setTime(endDate);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long endDateTime = calendar.getTime().getTime();
        return (int)((endDateTime - startDateTime) / (1000 * 3600));
    }

    /**
     * 返回传入时间之前的时间列表
     *
     * @param date    时间节点
     * @param preHour 需要的时间个数
     * @return
     */
    public static List<Date> getPreHourDateList(Date date, int preHour)
    {
        List<Date> preHourDateList = getDateList(date, preHour);
        return preHourDateList;
    }

    /**
     * 获取两个时间点之间的小时时间
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getHourDateListBetween(Date startDate, Date endDate)
    {
        int interval = intervalBetweenHours(startDate, endDate);
        List<Date> preHourDateList = getDateList(endDate, interval);
        return preHourDateList;
    }

    private static List<Date> getDateList(Date date, int preHour)
    {
        List<Date> preHourDateList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        for (int i = preHour; i > 0; i--)
        {
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, -i + 1);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date preHourDate = calendar.getTime();
            preHourDateList.add(preHourDate);
        }
        return preHourDateList;
    }

    /**
     * 增加或者减少天数，number为正整数增加，number为负数减少
     *
     * @param date
     * @param number
     * @return
     */
    public static Date addDay(Date date, int number)
    {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_YEAR, number);
        return startDT.getTime();
    }

    /**
     * 将国际化的时间格式转换为标准时间
     *
     * @param date 时间字符串    EEE MMM dd HH:mm:ss z yyyy
     * @return yyyy-MM-dd
     * @throws ParseException
     */
    public static String dateTransformationShort(String date)
        throws ParseException
    {
        return transformation(date, DATE_SHORT_EM, DATE_SHORT, Locale.US, GMT_8);
    }

    /**
     * 将国际化的时间格式转换为标准时间
     *
     * @param date 时间字符串    EEE MMM dd HH:mm:ss z yyyy
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static String dateTransformationLong(String date)
        throws ParseException
    {
        return transformation(date, DATE_LONG_EM, DATE_LONG, Locale.US, GMT_8);
    }

    /**
     * 时间格式转换
     *
     * @param date         时间字符串,输入时间格式必须和输入格式一致
     * @param sourceFormat 时间字符串输入格式:   EEE MMM dd HH:mm:ss z yyyy
     * @param targetFormat 时间字符串输出格式:   yyyy-MM-dd HH:mm:ss
     * @param locale       时区
     * @return
     * @throws ParseException
     */
    public static String transformation(String date, String sourceFormat, String targetFormat,
                                        Locale locale, String timeZone)
        throws ParseException
    {
        SimpleDateFormat source = new SimpleDateFormat(sourceFormat, locale);
        source.setTimeZone(TimeZone.getTimeZone(timeZone));

        SimpleDateFormat target = new SimpleDateFormat(targetFormat, locale);
        target.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date parse = source.parse(date);
        return target.format(parse);
    }

    public enum CharSet
    {
        LONG_DATE,
        SHORT_DATA,
    }

}
