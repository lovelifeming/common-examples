package com.zsm.commonexample.demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
public class Java7Test
{
    @Test
    public void test()
    {

        //二进制字面值前缀0b 或0B
        int i = 0b010;//10进制值为2
        int j = 0B010;

        //数字间的下划线不影响实际值
        int k = 1_234_567_890;//值为1234567890

        //增强泛型推断
        Map<String, List<String>> map = new HashMap<>();

        /**
         * switch中添加对String类型的支持
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
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line = br.readLine();
        }
        //单个catch中捕获多个异常类型
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
