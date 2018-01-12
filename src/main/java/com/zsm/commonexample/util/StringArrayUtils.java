package com.zsm.commonexample.util;

/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/10 10:07.
 * @Modified By:
 */
public class StringArrayUtils
{

    /**
     * 把String[]数组转换成字符串
     *
     * @param value 数组
     * @param start 数组index起始位置，0~value.length-1
     * @param end   数组index结束位置，0~value.length-1
     * @return
     */
    public static String convertStringArrayToString(String[] value, int start, int end)
    {
        boolean flag = start > 0 && start <= end && end <= value.length - 1;
        if (!flag)
        {
            new RuntimeException("parameter has error,please check!");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++)
        {
            sb.append(value[i]);
        }
        return sb.toString();
    }
}
