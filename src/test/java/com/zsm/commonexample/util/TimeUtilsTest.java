package com.zsm.commonexample.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/4 14:42.
 * @Modified By:
 */
public class TimeUtilsTest
{
    private String startTime = "2017-12-4 12:08:6";

    private String endTime = "2017-12-4 14:12:12";

    @Test
    public void timespanTest()
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int radix = 1000 * 60 * 60;
        sop(TimeUtils.TimespanByString(startTime, endTime, radix));

        sop(TimeUtils.TimespanDay(startTime, endTime));
        sop(TimeUtils.TimespanHour(startTime, endTime));
        sop(TimeUtils.TimespanMinute(startTime, endTime));
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        sop(TimeUtils.TimespanDay(start, end));
        sop(TimeUtils.TimespanHour(start, end));
        sop(TimeUtils.TimespanMinute(start, end));
    }

    private static void sop(Object object)
    {
        System.out.println(object);
    }
}
