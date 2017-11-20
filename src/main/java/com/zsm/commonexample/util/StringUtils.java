package com.zsm.commonexample.util;

/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:11.
 * @Modified By:
 */
public class StringUtils
{
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
}
