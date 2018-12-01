package com.zsm.commonexample.interview;

import org.junit.Test;

import java.math.BigDecimal;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/8/008.
 * @Modified By:
 */
public class NumberTest
{
    /**
     * 测试多维数组初始化
     */
    @Test
    public void testArraysInitialize()
    {
        int[][] ARRAY = {{1, 2, 3}, {2, 1}, {3, 4, 5}, {4}};
        for (int i = 0; i < ARRAY.length; i++)
        {
            int[] ints = ARRAY[i];
            for (int j = 0; j < ints.length; j++)
            {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        //多维数组创建时不指定大小，会自动扩充，但不会自动补0。
        //1 2 3
        //2 1
        //3 4 5
        //4
    }

    @Test
    public void comparableNumber()
    {
        /**
         * Integer整数中 -128~127 整数缓存在IntegerCache对象中,当缓存中存在对应值时直接使用缓存对象，不会生成新对象。
         */
        Integer one = 128;
        Integer two = 128;
        System.out.println("number one comparable number two:" + (one == two));
        Integer three = 127;
        Integer four = 127;
        System.out.println("number one comparable number two:" + (three == four));
    }

    /**
     * java中都是值传递，基础类型是直接传递栈中的值，基础类型对应的包装类则是都是final类，传递的是复制的引用地址，
     * 而其他对象传递的则是引用地址。
     */
    @Test
    public void valueTransmit()
    {
        Integer i = 1;
        valueTransmit(i);
        System.out.println("valueTransmit behind:" + i);
    }

    private static void valueTransmit(Integer value)
    {
        value = value + 10;
        System.out.println("valueTransmit change:" + value);
    }

    //region Float浮点数相减精度丢失剖析
    //(1) 十进制整数转化为二进制数:只要遇到除以后的结果为0就结束了,所有整数都可以用二进制精确表示。
    //
    //           算法很简单。举个例子，11表示成二进制数：
    //
    //                     11/2=5 余   1
    //
    //                       5/2=2   余   1
    //
    //                       2/2=1   余   0
    //
    //                       1/2=0   余   1
    //
    //                          0结束         11二进制表示为(从下往上):1011
    //
    // (2) 十进制小数转化为二进制数:有些小数乘2永远不能消除小数部分，因此小数的二进制表示有时是不可能精确的。
    //
    //           算法是乘以2直到没有了小数为止。举个例子，0.9表示成二进制数
    //
    //                     0.9*2=1.8   取整数部分 1
    //
    //                     0.8(1.8的小数部分)*2=1.6    取整数部分 1
    //
    //                     0.6*2=1.2   取整数部分 1
    //
    //                     0.2*2=0.4   取整数部分 0
    //
    //                     0.4*2=0.8   取整数部分 0
    //
    //                     0.8*2=1.6 取整数部分 1
    //
    //                     0.6*2=1.2   取整数部分 0
    //
    //                              .........      0.9二进制表示为(从上往下): 1100100100100......
    //
    //endregion
    @Test
    public void testFloat()
    {
        Float f1 = 20.8f;
        Float f2 = 17.5f;
        Float f3 = f1 - f2;
        System.out.println("float数相减:" + f3); //Float浮点数相减精度丢失. 剩余：3.2999992
        //采用BigDecimal进行精密计算
        BigDecimal d1 = new BigDecimal(Float.toString(f1));
        BigDecimal d2 = new BigDecimal(Float.toString(f2));
        float f4 = d1.subtract(d2).floatValue();
        System.out.println("BigDecimal数相减:" + f4);
    }
}
