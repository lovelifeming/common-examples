package com.zsm.commonexample.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
