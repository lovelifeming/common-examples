package com.zsm.commonexample.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:11.
 * @Modified By:
 */
public class StringUtils
{
    static final Pattern REGEX_IS_NUM = Pattern.compile("[0-9]*");

    static final Pattern REGEX_IS_DATE = Pattern.compile(
        "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])" +
        "|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" +
        "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?" +
        "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?" +
        "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))" +
        "(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

    static final Pattern REGEX_IS_MOIBLE = Pattern.compile("^[1][0-9]{10}$");

    static final Pattern REGEX_IS_PHONE_ZONE = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");

    static final Pattern REGEX_IS_PHONE = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");

    static final Pattern REGEX_IS_EMAIL = Pattern.compile(
        "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    static final Pattern REGEX_IS_BANKCARD = Pattern.compile("^[0-9]{0,30}$");

    static final Pattern REGEX_IS_POSTAL = Pattern.compile("^[1-9]\\d{5}$");

    static final Pattern REGEX_IS_MONEY = Pattern.compile(
        "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)");

    static final Pattern REGEX_IS_ALL_NUMBER = Pattern.compile("^\\d*$");

    static final Pattern REGEX_IS_ALLOWABLE_IP = Pattern.compile(
        "(127[.]0[.]0[.]1)|" + "(localhost)|" + "(^10(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){3}$)|" +
        "(^172\\.([1][6-9]|[2]\\d|3[01])(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){2}$)|" +
        "(^192\\.168(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){2}$)");

    static final Pattern REGEX_RESOURCES_URL = Pattern.compile(".*(\\.jpg|\\.png|\\.gif|\\.js|\\.css).*");

    /**
     * 获取UUID字符串，移除-
     */
    public static String getUUID()
    {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    /**
     * 特殊字符转义,防止字符串里面的特殊字符被转义.
     */
    public static String conversionSpecialCharacter(String content)
    {
        content.replace("\\", "\\\\").replace("*", "\\*")
            .replace("+", "\\+").replace("|", "\\|")
            .replace("{", "\\{").replace("}", "\\}")
            .replace("(", "\\(").replace(")", "\\)")
            .replace("^", "\\^").replace("$", "\\$")
            .replace("[", "\\[").replace("]", "\\]")
            .replace("?", "\\?").replace(",", "\\,")
            .replace(".", "\\.").replace("&", "\\&");
        return content;
    }

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
     * 数字格式化:
     * 显示正负数的符号：+12与-12
     * 左对齐：12
     * 右对齐：   12
     * 补全编号是：001
     * 空格补全：   1
     * 价格是： 3.14159元
     * 整数分组是：31,415.926500
     * 括号包括：(12.131400)
     * 十六进制：0x14  八进制：024
     * 格式化前一个转换符：3.141593,转换后：3.14
     * 格式参数$的使用：12,abc
     * 浮点数格式：2.718285
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
        System.out.printf("格式参数$的使用：%1$d,%2$s %n", 12, "abc");
        //自定义浮点数精度
        Double val = 2.7182845965;
        int precision = 6;
        String format = "%." + precision + "f";
        System.out.println("浮点数格式：" + String.format(format, val, precision));
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
     * 分词器分割字符串
     *
     * @param source    原始字符串
     * @param separator 分隔符
     * @return
     */
    public static Object[] split(String source, String separator)
    {
        StringTokenizer tokenizer = new StringTokenizer(source, separator);
        List<Object> temp = new ArrayList<>();
        while (tokenizer.hasMoreElements())
        {
            temp.add(tokenizer.nextElement());
        }
        return temp.toArray();
    }

    /**
     * 判断字符串里面是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean containChinese(String str)
    {
        String regexChinese = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regexChinese);
        return pattern.matcher(str).find();
    }

    /**
     * 判断字符串是否包含英文
     */
    public static boolean containEnglish(String charaString)
    {
        return charaString.matches("^[a-zA-Z0-9]*");
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
     * 字符串转list集合
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

    /**
     * List 转 String 操作
     *
     * @param list
     * @return a, b, c, d
     */
    public static String convertString(List<String> list, String separate)
    {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < list.size(); i++)
        {
            temp.append(list.get(i));
            if (i != (list.size() - 1))
            {
                temp.append(separate);
            }
        }
        return temp.toString();
    }

    /**
     * 判断字符串是否为null或者为空
     */
    public static boolean isEmpty(String str)
    {
        if (null == str || str.length() == 0)
            return true;
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 判定字符串是否是数字
     */
    public static boolean isNum(String str)
    {
        if (str == null)
        {
            return false;
        }
        Matcher isNum = REGEX_IS_NUM.matcher(str);
        if (isNum.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static String setValueOrDefaultValue(String s, String defaultvalue)
    {
        return s == null ? defaultvalue : s;
    }

    /**
     * 判断是否为时间字符串
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate)
    {
        Matcher m = REGEX_IS_DATE.matcher(strDate);
        if (m.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 数字转汉语金额
     */
    public static String digitUppercase(double n)
    {
        String[] fraction = {"角", "分"};
        String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[][] unit = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};
        String head = n < 0 ? "负" : "";
        n = Math.abs(n);
        String s = "";
        for (int i = 0; i < fraction.length; i++)
        {
            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1)
        {
            s = "整";
        }
        int integerPart = (int)Math.floor(n);
        for (int i = 0; i < unit[0].length && integerPart > 0; i++)
        {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++)
            {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
            .replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /**
     * 手机号验证,验证通过返回true
     */
    public static boolean isMobile(final String str)
    {
        Matcher m = REGEX_IS_MOIBLE.matcher(str);
        return m.matches();
    }

    /**
     * 电话号码验证,验证通过返回true
     */
    public static boolean isPhone(final String str)
    {
        Matcher m;
        boolean b;
        int lengthOfContainsZone = 9;
        if (str.length() > lengthOfContainsZone)
        {
            m = REGEX_IS_PHONE_ZONE.matcher(str);
            b = m.matches();
        }
        else
        {
            m = REGEX_IS_PHONE.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 电子邮箱验证,验证通过返回true
     */
    public static boolean isEmail(final String str)
    {
        Matcher m;
        boolean b = false;
        if (str != null)
        {
            m = REGEX_IS_EMAIL.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 银行卡号验证,验证通过返回true
     */
    public static boolean isBankCard(final String str)
    {
        return REGEX_IS_BANKCARD.matcher(str).matches();
    }

    /**
     * 邮政编码验证,验证通过返回true
     */
    public static boolean isPostalcode(final String str)
    {
        return REGEX_IS_POSTAL.matcher(str).matches();
    }

    /**
     * 金额类验证,验证通过返回true
     */
    public static boolean isMoney(final String str)
    {
        return REGEX_IS_MONEY.matcher(str).matches();
    }

    public static boolean matchRegex(String val, String regex)
    {
        if (isEmpty(val))
        {
            return false;
        }
        return Pattern.compile(regex).matcher(val).matches();
    }

    /**
     * 全角转半角
     */
    public static String toDBC(String str)
    {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == '\u3000')
            {
                c[i] = ' ';
            }
            else if (c[i] > '\uFF00' && c[i] < '\uFF5F')
            {
                c[i] = (char)(c[i] - 65248);
            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /**
     * 验证字符串是否为纯数字,验证通过返回true
     */
    public static boolean isAllNum(final String str)
    {
        return REGEX_IS_ALL_NUMBER.matcher(str).matches();
    }

    /**
     * 判断是否为内网ip
     */
    public static boolean isInnerIp(String ip)
    {
        return REGEX_IS_ALLOWABLE_IP.matcher(ip).matches();
    }

    /**
     * 是否包含无用的URL
     */
    public static boolean filterUrl(String url)
    {
        return REGEX_RESOURCES_URL.matcher(url).find();
    }
}
