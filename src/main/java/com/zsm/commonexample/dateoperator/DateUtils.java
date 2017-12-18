package com.zsm.commonexample.dateoperator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/18 10:35.
 * @Modified By:
 */
public class DateUtils
{
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

}
