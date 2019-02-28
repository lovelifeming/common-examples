package com.zsm.commonexample.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;


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
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
        "e", "f"};

    /**
     * 初始化 62 进制数据，索引位置代表转换字符的数值 0-61，比如 A代表10，z代表61
     */
    private static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    //private static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 进制转换比率
     */
    private static int SCALE = 62;

    /**
     * 匹配字符串只包含数字和大小写字母
     */
    private static String REGEX = "^[0-9a-zA-Z]+$";

    /**
     * 十进制转换成十六进制,十进制转换为二进制,十进制转换为八进制
     */
    public static void convertNumber()
    {
        int i = 666;
        String hexString = Integer.toHexString(i);
        System.out.println("十进制转换为十六进制：" + hexString);

        int hexInt = Integer.parseInt(hexString, 16);
        //如果是负数十六进制转回十进制，实现 BigInteger 获取
        int hexNegativeInt = new BigInteger(hexString, 16).intValue();

        String binaryString = Integer.toBinaryString(i);
        System.out.println("十进制转换为二进制：" + binaryString);

        int binaryInt = Integer.parseInt(binaryString, 2);

        String octalString = Integer.toOctalString(i);

        System.out.println("十进制转换为八进制：" + octalString);
        int octalInt = Integer.parseInt(octalString, 8);
        System.out.println("十六进制反转：" + hexInt + " 二进制反转：" + binaryInt + " 八进制反转：" + octalInt);
    }

    /**
     * 十进制转成二进制
     */
    public static String toBinary(int val)
    {
        return convertInt(val, 1, 1);
    }

    /**
     * 十进制转化成八进制
     *
     * @param value
     * @return
     */
    public static String toOCT(int value)
    {
        return convertInt(value, 7, 3);
    }

    /**
     * 十进制转换成十六进制
     *
     * @param val
     * @return
     */
    public static String toHex(int val)
    {
        return convertInt(val, 15, 4);
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

    /**
     * 金额格式转换
     *
     * @param d
     * @return
     */
    public String formatDouble(double d)
    {
        DecimalFormat fmat = new DecimalFormat("\u00A4##.0");
        return fmat.format(d);
    }

    //region 十进制数字与62进制字符串相互转换   62进制最多有 2^62 - 1个， max:AzL8n0Y58m7

    /**
     * 十进制数字转为62进制字符串
     *
     * @param val    十进制数字
     * @param length 输出字符串长度
     * @return 62进制字符串
     */
    public static String encode10To62(long val, int length)
    {
        return org.apache.commons.lang3.StringUtils.leftPad(encode10To62(val), length, '0');
    }

    /**
     * 十进制数字转为62进制字符串
     *
     * @param val 十进制数字
     * @return 62进制字符串
     */
    public static String encode10To62(long val)
    {
        if (val < 0)
        {
            throw new IllegalArgumentException("this is an Invalid parameter:" + val);
        }
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (Math.abs(val) > SCALE - 1)
        {
            //从最后一位开始进制转换，取转换后的值，最后反转字符串
            remainder = Long.valueOf(val % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            val = val / SCALE;
        }
        //获取最高位
        sb.append(CHARS.charAt(Long.valueOf(val).intValue()));
        return sb.reverse().toString();
    }

    /**
     * 十进制数字转为62进制字符串
     *
     * @param val 62进制字符串
     * @return 十进制数字
     */
    public static long decode62To10(String val)
    {
        if (val == null)
        {
            throw new NumberFormatException("null");
        }
        if (!val.matches(REGEX))
        {
            throw new IllegalArgumentException("this is an Invalid parameter:" + val);
        }
        String tmp = val.replace("^0*", "");

        long result = 0;
        int index = 0;
        int length = tmp.length();
        for (int i = 0; i < length; i++)
        {
            index = CHARS.indexOf(tmp.charAt(i));
            result += (long)(index * Math.pow(SCALE, length - i - 1));
        }
        return result;
    }
    //endregion

    //region  ~(取反)、&(与)、｜(或)、^(异或)、>>(右移)、<<(左移)、>>>(无符号右移)
    //>>：带符号右移。正数右移高位补0，负数右移高位补1。>>>：无符号右移。无论是正数还是负数，高位通通补0。

    /**
     * 对称加密: 原理是一个数异或同一个数两次还是原数
     *
     * @param source 加密字符串
     * @param key    对称加密密钥
     * @return
     */
    public static String encode(String source, int key)
        throws UnsupportedEncodingException
    {
        byte[] bytes = source.getBytes("UTF-8");
        for (int i = 0; i < bytes.length; i++)
        {
            bytes[i] = (byte)(bytes[i] ^ key);
        }
        return new String(bytes);
    }

    /**
     * 计算n*2
     *
     * @param n
     * @return
     */
    public static int mulTwo(int n)
    {
        return n << 1;
    }

    /**
     * 除以2
     *
     * @param n
     * @return
     */
    public static int divTwo(int n)
    {
        if (n > 0)
        {
            return n >> 1;
        }
        return n;
    }

    /**
     * 计算n*(2^m)，即乘以2的m次方
     *
     * @param n
     * @param m
     * @return
     */
    public static int mulTwoPower(int n, int m)
    {
        return n << m;
    }

    /**
     * 计算n/(2^m)，即除以2的m次方
     *
     * @param n
     * @param m
     * @return
     */
    public static int divTwoPower(int n, int m)
    {
        return n >> m;
    }

    /**
     * 奇偶数判断
     *
     * @param n
     * @return
     */
    public static boolean isOddNumber(int n)
    {
        return (n & 1) == 1;
    }

    /**
     * 不增加新变量的情况下交换两个变量的值,基于位运算的方式,实际上是(n^m)^m，也就是n异或了m两次，等号右边是n的值.
     *
     * @param n
     * @param m
     */
    public static void swapInt(int n, int m)
    {
        n = n ^ m;
        m = n ^ m;
        n = n ^ m;
        System.out.printf("n=%d,m=%d", n, m);
    }

    /**
     * 十进制转十六进制
     *
     * @param decimalism
     * @return
     */
    public static String decimalismToHex(int decimalism)
    {
        String hex = "";
        while (decimalism != 0)
        {
            int hexValue = decimalism % 16;
            hex = toHexCHar(hexValue) + hex;
            decimalism = decimalism / 16;
        }
        return hex;
    }

    /**
     * 将0~15的十进制数转换成0~F的十六进制数
     *
     * @param hexValue
     * @return
     */
    public static char toHexCHar(int hexValue)
    {
        if (hexValue <= 9 && hexValue >= 0)
        {
            return (char)(hexValue + '0');
        }
        else
        {
            return (char)(hexValue - 10 + 'A');
        }
    }

    //endregion

    //region 交换i和j的位置,类型转换，将字节数组转换为十六进制字符串，二进制与十六进制相互转换

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
    //endregion
}
