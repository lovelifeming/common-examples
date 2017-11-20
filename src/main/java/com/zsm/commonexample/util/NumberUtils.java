package com.zsm.commonexample.util;

/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:07.
 * @Modified By:
 */
public class NumberUtils
{
    private static final String ZERO = "0";

    /**
     * 十进制转成二进制
     */
    public static String toBinary(int value)
    {
        return convertInt(value, 1, 1);
    }

    public static String to(int value)
    {
        return convertInt(value, 7, 3);
    }

    /**
     * 十进制转换成十六进制
     *
     * @param value
     * @return
     */
    public static String toHex(int value)
    {
        return convertInt(value, 15, 4);
    }

    /**
     * 把int类型数据转换成其他进制
     *
     * @param num    转换值
     * @param base   与基数
     * @param offset 位移数
     * @return
     */
    private static String convertInt(int num, int base, int offset)
    {
        if (num == 0)
        {
            return ZERO;
        }
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] result = new char[32];
        int index = result.length;
        while (num != 0)
        {
            int temp = num & base;
            result[--index] = chs[temp];
            num = num >>> offset;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < result.length; i++)
        {
            sb.append(result[i]);
        }
        return sb.toString();
    }
}
