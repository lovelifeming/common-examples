package com.zsm.commonexample.util;

import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 22:58.
 * @Modified By:
 */
public class CommonUtils
{
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
        "e", "f"};

    /**
     * 关闭继承于Closeable接口的流对象
     *
     * @param closeables
     */
    public static void closeStream(Closeable... closeables)
    {
        for (Closeable closeable : closeables)
        {
            try
            {
                if (closeable != null)
                {
                    closeable.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println("FileUtils-->closeStream-->" + e.getMessage());
            }
        }
    }

    /**
     * 交换i和j的位置
     *
     * @param list
     * @param i
     * @param j
     */
    public static void swap(List<?> list, int i, int j)
    {
        swapE(list, i, j);
    }

    public static <E> void swapE(List<E> list, int i, int j)
    {
        list.set(i, list.set(j, list.get(i)));
    }

    /**
     * 类型转换，包括Long，long，String，Double，double，Integer，int，Date
     *
     * @param type
     * @param value
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> Object typeTransfer(Class<T> type, String value)
        throws RuntimeException
    {
        Object t = null;
        if (type.equals(Long.class) || type.equals(long.class))
        {
            t = Long.parseLong(value);
        }
        else if (type.equals(String.class))
        {
            t = value;
        }
        else if (type.equals(Double.class) || type.equals(double.class))
        {
            t = Double.parseDouble(value);
        }
        else if (type.equals(Integer.class) || type.equals(int.class))
        {
            t = Integer.parseInt(value);
        }
        else if (type.equals(Date.class))
        {
            t = Date.parse(value);
        }
        else
        {
            throw new RuntimeException(type.getName() + " type is invalid! value:" + value);
        }
        return t;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     *
     * @param bt
     * @return 十六进制字符串
     */
    public static String byteToHexString(byte bt)
    {
        int n = bt;
        if (n < 0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 将二进制转换成十六进制
     *
     * @param bytes 二进制数组
     * @return
     */
    public static String parseBytesToHexString(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将十六进制转换为二进制
     *
     * @param hexString
     * @return
     */
    public static byte[] parseHexStringToBytes(String hexString)
    {
        if (StringUtils.isEmpty(hexString))
        {
            return null;
        }
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++)
        {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }
}
