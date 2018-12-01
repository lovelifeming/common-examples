package com.zsm.commonexample.interview;

import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/2.
 * @Modified By:
 */
public class TryCatchTest
{
    @Test
    public void testTryCatchFinally()
    {
        System.out.println("getName return:" + getName());
    }

    /**
     * 测试try-catch-finally执行过程，try-catch中的return的值不会受finally影响。在finally中有return，会直接返回忽略其他return。
     * try-catch-finally中的return优先级：finally < catch < try
     *
     * @return
     */
    public static String getName()
    {
        String name = "jason";
        try
        {
            name = "try name";
            System.out.println("try print:" + name);
            throw new Exception("");
            //return name;
        }
        catch (Exception e)
        {
            name = "catch name";
            System.out.println("catch print:" + name);
            return name;
        }
        finally
        {
            name = "finally name";
            System.out.println("finally print:" + name);
            //return name;
        }
    }

    public static final int END_NUMBER = Integer.MAX_VALUE;

    public static final int START_NUMBER = END_NUMBER - 2;

    @Test
    public void testCycle()
    {
        //循环整数游标index越界
        int count = 0;
        for (int index = START_NUMBER; index <= END_NUMBER; index++)
        {
            if (count > 100)
                break;
            count++;
        }
        System.out.println("循环次数:" + count);
    }

}
