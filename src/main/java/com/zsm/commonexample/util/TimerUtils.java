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
    /**
     *
     * @param timerTask
     * @param hour
     * @param minute
     * @param second
     */
    public static Timer scheduleEveryDay(TimerTask timerTask, int hour, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, second);
        
        Timer timer = new Timer();
        Date date = new Date();
        timer.schedule(timerTask, date);
        return timer;
    }

    public static Timer periodSchedule(TimerTask timerTask, int period)
    {
        return periodSchedule(timerTask, period, new Date());
    }

    public static Timer periodSchedule(TimerTask timerTask, int period, Date startDate)
    {
        Timer timer = new Timer();
        timer.schedule(timerTask, startDate, period);
        return timer;
    }
}
