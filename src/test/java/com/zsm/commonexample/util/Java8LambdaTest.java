package com.zsm.commonexample.util;

import org.junit.Test;


/**
 * Java8 Lambda表达式示例
 * Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中）
 * <p>
 * 语法格式：
 * 　　(parameters) -> expression 或者 (parameters) -> {statements;}
 * PS：
 * 　　（1）如果参数只有一个，可以不加圆括号
 * 　　（2）不需要声明参数类型
 * 　　（3）如果只有一条语句，可以不加花括号
 * 　　（4）如果只有一条语句，编译器会自动将值返回；如果多条的话，需要手动return
 * 方法引用通过方法的名字来指向一个方法
 * 语法格式：
 * 　　方法引用使用一对冒号 ::
 * 　　构造方法引用： 类::new
 * 　　静态方法引用：类::静态方法
 * 　　实例方法引用：类::实例方法  或者  对象::实例方法
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
