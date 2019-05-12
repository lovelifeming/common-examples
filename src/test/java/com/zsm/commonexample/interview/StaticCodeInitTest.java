package com.zsm.commonexample.interview;

/**
 * 类初始化顺序：常量池和对象堆栈是在不同区域，两个区域的对象初始化顺序没有先后之分。
 * <p>
 * 初始化块通常写在类的构造方法之前，由花括号括起来，通常包含对成员属性进行初始化的语句；
 * 初始化块分为instance初始化块和static初始化块，初始化块在构造方法执行之前被执行；
 * static初始化块不能访问非statci成员，也不能调用非static方法，并且只在类加载时执行一次；
 * 初始化块通常用于提取多个构造方法中的公共代码
 * <p>
 * 父类静态字段初始化
 * 父类静态代码块
 * 子类静态字段初始化
 * 子类静态代码块
 * 父类普通字段初始化
 * 父类构造代码块
 * 父类构造函数
 * 子类普通字段初始化
 * 子类构造代码块
 * 子类构造函数
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2019-05-12 11:44.
 * @Modified By:
 */
public class StaticCodeInitTest
{
    public static void main(String[] args)
    {
        staticFunction();
    }

    {
        System.out.println("2");
        System.out.println("b=" + b);
    }

    static StaticCodeInitTest st = new StaticCodeInitTest();

    static
    {
        System.out.println("1");
    }

    StaticCodeInitTest()
    {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction()
    {
        System.out.println("4");
    }

    int a = 11;

    static int b = 12;
    /**
     * 输出：(对象内部创建自己的静态常量时，初始化的顺序是先创建堆栈对象，再初始化常量池)
     * 2
     * b=0
     * 3
     * a=11,b=0
     * 1
     * 4
     */

}
