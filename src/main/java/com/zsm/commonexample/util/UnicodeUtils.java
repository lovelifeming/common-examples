package com.zsm.commonexample.util;

/**
 * Unicode 编码转换
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/28.
 * @Modified By:
 */
public class UnicodeUtils
{
    /**
     * unicode 编码，把字符串转码为Unicode编码。
     *
     * @param source 原始字符串
     * @return
     */
    public static String encodeUnicode(String source)
    {
        StringBuffer sb = new StringBuffer();
        char[] chars = source.toCharArray();
        String unicode = null;
        for (int i = 0; i < chars.length; i++)
        {
            unicode = Integer.toHexString(chars[i]);
            if (unicode.length() <= 2)
            {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        return sb.toString();
    }

    /**
     * unicode 解码：把Unicode编码转换为字符串
     *
     * @param target Unicode编码
     * @return
     */
    public static String decodeUnicode(String target)
    {
        StringBuffer sb = new StringBuffer();
        String[] strings = target.split("\\\\u");
        for (int i = 1; i < strings.length; i++)
        {
            int data = Integer.parseInt(strings[i], 16);
            sb.append((char)data);
        }
        return sb.toString();
    }

    /**
     * unicode 解码：把Unicode编码转换为字符串
     *
     * @param target Unicode编码
     * @return
     */
    public static String decodeUnicode1(String target)
    {
        int start = 0;
        int end = 0;
        StringBuffer sb = new StringBuffer();
        while (start > -1)
        {
            end = target.indexOf("\\u", start + 2);
            String charStr = null;
            if (end == -1)
            {
                charStr = target.substring(start + 2, target.length());
            }
            else
            {
                charStr = target.substring(start + 2, end);
            }
            char letter = (char)Integer.parseInt(charStr, 16);
            sb.append(new Character(letter).toString());
            start = end;
        }
        return sb.toString();
    }
}
