package com.zsm.commonexample.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/27.
 * @Modified By:
 */
public class TimerUtils
{
    public static void scheduleEveryDay(TimerTask timerTask, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, second);
        Timer timer = new Timer();
        Date date = new Date();
        timer.schedule(timerTask, date);
    }

    public static void periodSchedule(TimerTask timerTask, int period)
    {
        periodSchedule(timerTask, period, new Date());
    }

    public static void periodSchedule(TimerTask timerTask, int period, Date startDate)
    {
        Timer timer = new Timer();
        timer.schedule(timerTask, startDate, period);
    }
}
