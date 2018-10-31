package com.zsm.commonexample.util;

import org.junit.Test;


/**
 *
 * Java8 Lambda表达式示例
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/10/31.
 * @Modified By:
 */
public class Java8LambdaTest
{

    @Test
    public void testLambda()
    {
        //创建线程
        Thread thread = new Thread(() -> System.out.println("lambda 创建线程"));
        thread.start();
        //赋值函数
        Action action = System.out::println;
        action.execute(":: 类似goto，C语言的指针");
        test(System.out::println, "lambda 函数式编程");
    }

    static void test(Action action, String str)
    {
        action.execute(str);
    }
}


@FunctionalInterface
interface Action<T>
{
    void execute(T t);
}
