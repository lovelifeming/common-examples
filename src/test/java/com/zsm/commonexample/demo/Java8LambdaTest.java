package com.zsm.commonexample.demo;

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
 *默认方法（Default methods）。默认方法允许将新功能添加到库的接口中，并确保兼容实现老版本接口的旧有代码
 *
 *重复注解（Repeating Annotations）。重复注解提供了在同一声明或类型中多次应用相同注解类型的能力
 *
 *类型注解（Type Annotation）。在任何地方都能使用注解，而不是在声明的地方
 *
 *方法参数反射（Method Parameter Reflection）
 *
 *Stream API 。新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。Stream API集成到了Collections API里
 *
 *HashMap改进，在键值哈希冲突时能有更好表现
 *
 *Date Time API。加强对日期和时间的处理
 *
 * Optional类来防止空指针异常 Optional类实际上是个容器：它可以保存类型T的值，或者保存null
 *
 * JavaScript引擎Nashorn Nashorn允许在JVM上开发运行JavaScript应用，允许Java与JavaScript相互调用
 *
 * Base64   Base64编码成为了Java类库的标准。Base64类同时还提供了对URL、MIME友好的编码器与解码器
 *
 *java.util 包下的改进，提供了几个实用的工具类。
 * 并行数组排序。
 * 标准的Base64编解码。
 * 支持无符号运算
 *
 *java.util.concurrent 包下增加了新的类和方法
 *
 *java.util.concurrent.ConcurrentHashMap 类添加了新的方法以支持新的StreamApi和lambada表达式
 *java.util.concurrent.atomic 包下新增了类以支持可伸缩可更新的变量
 *java.util.concurrent.ForkJoinPool类新增了方法以支持 common pool
 *新增了java.util.concurrent.locks.StampedLock类，为控制读/写访问提供了一个基于性能的锁，且有三种模式可供选择
 *
 *HotSpot
 *删除了 永久代（PermGen
 *方法调用的字节码指令支持默认方法
 *
 *
 *
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
