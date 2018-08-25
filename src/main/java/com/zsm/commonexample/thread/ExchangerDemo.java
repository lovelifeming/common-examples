package com.zsm.commonexample.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Exchanger 可以在两个线程之间交换数据，只能是2个线程，他不支持更多的线程之间互换数据
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/25.
 * @Modified By:
 */
public class ExchangerDemo
{
    public static void exchanger()
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        Exchanger exchanger = new Exchanger();
        executor.execute(new Runnable()
        {
            String data = "thread one";

            @Override
            public void run()
            {
                doExchangerData(data, exchanger);
                System.out.println(Thread.currentThread().getName() + " thread one 的数据是 " + data);
            }
        });
        executor.execute(new Runnable()
        {
            String data = "thread two";

            @Override
            public void run()
            {
                doExchangerData(data, exchanger);
                System.out.println(Thread.currentThread().getName() + " thread two 的数据是 " + data);
            }
        });
        executor.shutdown();
    }

    private static void doExchangerData(String data, Exchanger exchanger)
    {
        System.out.println(Thread.currentThread().getName() + "交换前的数据 " + data);
        try
        {
            Thread.sleep((long)(Math.random() * 1000));
            String temp = (String)exchanger.exchange(data);
            System.out.println(Thread.currentThread().getName() + "交换后的数据 " + temp);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
