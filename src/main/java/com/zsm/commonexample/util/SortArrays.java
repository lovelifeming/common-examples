package com.zsm.commonexample.util;

/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:10.
 * @Modified By:
 */
public class SortArrays
{
    //region Description

    /**
     * ASC sort  选择排序升序
     *
     * @param value
     * @return int[]
     */
    public static int[] ascSelectSortInt(int... value)
    {
        int length = value.length;
        if (length <= 0)
        {
            return value;
        }
        int temp;
        for (int i = 0; i < length - 1; i++)
        {
            for (int j = i + 1; j < length; j++)
            {
                if (value[i] > value[j])
                {
                    swapSelect(value, i, j);
                }
            }
        }
        return value;
    }

    /**
     * DECS sort  选择排序降序
     *
     * @param value
     * @return int[]
     */
    public static int[] decsSelectSortInt(int... value)
    {
        int length = value.length;
        if (length <= 0)
        {
            return value;
        }
        for (int i = 0; i < length - 1; i++)
        {
            for (int j = i + 1; j < length; j++)
            {
                if (value[i] < value[j])
                {
                    swapSelect(value, i, j);
                }
            }
        }
        return value;
    }

    /**
     * DECS sort  冒泡排序降序
     *
     * @param value
     * @return int[]
     */
    public static double[] ascBubbleSortDouble(double... value)
    {
        int length = value.length;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length - i - 1; j++)
            {
                if (value[j] > value[j + 1])
                {
                    swapSelect(value, j, j + 1);
                }
            }
        }
        return value;
    }

    private static void swapSelect(int[] value, int before, int behind)
    {
        int temp;
        temp = value[before];
        value[before] = value[behind];
        value[behind] = temp;

    }

    private static void swapSelect(double[] value, int before, int behind)
    {
        double temp;
        temp = value[before];
        value[before] = value[behind];
        value[behind] = temp;
    }

    //endregion
}
