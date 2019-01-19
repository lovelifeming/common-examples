package com.zsm.commonexample.demo;

import org.junit.Test;

import java.io.PrintStream;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.out;


/**
 * Java5开发代号为Tiger(老虎),于2004-09-30发行
 * 特性列表
 * 泛型:所谓类型擦除指的就是Java源码中的范型信息只允许停留在编译前期，而编译后的字节码文件中将不再保留任何的范型信息。也就是说，
 * 范型信息在编译时将会被全部删除，其中范型类型的类型参数则会被替换为Object类型，并在实际使用时强制转换为指定的目标数据类型。
 * 而C++中的模板则会在编译时将模板类型中的类型参数根据所传递的指定数据类型生成相对应的目标代码。
 * <p>
 * 枚举
 * <p>
 * 自动装箱拆箱(Autoboxing/Unboxing ):八大基本类型和它们的包装类能够自动的相互转换。将primitive类型转换成对应的wrapper类型：
 * Boolean、Byte、Short、Character、Integer、Long、Float、Double
 * <p>
 * 可变参数:本质仍然是用一个数组存储参数，只是java隐藏了这一过程。需要注意的是如果一个方法声明中含有可变参数，那必须放在最后一个位置。
 * <p>
 * 注解   Inherited表示该注解是否对类的子类继承的方法等起作用  Retention表示annotation是否保留在编译过的class文件中还是在运行时可读
 * <p>
 * foreach循环:（增强for、for/in）
 * <p>
 * 静态导入(Static Import):通过import类来使用类里的静态变量或方法（直接通过名字，不需要加上类名.）,简化了代码的书写。
 * <p>
 * printf输出格式化:（System.out.printf 支持%s %d等格式化输出）
 * <p>
 * 注解(Annotations):关键字@interface
 * <p>
 * 并发支持线程框架/数据结构: JUC       uncaught exception（可以抓住多线程内的异常） blocking queue(BlockingQueue)   JUC类库
 * <p>
 * Arrays工具类/StringBuilder/instrument
 * <p>
 * 线程模型和并发库:(java.util.concurrent)
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/12/5.
 * @Modified By:
 */
public class Java5Tes
{
    //泛型 (Generics)
    @Test
    public void testGenerics()
    {
        List<Integer> list = new ArrayList<Integer>();
    }

    /**
     * 通配符类型：避免unchecked警告，问号表示任何类型都可以接受
     *
     * @param list
     * @param out
     */
    public void printList(List<?> list, PrintStream out)
    {
        for (Iterator<?> i = list.iterator(); i.hasNext(); )
        {
            out.printf("第%d个元素是%s/n", i, i.next().toString());
        }
    }

    /**
     * 限制类型
     *
     * @param number
     * @param <A>
     * @return
     */
    public static <A extends Number> void printNumber(A... number)
    {
        for (int i = 0; i < number.length; i++)
        {
            out.println(number[i]);
        }
    }
}


@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface funcation
{

}
