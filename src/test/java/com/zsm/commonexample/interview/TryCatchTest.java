package com.zsm.commonexample.interview;

/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/2.
 * @Modified By:
 */
public class TryCatchTest
{
    public static void main(String[] args)
    {

        System.out.println("getName return:" + getName());
    }

    /**
     * 测试try-catch-finally执行过程，try-catch中的return的值不会受finally影响。在finally中有return，会直接返回忽略其他return。
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

}
