package com.zsm.commonexample.interview;

/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/8/008.
 * @Modified By:
 */
public class NumberTest
{
    public static void main(String[] args)
    {
        testArraysInitialize();
    }

    /**
     * 测试多维数组初始化
     */
    private static void testArraysInitialize()
    {
        //多维数组会自动扩充，但不会自动补0。
        //1 2 3
        //2 1
        //3 4 5
        //4 5
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
    }

}
