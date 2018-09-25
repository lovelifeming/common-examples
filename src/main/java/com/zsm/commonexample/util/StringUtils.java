package com.zsm.commonexample.util;

import java.util.*;
import java.util.regex.Pattern;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:11.
 * @Modified By:
 */
public class StringUtils
{
    /**
     * 字符串格式化
     */
    public static void demonstrationString()
    {
        String str = String.format("字符串类型：Hi,%s", "小茗");
        System.out.println(str);
        str = String.format("字符串类型：Hi,%s:%s", "小茗", "小强", "小强");
        System.out.println(str);
        System.out.printf("字符类型：字母a的大写是：%c %n", 'A');
        System.out.printf("布尔类型：3>2的结果是：%b %n", 3 > 2);
        System.out.printf("整数类型（十进制）：10的一半是：%d %n", 10 / 2);
        System.out.printf("整数类型（十六进制）：20的16进制数是：%x %n", 20);
        System.out.printf("整数类型（八进制）：20的8进制数是：%o %n", 20);
        System.out.printf("浮点类型：20元的书打8.5折扣是：%f 元%n", 20 * 0.85);
        System.out.printf("十六进制浮点类型：16进制数：%a %n", 20 * 0.85);
        System.out.printf("指数类型：指数表示：%e %n", 20 * 0.85);
        System.out.printf("通用浮点类型：指数和浮点数结果的长度较短的是：%g %n", 20 * 0.85);
        System.out.printf("百分比类型：折扣是%d%% %n", 85);
        System.out.printf("散列码：字母A的散列码是：%h %n", 'A');
    }

    /**
     * 数字格式化
     */
    public static void demonstrationConvert()
    {
        //+  为正数或者负数添加符号
        System.out.printf("显示正负数的符号：%+d与%+d %n", 12, -12);
        //-左对齐
        System.out.printf("左对齐：%-5d %n", 12);
        System.out.printf("右对齐：%5d %n", 12);
        //补0使用 数字前面补0
        System.out.printf("补全编号是：%03d %n", 1);
        //空格使用
        System.out.printf("空格补全：% 4d%n", 1);
        //空格和小数点后面个数
        System.out.printf("价格是：% 2.5f元%n", 3.1415926529);
        //, 使用对数字分组
        System.out.printf("整数分组是：%,f %n", 31415.9265);
        //( 使用括号包含负数
        System.out.printf("括号包括：%(f %n", -12.1314);
        //# 如果是浮点数则包含小数点，如果是16进制或8进制则添加0x或0
        System.out.printf("十六进制：%#x  ", 20);
        System.out.printf("八进制：%#o %n", 20);
        //< 格式化前一个转换符所描述的参数
        System.out.printf("格式化前一个转换符：%f,转换后：%<3.2f %n", 3.14159265);
        //$使用 格式化参数的索引
        System.out.printf("格式参数$的使用：%1$d,%2$s", 12, "abc");
    }

    /**
     * 日期字符串格式
     */
    public static void demonstrationDate()
    {
        Date date = new Date();
        //c的使用
        System.out.printf("全部日期和时间信息：%tc%n", date);
        //f的使用
        System.out.printf("年-月-日格式：%tF%n", date);
        //d的使用
        System.out.printf("月/日/年格式：%tD%n", date);
        //r的使用
        System.out.printf("HH:MM:SS PM格式（12时制）：%tr%n", date);
        //t的使用
        System.out.printf("HH:MM:SS格式（24时制）：%tT%n", date);
        //R的使用
        System.out.printf("HH:MM格式（24时制）：%tR %n", date);
        //定义日期格式的转换符可以使日期通过指定的转换符生成新字符串
        //b的使用，月份简称
        String str = String.format(Locale.CHINESE, "英文月份简称：%tb", date);
        System.out.println(str);
        System.out.printf("本地月份简称：%tb%n", date);
        //B的使用，月份全称
        str = String.format(Locale.CHINESE, "英文月份全称：%tB", date);
        System.out.println(str);
        System.out.printf("本地月份全称：%tB%n", date);
        //a的使用，星期简称
        str = String.format(Locale.US, "英文星期的简称：%ta", date);
        System.out.println(str);
        //A的使用，星期全称
        System.out.printf("本地星期的简称：%tA%n", date);
        //C的使用，年前两位
        System.out.printf("年的前两位数字（不足两位前面补0）：%tC%n", date);
        //y的使用，年后两位
        System.out.printf("年的后两位数字（不足两位前面补0）：%ty%n", date);
        //j的使用，一年的天数
        System.out.printf("一年中的天数（即年的第几天）：%tj%n", date);
        //m的使用，月份
        System.out.printf("两位数字的月份（不足两位前面补0）：%tm%n", date);
        //d的使用，日（二位，不够补零）
        System.out.printf("两位数字的日（不足两位前面补0）：%td%n", date);
        //e的使用，日（一位不补零）
        System.out.printf("月份的日（前面不补0）：%te", date);
    }

    /**
     * 日期字符串格式化转换
     */
    public static void demonstrationDateConvert()
    {
        Date date = new Date();
        //H的使用
        System.out.printf("2位数字24时制的小时（不足2位前面补0）:%tH%n", date);
        //I的使用
        System.out.printf("2位数字12时制的小时（不足2位前面补0）:%tI%n", date);
        //k的使用
        System.out.printf("2位数字24时制的小时（前面不补0）:%tk%n", date);
        //l的使用
        System.out.printf("2位数字12时制的小时（前面不补0）:%tl%n", date);
        //M的使用
        System.out.printf("2位数字的分钟（不足2位前面补0）:%tM%n", date);
        //S的使用
        System.out.printf("2位数字的秒（不足2位前面补0）:%tS%n", date);
        //L的使用
        System.out.printf("3位数字的毫秒（不足3位前面补0）:%tL%n", date);
        //N的使用
        System.out.printf("9位数字的毫秒数（不足9位前面补0）:%tN%n", date);
        //p的使用
        String str = String.format(Locale.US, "小写字母的上午或下午标记(英)：%tp", date);
        System.out.println(str);
        System.out.printf("小写字母的上午或下午标记（中）：%tp%n", date);
        //z的使用
        System.out.printf("相对于GMT的RFC822时区的偏移量:%tz%n", date);
        //Z的使用
        System.out.printf("时区缩写字符串:%tZ%n", date);
        //s的使用
        System.out.printf("1970-1-1 00:00:00 到现在所经过的秒数：%ts%n", date);
        //Q的使用
        System.out.printf("1970-1-1 00:00:00 到现在所经过的毫秒数：%tQ%n", date);
    }

    /**
     * 首字母转小写
     *
     * @param str
     * @return
     */
    public static String toLowerCaseInitial(String str)
    {
        if (Character.isLowerCase(str.charAt(0)))
        {
            return str;
        }
        else
        {
            return new StringBuilder(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String toUpperCaseInitial(String str)
    {
        if (Character.isUpperCase(str.charAt(0)))
        {
            return str;
        }
        else
        {
            return new StringBuilder(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
        }
    }

    public static String appendTabAndWrap(String source, int tabNum, boolean wrap)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabNum; i++)
        {
            sb.append("\t");
        }
        sb.append(source);
        if (wrap)
        {
            sb.append(FileUtils.LINE_SEPARATOR);
        }
        return sb.toString();
    }

    /**
     * 判断字符串里面是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean haseChinese(String str)
    {
        String regexChinese = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regexChinese);
        return pattern.matcher(str).find();
    }

    /**
     * 判断字符串是否为null，如果为null返回 "" 空字符串，否则返回原字符串
     *
     * @param str
     * @return
     */
    public static String isNull(String str)
    {
        if (str == null)
        {
            return "";
        }
        else
        {
            return str;
        }
    }

    /**
     * 判断字符串是否为null或者为空。
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * 查找字符串里面包含的最大子字符串
     */
    public static String getMaxSubString(String str, String str1)
    {
        //获取较短字符串，缩进字符串长度查询最大子字符串
        //外循环控制判断的字符串长度，内循环从左向右移动检索
        int length = str.length();
        for (int i = 0; i < length; i++)
        {
            for (int x = 0, y = length - i; y != length + 1; ++x, ++y)
            {
                String temp = str.substring(x, y);
                if (str1.contains(temp))
                {
                    return temp;
                }
            }
        }
        return "";
    }

    /**
     * 字符串头尾去空
     */
    public static String trimString(String str)
    {
        char[] ch = str.toCharArray();
        int first = 0;
        int last = ch.length - 1;
        while (ch[first] == ' ')
        {
            first++;
        }
        while (ch[last] == ' ')
        {
            last--;
        }
        return str.substring(first, last + 1);
    }

    /**
     * 字符串反转
     */
    public static String revertString(String str)
    {
        char[] ch = str.toCharArray();
        int len = ch.length;
        for (int i = 0, j = len - 1; i < j; i++, j--)
        {
            swap(ch, i, j);
        }
        return String.valueOf(ch);
    }

    /**
     * 交换字符数组
     *
     * @param ch
     * @param first
     * @param last
     * @return
     */
    public static char[] swap(char[] ch, int first, int last)
    {
        char temp = ch[first];
        ch[first] = ch[last];
        ch[last] = temp;
        return ch;
    }

    /**
     * 切分字符串，最后组成list集合
     *
     * @param str        字符串
     * @param delimiters 分隔符
     * @return
     */
    public static List<String> convertList(String str, String delimiters)
    {
        StringTokenizer tokenizer = new StringTokenizer(str, delimiters);
        List<String> list = new ArrayList<>();
        while (tokenizer.hasMoreTokens())
        {
            list.add(tokenizer.nextToken());
        }
        return list;
    }
}
