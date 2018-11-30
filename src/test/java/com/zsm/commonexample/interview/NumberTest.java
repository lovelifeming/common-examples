package com.zsm.commonexample.interview;

import org.junit.Test;


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
        int[][] ARRAY = {{1, 2, 3}, {2, 1}, {3, 4, 5}, {4, 5}};
        for (int i = 0; i < ARRAY.length; i++)
        {
            int[] ints = ARRAY[i];
            for (int j = 0; j < ints.length; j++)
            {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        //多维数组会自动扩充，但不会自动补0。
        //1 2 3
        //2 1
        //3 4 5
        //4 5
    }

    @Test
    public void comparableNumber()
    {
        /**
         * -128~127整数缓存在IntegerCache对象中,当缓存中存在对应值时直接指向该对象，不会生成新对象。
         */
        Integer one = 128;
        Integer two = 128;
        System.out.println("number one comparable number two:" + (one == two));
        Integer three = 127;
        Integer four = 127;
        System.out.println("number one comparable number two:" + (three == four));
    }

    @Test
    public void valueTransmit()
    {
        Integer i = 1;
        valueTransmit(i);
        System.out.println(i);
    }

    /**
     * java中都是值传递，基础类型是直接传递栈中值，基础类型对应的包装类则是都是final类，传递的是复制的引用地址，
     * 其他对象也是传递的引用地址。
     */
    private static void valueTransmit(Integer value)
    {
        value = value + 10;
        System.out.println("valueTransmit:" + value);
    }

    @Test
    public void testFloat()
    {
        Float total = 200000.8f;
        Float own = 170000.5f;
        Float left = total - own;
        System.out.println("剩余：" + left); //剩余：30000.297
    }

}
