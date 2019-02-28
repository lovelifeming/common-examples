package com.zsm.commonexample.demo;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


/**
 * java.time包
 * Instant：时间戳
 * Duration：持续时间，时间差
 * LocalDate：只包含日期，比如：2019-02-26
 * LocalTime：只包含时间，比如：16:30:10
 * LocalDateTime：包含日期和时间，比如：2019-02-26 16:30:10
 * Period：时间段
 * ZoneOffset：时区偏移量，比如：+8:00
 * ZonedDateTime：带时区的时间
 * Clock：时钟，比如获取目前美国纽约的时间
 * java.time.format包    DateTimeFormatter：时间格式化
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2019/2/26 15:52.
 * @Modified By:
 */
public class Java8DateTest
{
    /**
     * 获取当前日期操作
     */
    @Test
    public void testLocalDate()
    {
        LocalDate date = LocalDate.now();
        System.out.println("获取今天的日期: " + date);//2019-02-26

        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate firstDay1 = LocalDate.now().withDayOfMonth(1);
        System.out.println("获取本月第一天的日期: " + firstDay + " or " + firstDay1);//2019-02-01 or 2019-02-01

        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("获取本月最后一天的日期(不用考虑大月，小月，平年，闰年): " + lastDay);//2019-02-28

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        System.out.println("当天日期+1天: " + tomorrow);//2019-02-27

        boolean leapYear = date.isLeapYear();
        System.out.println("判断是否为闰年:" + leapYear);//2019年不是闰年false

        LocalDate date1 = LocalDate.of(2019, 3, 8);
        MonthDay monthDay = MonthDay.of(date1.getMonth(), date1.getDayOfMonth());
        MonthDay monthDay1 = MonthDay.from(LocalDate.of(2019, 2, 26));
        System.out.println("获取指定月天::" + monthDay + " and " + monthDay1);//--03-08 or --02-26

        LocalDate compareDate = LocalDate.of(2019, 3, 8);
        System.out.println("比较日期在今天之后:" + date.isAfter(compareDate) + " 或者之前: " + date.isBefore(compareDate));
    }

    /**
     * 获取当前时间操作
     */
    @Test
    public void testLocalTime()
    {
        LocalTime now = LocalTime.now();
        System.out.println("获取当前的时间: " + now);//16:53:06.480

        LocalTime nano = now.withNano(0);
        System.out.println("获取当前的时间(去除纳秒):" + nano);//16:53:06

        LocalTime time = LocalTime.of(16, 40, 10);
        LocalTime time1 = LocalTime.parse("16:40:10");
        System.out.println("生成指定时间: " + time + " or " + time1);//16:40:10 or 16:40:10

        LocalTime plusHours = now.plusHours(3);
        LocalTime plus = now.plus(3, ChronoUnit.HOURS);
        System.out.println("当前时间加3小时:" + plusHours + " or " + plus);//19:53:06.480 or 19:53:06.480
    }

    /**
     * 时区操作
     */
    @Test
    public void testZoneId()
    {
        ZoneId defaultZone = ZoneId.systemDefault();
        System.out.println("获取当前时区:" + defaultZone);//Asia/Shanghai

        ZoneId america = ZoneId.of("America/New_York");
        ZoneId china = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime americaDateTime = LocalDateTime.now(america);
        System.out.println("美国时区:" + america + " 中国时区:" + china);//America/New_York     America/New_York
        System.out.println("当地时间: " + localDateTime + " 美国时间: " + americaDateTime);
        //2019-02-26T17:27:19.483           2019-02-26T04:27:19.484

        ZonedDateTime americaZoneDateTime = ZonedDateTime.now(america);
        System.out.println("当前时间(带时区): " + americaZoneDateTime);//2019-02-26T04:27:19.493-05:00[America/New_York]
    }

    /**
     * 比较两个日期之间周期的时间差
     */
    @Test
    public void testPeriod()
    {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.of(2019, 10, 1);
        Period period = Period.between(start, end);
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();
        System.out.println("相差年数:" + years + " 相差月数:" + months + " 相差天数:" + days);// 0 7 5
        long until1 = start.until(end, ChronoUnit.DAYS);
        System.out.println("相差:" + until1);//217
    }

    /**
     * 日期时间格式化操作
     */
    @Test
    public void testFormatter()
    {
        String date = "20190202";
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate parse = LocalDate.parse(date, formatter);
        System.out.println("标准时间格式:" + parse);//2019-02-02

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        System.out.println("自定义时间格式:" + formatter1.format(LocalDateTime.now()));//2019-02-26 17:55:20
    }

    /**
     * 时间类与Date类相互转换
     */
    @Test
    public void testDateTime()
    {
        //Date与Instant的相互转化
        Instant instant = Instant.now();
        Date date = Date.from(instant);
        Instant instant1 = date.toInstant();
        System.out.println("转换前instant:" + instant.toString() + " 转换后date:" + date.toString() + " 再转换为instant1:" +
                           instant1.toString());
        //转换前instant:2019-02-27T01:33:22.138Z 转换后date:Wed Feb 27 09:33:22 CST 2019 再转换为instant1:2019-02-27T01:33:22.138Z

        //Date转为LocalDateTime
        Date date1 = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
        System.out.println("转换前date1:" + date1.toString() + " 转换后localDateTime:" + localDateTime.toString());

        //LocalDateTime转Date需要先转为Instant再转为Date
        LocalDateTime localDateTime1 = LocalDateTime.now();
        Instant toInstant = localDateTime1.atZone(ZoneId.systemDefault()).toInstant();
        Date date2 = Date.from(instant);
        System.out.println("转换前localDateTime1:" + localDateTime1.toString() + " 转换中间值toInstant:" +
                           toInstant.toString() + " 转换后date2:" + date2.toString());
        //转换前localDateTime1:2019-02-27T09:39:01.374 转换中间值toInstant:2019-02-27T01:39:01.374Z 转换后date2:Wed Feb 27 09:39:01 CST 2019

        //LocalDate转Date
        //因为LocalDate不包含时间，所以转Date时，会默认转为当天的起始时间，00:00:00
        LocalDate localDate = LocalDate.now();
        Instant instant2 = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date3 = Date.from(instant);
        System.out.println("转换前instant2:" + instant2.toString() + " 转换后date3:" + date3.toString());
        //转换前instant2:2019-02-26T16:00:00Z 转换后date3:Wed Feb 27 09:39:01 CST 2019
    }
}
