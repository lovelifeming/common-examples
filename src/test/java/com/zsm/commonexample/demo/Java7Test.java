package com.zsm.commonexample.demo;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 二进制前缀0b或者0B。整型（byte, short, int, long）可以直接用二进制表示
 * <p>
 * <p>
 * 数字字面量的改进 / 数值可加下划。用下划线连接整数提升其可读性，自身无含义，不可用在数字的起始和末尾
 * <p>
 * switch 支持String类型
 * <p>
 * 泛型实例化类型自动推断
 * <p>
 * try-with-resources语句
 * <p>
 * 单个catch中捕获多个异常类型（用| 分割）并通过改进的类型检查重新抛出异常
 * <p>
 * Path接口、DirectoryStream、Files、WatchService（重要接口更新）
 * <p>
 * fork/join framework
 * <p>
 * JSR203 NIO2.0（AIO）新IO的支持
 * <p>
 * JSR292与InvokeDynamic指令
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/12/5.
 * @Modified By:
 */
@Ignore
public class Java7Test
{
    @Test
    public void test()
    {

        //1.二进制字面值前缀0b 或0B
        int i = 0b010;//10进制值为2
        int j = 0B010;

        //2.数字间的下划线不影响实际值
        int k = 1_234_567_890;//值为1234567890

        //3.增强泛型推断
        Map<String, List<String>> map = new HashMap<>();

        /**
         * 4.switch中添加对String类型的支持
         * ①case仅仅有一种情况。直接转成if。
         * ②假设仅仅有一个case和default，则直接转换为if…else…。
         * ③有多个case。先将String转换为hashCode，然后相应的进行处理，JavaCode在底层兼容
         */
        String option = "";
        switch (option)
        {
            case "good":
                System.out.println("option good!");
                break;
            case "middle":
                System.out.println("option middle!");

            case "bad":
                System.out.println("option bad!");
                break;
            default:
                System.out.println("no option!");
        }

        String filePath = "";
        //5.try-with-resources:捕获多个异常,自动释放资源(资源类必须实现java.lang.AutoCloseable接口)
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line = br.readLine();
        }
        //6.单个catch中捕获多个异常类型
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 7.bytebuffer   filechannel
     *
     * @throws IOException
     */
    @Test
    public void openAndWrite()
        throws IOException
    {
        FileChannel channel = FileChannel.open(Paths.get("D:\\test.txt"), StandardOpenOption.CREATE,
            StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.putChar('A');

        CharBuffer charBuffer = buffer.asCharBuffer();
        charBuffer.put('B');
        System.out.println(buffer.getChar());

        charBuffer.put('C');
        buffer.getChar();
        buffer.flip();
        /**
         * 原始文件：111111111111111111111111111111111111
         * 写入文件： A B C111111111111111111111111111111
         */

        channel.write(buffer);
    }

    @Test
    public void readWriteAbsolute()
        throws IOException
    {
        FileChannel channel = FileChannel.open(Paths.get("test.txt"), StandardOpenOption.READ,
            StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        /**
         * 原始文件：111111111111111111111111111111111111
         *
         * 写入文件：111111 A B11111111111111111111111111
         */
        //一个字符占用两个字节
        ByteBuffer write = ByteBuffer.allocate(4).putChar('A').putChar('B');
        write.flip();
        channel.write(write, 6);

        ByteBuffer read = ByteBuffer.allocate(4);
        channel.read(read, 8);
        read.flip();
        char c = read.getChar();
        System.out.println(c);  //输出 B
    }

    @Test
    public void pathUsage()
    {
        Path path1 = Paths.get("root", "second", "third");
        Path path2 = Paths.get("root1", "second1", "third");

        Path resolve = path1.resolve(path2);    //  root\second\third\root1\second1\third
        Path resolveSibling = path1.resolveSibling(path2);  //  root\second\root1\second1\third
        Path relativize = path1.relativize(path2);  //  ..\..\..\root1\second1\third

        Path subpath = path1.subpath(1, 2); //  second
        boolean end = path1.endsWith(path2);    //  false
        boolean endsWith = path1.endsWith("third"); //true

        Path normalize = Paths.get("root//./.../../second1/test.txt").normalize();   //  root\second1\test.txt
        System.out.println("resolve:" + resolve);
        System.out.println("resolveSibling:" + resolveSibling);
        System.out.println("relativize:" + relativize);
        System.out.println("subpath:" + subpath);
        System.out.println("end:" + end);
        System.out.println("endsWith:" + endsWith);
        System.out.println("normalize:" + normalize);
    }

    @Test
    public void directoryStream()
    {
        // DirectoryStream
        Path path = Paths.get("");
        // 获取当前目录所有文件及文件夹
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*"))
        {
            for (Path p : stream)
            {
                System.out.println(p.normalize().toString());
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void filesUsage()
        throws IOException
    {
        Path file = Files.createFile(Paths.get("test.txt").toAbsolutePath());
        List<String> content = new ArrayList<>();
        content.add("This is ");
        content.add(" the site of");
        content.add(" love life ming");
        Path path = Files.write(file, content, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        long size = Files.size(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Files.copy(file, outputStream);
        System.out.println("file path: " + path.normalize());
        System.out.println("file size: " + size);
        List<String> readAllLines = Files.readAllLines(path, Charset.forName("UTF-8"));
        System.out.println("file content:");
        readAllLines.forEach(n -> System.out.println(n));
        /**
         * 文件内容
         * This is
         * the site of
         * love life ming
         */
        Files.delete(file);
    }

    /**
     * 目录监控：监控指定目录下文件及文件夹变化
     * 该类的对象就是操作系统原生的文件系统监控器。OS自带的文件系统监控器可以监控系统上所有文件的变化，这种监控是无需遍历、
     * 无需比较的，是一种基于信号收发的监控，因此效率一定是最高的，这里Java对其进行了包装。
     */
    @Test
    public void watchService()
        throws IOException, InterruptedException
    {
        WatchService service = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("").toAbsolutePath();
        path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
        while (true)
        {
            WatchKey take = service.take();
            for (WatchEvent<?> event : take.pollEvents())
            {
                Path context = (Path)event.context();
                context = path.resolve(context);
                long size = Files.size(context);
                System.out.println("create file: " + context + " and size: " + size);
            }
            take.reset();
        }
    }

}
