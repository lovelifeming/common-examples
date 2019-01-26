package com.zsm.commonexample.demo;

/**
 * Java 11 正式发布 2018年9月26日,Java 大版本周期变化后的第一个长期支持版本（LTS版本，Long-Term-Support，持续支持到2026年9月）
 * 支持Unicode 10.0,在jdk10中是8.0
 * <p>
 * 本地变量类型推断
 * <p>
 * 字符串加强：增加了一系列字符串处理方法。
 * <p>
 * 集合加强(List/Set/Map)了 of 和 copyOf 方法。注意：使用of和copyOf创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，
 * 不然会报 java.lang.UnsupportedOperationException 异常。
 * <p>
 * InputStream 加强：添加了transferTo方法，可以用来将数据直接传输到 OutputStream。
 * <p>
 * 标准化HTTP Client
 * <p>
 * 编译器线程的延迟分配。添加了新的命令-XX:+UseDynamicNumberOfCompilerThreads动态控制编译器线程的数量
 * <p>
 * 新的垃圾收集器—ZGC。一种可伸缩的低延迟垃圾收集器(实验性)
 * <p>
 * Epsilon。一款新的实验性无操作垃圾收集器。Epsilon GC 只负责内存分配，不实现任何内存回收机制。这对于性能测试非常有用，可用于与其他GC对比成本和收益
 * <p>
 * Lambda参数的局部变量语法。java10中引入的var字段得到了增强，现在可以用在lambda表达式的声明中。
 * 如果lambda表达式的其中一个形式参数使用了var，那所有的参数都必须使用var
 * <p>
 * 简化运行源代码命令,合并编译、运行命令 java Demo.java
 * <p>
 * ============================移除项============================
 * 移除了com.sun.awt.AWTUtilities
 * 移除了sun.misc.Unsafe.defineClass，使用 java.lang.invoke.MethodHandles.Lookup.defineClass来替代
 * 移除了Thread.destroy()以及 Thread.stop(Throwable)方法
 * 移除了sun.nio.ch.disableSystemWideOverlappingFileLockCheck、sun.locale.formatasDefault属性
 * 移除了jdk.snmp模块
 * 移除了javafx，openjdk估计是从 java10版本就移除了，oracle jdk10还尚未移除javafx，而java11版本则oracle的jdk版本也移除了javafx
 * 移除了Java Mission Control，从JDK中移除之后，需要自己单独下载
 * 移除了这些Root Certificates ：Baltimore Cybertrust Code Signing CA，SECOM ，AOL and Swisscom
 * <p>
 * ============================废弃项============================
 * 废弃了Nashorn JavaScript Engine
 * 废弃了 -XX+AggressiveOpts 选项
 * -XX:+UnlockCommercialFeatures 以及 -XX:+LogCommercialFeatures 选项也不再需要
 * 废弃了Pack200工具及其API
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/12/5.
 * @Modified By:
 */
public class Java11Test
{
}
