package com.zsm.commonexample.dateoperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/18 10:35.
 * @Modified By:
 */
public class DateUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static Pattern REGEX_IS_DATE = Pattern.compile("\\d{4}-\\d{2}|\\d{4}-\\d{2}-\\d{2}");

    //      示例：2019-07-17T10:00:02.657Z
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_T = new SimpleDateFormat(
        DateFormatEnum.DATA_LONG_T.pattern);

    //      示例：Wed Jul 17 10:00:02 CST 2019
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_EZ = new SimpleDateFormat(
        DateFormatEnum.DATE_LONG_EM.pattern, Locale.UK);

    //      示例：2019-07-17 10:00:02
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateFormatEnum.DATE_LONG.pattern);

    public static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public static final String GMT_8 = "GMT+8";

    /**
     * 判断字符串是否为yyyy-MM或者yyyy-MM-dd
     */
    public static boolean checkDateStr(String dateStr)
    {
        return REGEX_IS_DATE.matcher(dateStr).matches();
    }

    /**
     * 初始化时间格式化对象 SimpleDateFormat
     */
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatEnum.DATE_LONG.pattern);

    /**
     * 获取当前时间的长日期格式字符串  yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowDateLongString()
    {
        return new SimpleDateFormat(DateFormatEnum.DAY.pattern).format(new Date());
    }

    /**
     * 获取时间的长日期格式字符串  yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateLongString(Date date)
    {
        return new SimpleDateFormat(DateFormatEnum.DAY.pattern).format(date);
    }

    /**
     * 获取当前时间的短日期格式字符串  yyyy-MM-dd
     *
     * @return
     */
    public static String getDateShortString()
    {
        return new SimpleDateFormat(DateFormatEnum.DAY.pattern).format(new Date());
    }

    /**
     * 获取时间的短日期格式字符串  yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateShortString(Date date)
    {
        return new SimpleDateFormat(DateFormatEnum.DAY.pattern).format(date);
    }

    /**
     * 日期时间转时间戳
     */
    public static long dateToTimeStamp(String date, DateFormatEnum dateFormat)
        throws ParseException
    {
        return new SimpleDateFormat(dateFormat.getFormat()).parse(date).getTime();
    }

    /**
     * 例如：2019-07-17T10:00:02.657Z 转换为标准时间 2019-07-17 10:00:02
     *
     * @param dateString
     * @return
     */
    public static String convertUTCToStandard(String dateString)
    {
        String result = "";
        try
        {
            result = SIMPLE_DATE_FORMAT.format(
                SIMPLE_DATE_FORMAT_EZ.parse(SIMPLE_DATE_FORMAT_T.parse(dateString).toString()));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    /**
     * 格式化时间
     *
     * @param date 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date)
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
     * @param format yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date, String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据时间样式格式化时间
     */
    public static String formatDate(Date date, DateFormatEnum format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.getFormat());
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
     * @param format 格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date toDate(String source, String format)
        throws ParseException
    {
        return new SimpleDateFormat(format).parse(source);
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
     * 增加或者减少天数，number为正整数增加，number为负数减少
     *
     * @param date
     * @param number
     * @return
     */
    public static Date addHours(Date date, int number)
    {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.HOUR_OF_DAY, number);
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
        return transformation(date, DateFormatEnum.DATA_SHORT_EM.pattern, DateFormatEnum.DAY.pattern, Locale.US, GMT_8);
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
        return transformation(date, DateFormatEnum.DATE_LONG_EM.pattern, DateFormatEnum.DATE_LONG.pattern,
            Locale.US, GMT_8);
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

    /**
     * 获取一段时间内的以天为单位的日期集合
     */
    public static List<String> getPerDaysByStartAndEndDate(String startDate, String endDate, String dateFormat)
    {
        return getPerDaysByStartAndEndDate(startDate, endDate, Calendar.DAY_OF_MONTH, dateFormat);
    }

    public static List<String> getPerDaysByStartAndEndDate(String startDate, String endDate, int calendarField,
                                                           String dateFormat)
    {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try
        {
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start >= end)
            {
                String result = format.format(eDate);
                return new ArrayList<String>()
                {{add(result);}};
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sDate);
            List<String> res = new ArrayList<String>();
            while (start < end)
            {
                res.add(format.format(calendar.getTime()));
                calendar.add(calendarField, 1);
                start = calendar.getTimeInMillis();
            }
            return res;
        }
        catch (ParseException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取当前时间前后几分钟的时间戳
     */
    public static Long getTimeByMinute(double minute)
    {
        Calendar calendar = Calendar.getInstance();
        int second = (int)(minute * 60);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTimeInMillis();
    }

    /**
     * 计算是否是季度末
     *
     * @param date eg:2020-03-01
     * @return true/false
     */
    public static boolean isSeasonEnd(String date)
    {
        int month = Integer.parseInt(date.substring(5, 7));
        List seasonEnds = Arrays.asList(3, 6, 9, 12);
        return seasonEnds.contains(month);
    }

    /**
     * 获取时间间隔N天的时间
     */
    public static String getDayFromNow(int day, DateFormatEnum format)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat(format.getFormat());
        calendar.add(Calendar.DAY_OF_YEAR, day);
        Date date = calendar.getTime();
        return df.format(date);
    }

    /**
     * 判断两个时间的间隔天数
     */
    public static int differentDays(String start, String end, DateFormatEnum dateFormat)
        throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat.getFormat());
        Date startDate = df.parse(start);
        Date endDate = df.parse(end);
        long a = endDate.getTime() - startDate.getTime();
        long b = a / (24 * 60 * 60 * 1000);
        return (int)b;
    }

    /**
     * 根据现在的年月获取去年的年份
     */
    public static String strToLastYear(String val)
        throws ParseException
    {
        Date date = toDate(val, DateFormatEnum.MONTH.pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        Date yearDate = c.getTime();
        String year = formatDate(yearDate, DateFormatEnum.MONTH);
        return year;
    }

    /**
     * 根据出生日期获取当前的年龄
     *
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay)
    {
        Calendar cal = Calendar.getInstance();
        if (birthDay == null || cal.before(birthDay))
        {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow < monthBirth || (monthNow == monthBirth && dayOfMonthNow < dayOfMonthBirth))
        {
            age--;
        }
        return age;
    }

    /**
     * 获取某月所有日期（形如:"2020-04-01"）
     */
    public static List<String> getDayOfMonth(int year, int month, DateFormatEnum format)
    {
        DateFormat df = new SimpleDateFormat(format.pattern);
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        while (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) < month)
        {
            dates.add(df.format(cal.getTime().clone()));
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * 获取某月天数
     */
    public static int getDaysOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取周对应数字,默认返回 周日(0)
     */
    public static int getWeekNum(String week)
    {
        return WeekEnum.getWeekEn(week);
    }

    public enum DateFormatEnum
    {
        /**
         * 年
         */
        YEAR(1, "yyyy"),
        /**
         * 年-月
         */
        MONTH(2, "yyyy-MM"),
        /**
         * 年月
         */
        MONTHNEW(3, "yyyyMM"),
        /**
         * 年-月-日
         */
        DAY(4, "yyyy-MM-dd"),
        /**
         * 年月日
         */
        DAYNEW(5, "yyyyMMdd"),
        /**
         * ""年""月""日
         */
        DAYOLD(6, "yyyy年MM月dd日"),
        /**
         * 年-月-日 时
         */
        HOUR(7, "yyyy-MM-dd HH"),
        /**
         * 年-月-日 时:分
         */
        MINUTES(8, "yyyy-MM-dd HH:mm"),
        /**
         * 年-月-日  时:分:秒
         */
        DATE_LONG(9, "yyyy-MM-dd HH:mm:ss"),
        /**
         * 时:分:秒
         */
        TIME(10, "HH:mm:ss"),
        DATE_LONG_EM(11, "EEE MMM dd HH:mm:ss z yyyy"), //Mon Jul 02 20:08:02 CST 2018
        DATA_SHORT_EM(12, "EEE MMM dd yyyy"),  //Mon Jul 02 2018
        DATA_LONG_SLASH(13, "yyyy/MM/dd HH:mm:ss"),
        DATA_SHORT_SLASH(14, "yyyy/MM/dd"),
        DATA_LONG_T(15, "yyyy-MM-dd'T'HH:mm:ss"),   //2019-07-17T10:00:02.657Z
        DATAEW(16, "yyyyMMddHHmmss");

        private int index;

        private String pattern;

        DateFormatEnum(int index, String pattern)
        {
            this.index = index;
            this.pattern = pattern;
        }

        public static String getFormat(int index)
        {
            for (DateFormatEnum df : DateFormatEnum.values())
            {
                if (df.getIndex() == index)
                {
                    return df.getFormat();
                }
            }
            return null;
        }

        public int getIndex()
        {
            return index;
        }

        public void setIndex(int index)
        {
            this.index = index;
        }

        public String getFormat()
        {
            return pattern;
        }

        public void setPattern(String pattern)
        {
            this.pattern = pattern;
        }
    }


    // 周
    public enum WeekEnum
    {
        Mon(1, "Mon", "星期一"),
        Tue(2, "Tue", "星期二"),
        Wed(3, "Wed", "星期三"),
        Thu(4, "Thu", "星期四"),
        Fri(5, "Fri", "星期五"),
        Sat(6, "Sat", "星期六"),
        Sun(0, "Sun", "星期日");

        private int val;

        private String chName;

        private String enName;

        public static String getWeek(int val)
        {
            for (WeekEnum wk : WeekEnum.values())
            {
                if (wk.getVal() == val)
                {
                    return wk.enName;
                }
            }
            return "";
        }

        public static int getWeekEn(String enName)
        {
            for (WeekEnum wk : WeekEnum.values())
            {
                if (wk.enName == enName)
                {
                    return wk.val;
                }
            }
            return 0;
        }

        public static int getWeekCh(String chName)
        {
            for (WeekEnum wk : WeekEnum.values())
            {
                if (wk.chName == chName)
                {
                    return wk.val;
                }
            }
            return 0;
        }

        WeekEnum(int index, String enName, String chName)
        {
            this.val = index;
            this.enName = enName;
            this.chName = chName;
        }

        public int getVal()
        {
            return val;
        }

        public void setVal(int val)
        {
            this.val = val;
        }

        public String getChName()
        {
            return chName;
        }

        public void setChName(String chName)
        {
            this.chName = chName;
        }

        public String getEnName()
        {
            return enName;
        }

        public void setEnName(String enName)
        {
            this.enName = enName;
        }
    }
}
