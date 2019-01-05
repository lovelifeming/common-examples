package com.zsm.commonexample.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


/**
 * 系统属性,系统配置文件。如果想要获取更详细的系统信息,可以添加三方包Sigar.
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/26.
 * @Modified By:
 */
public class SystemProperty
{
    /**
     * 显示所有系统属性和环境变量
     */
    public static void showAllProperty()
    {
        Properties properties = System.getProperties();
        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<Object, Object> entry = iterator.next();
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }
        Map<String, String> map = System.getenv();
        for (map.entrySet().iterator(); iterator.hasNext(); )
        {
            Map.Entry<Object, Object> entry = iterator.next();
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }
    }

    /**
     * 显示系统环境属性
     */
    public static void showSystemProperties()
    {
        //Java运行时环境版本：1.8.0_181
        System.out.println("Java运行时环境版本：" + System.getProperty("java.version"));
        //Java运行时环境供应商：Oracle Corporation
        System.out.println("Java运行时环境供应商：" + System.getProperty("java.vendor"));
        //Java供应商的 URL：http://java.oracle.com/
        System.out.println("Java供应商的 URL：" + System.getProperty("java.vendor.url"));
        //java.home Java安装文件夹：D:\Program Files\Java\jdk1.8.0_181\jre
        System.out.println("java.home Java安装文件夹：" + System.getProperty("java.home"));
        //Java虚拟机规范版本：1.8
        System.out.println("Java虚拟机规范版本：" + System.getProperty("java.vm.specification.version"));
        //Java虚拟机规范供应商：Oracle Corporation
        System.out.println("Java虚拟机规范供应商：" + System.getProperty("java.vm.specification.vendor"));
        //Java虚拟机规范名称：Java Virtual Machine Specification
        System.out.println("Java虚拟机规范名称：" + System.getProperty("java.vm.specification.name"));
        //Java虚拟机实现版本：25.152-b16
        System.out.println("Java虚拟机实现版本：" + System.getProperty("java.vm.version"));
        //Java虚拟机实现供应商：Oracle Corporation
        System.out.println("Java虚拟机实现供应商：" + System.getProperty("java.vm.vendor"));
        //Java虚拟机实现名称：Java HotSpot(TM) 64-Bit Server VM
        System.out.println("Java虚拟机实现名称：" + System.getProperty("java.vm.name"));
        //Java运行时环境规范版本：1.8
        System.out.println("Java运行时环境规范版本：" + System.getProperty("java.specification.version"));
        //Java运行时环境规范供应商：Oracle Corporation
        System.out.println("Java运行时环境规范供应商：" + System.getProperty("java.specification.vendor"));
        //Java运行时环境规范名称：Java Platform API Specification
        System.out.println("Java运行时环境规范名称：" + System.getProperty("java.specification.name"));
        //Java类格式版本号：52.0
        System.out.println("Java类格式版本号：" + System.getProperty("java.class.version"));
        //Java类路径：D:\Program Files\Java\jdk1.8.0_181\jre\lib\charsets.jar;
        System.out.println("Java类路径：" + System.getProperty("java.class.path"));
        //加载库时搜索的路径列表：D:\Program Files\Java\jdk1.8.0_181\bin;C:\Windows\Sun\Java\bin;
        System.out.println("加载库时搜索的路径列表：" + System.getProperty("java.library.path"));
        //默认的临时文件路径：C:\Users\zengs\AppData\Local\Temp\
        System.out.println("默认的临时文件路径：" + System.getProperty("java.io.tmpdir"));
        //要使用的JIT编译器的名称：null
        System.out.println("要使用的JIT编译器的名称：" + System.getProperty("java.compiler"));
        //一个或多个扩展目录的路径：D:\Program Files\Java\jdk1.8.0_181\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
        System.out.println("一个或多个扩展目录的路径：" + System.getProperty("java.ext.dirs"));
        //操作系统的名称：Windows 10
        System.out.println("操作系统的名称：" + System.getProperty("os.name"));
        //操作系统的架构：amd64
        System.out.println("操作系统的架构：" + System.getProperty("os.arch"));
        //操作系统的版本：10.0
        System.out.println("操作系统的版本：" + System.getProperty("os.version"));
        //文件分隔符（在 UNIX 系统中是“/”）：\
        System.out.println("文件分隔符（在 UNIX 系统中是“/”）：" + System.getProperty("file.separator"));
        //路径分隔符（在 UNIX 系统中是“:”）：;
        System.out.println("路径分隔符（在 UNIX 系统中是“:”）：" + System.getProperty("path.separator"));
        //行分隔符（在 UNIX 系统中是“/n”）：\r\n
        System.out.println("行分隔符（在 UNIX 系统中是“/n”）：" + System.getProperty("line.separator"));
        //用户的账户名称：zengsm
        System.out.println("用户的账户名称：" + System.getProperty("user.name"));
        //用户的主目录：C:\Users\zengs
        System.out.println("用户的主目录：" + System.getProperty("user.home"));
        //用户的当前工作目录：D:\CommonExamples
        System.out.println("用户的当前工作目录：" + System.getProperty("user.dir"));
        //获取JVM虚拟机是32位还是64位
        System.out.println("JVM虚拟机是：" + System.getProperty("sun.arch.data.model") + "位");
    }

    /**
     * 显示Runtime运行时属性
     */
    public static void showRuntimeProperties()
    {
        Runtime runtime = Runtime.getRuntime();
        int radius = 1024 * 1024;
        //JVM可以使用的总内存:128974848
        System.out.println("JVM可以使用的总内存:" + runtime.totalMemory() / radius);
        //JVM可以使用的剩余内存:123484832
        System.out.println("JVM可以使用的剩余内存:" + runtime.freeMemory() / radius);
        //获取最大内存的字节数
        System.out.println("JVM可以使用的最大内存:" + Runtime.getRuntime().maxMemory() / radius);
        //JVM可以使用的处理器个数:4
        System.out.println("JVM可以使用的处理器个数:" + runtime.availableProcessors());
    }

    /**
     * 显示本地IP信息
     *
     * @throws UnknownHostException
     */
    public static void InetAddress()
        throws UnknownHostException
    {
        InetAddress address = InetAddress.getLocalHost();
        //本地ip地址:192.168.11.179
        System.out.println("本地ip地址:" + address.getHostAddress());
        //本地主机名:DESKTOP-E0KNBMF
        System.out.println("本地主机名:" + address.getHostName());
    }

    /**
     * 调用GC垃圾回收，退出JVM程序
     */
    public static void GCAndExit()
    {
        //调用GC垃圾收集，但没有办法保证GC的执行
        System.gc();    //底层是间接调用 Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();

        //直接退出或JVM.非零表示异常终止.0表示正常退出
        System.exit(0); //底层都是间接调用 Shutdown.halt0();
        Runtime.getRuntime().exit(1);
        Runtime.getRuntime().halt(1);
    }
}
