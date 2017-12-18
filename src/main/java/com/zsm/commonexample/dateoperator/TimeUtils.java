package com.zsm.commonexample.dateoperator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/4 14:14.
 * @Modified By:
 */
public class TimeUtils
{
    /**
     * 初始化时间格式化对象 SimpleDateFormat
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算两个字符串时间差 天
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanDay(Date start, Date end)
        throws ParseException
    {
        int radix = 1000 * 60 * 60 * 24;
        int day = calculateTimespan(start, end, radix);
        return day;
    }

    /**
     * 计算两个字符串时间差 小时
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanHour(Date start, Date end)
        throws ParseException
    {
        int radix = 1000 * 60 * 60;
        int hours = calculateTimespan(start, end, radix);
        return hours;
    }

    /**
     * 计算两个字符串时间差 分钟
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanMinute(Date start, Date end)
        throws ParseException
    {
        int radix = 1000 * 60;
        int minute = calculateTimespan(start, end, radix);
        return minute;
    }

    /**
     * 计算两个字符串时间差 天
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanDay(String start, String end)
        throws ParseException
    {
        int radix = 1000 * 60 * 60 * 24;
        int day = TimespanByString(start, end, radix);
        return day;
    }

    /**
     * 计算两个字符串时间差 小时
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanHour(String start, String end)
        throws ParseException
    {
        int radix = 1000 * 60 * 60;
        int hours = TimespanByString(start, end, radix);
        return hours;
    }

    /**
     * 计算两个字符串时间差 分钟
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static int TimespanMinute(String start, String end)
        throws ParseException
    {
        int radix = 1000 * 60;
        int minute = TimespanByString(start, end, radix);
        return minute;
    }

    /**
     * 计算两个字符串时间差
     *
     * @param start
     * @param end
     * @param radix
     * @return
     * @throws ParseException
     */
    public static int TimespanByString(String start, String end, int radix)
        throws ParseException
    {
        Date startTime = simpleDateFormat.parse(start);
        Date endTime = simpleDateFormat.parse(end);
        int result = calculateTimespan(startTime, endTime, radix);
        return result;
    }

    private static int calculateTimespan(Date start, Date end, int divisor)
    {
        long startTime = start.getTime();
        long endTime = end.getTime();
        int result = (int)(endTime - startTime) / divisor;
        return result;
    }
}
