package com.zsm.commonexample.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


/**
 * Semaphore、CyclicBarrier、CountDownLatch 示例
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/25.
 * @Modified By:
 */
public class ConcurrentSCCDemo
{
    //region Semaphore 信号量控制同步线程数

    /**
     * permits 初始化信号量，控制并发数。 fair 公平竞争资源 true为公平，false为不公平
     */
    private static Semaphore semaphore = new Semaphore(3, true);

    public static void semaphoretest()
    {
        for (int i = 1; i <= 20; i++)
        {
            new Thread(() -> semaphore(), "thread" + i).start();
        }
    }

    private static void semaphore()
    {
        try
        {
            /**
             * semaphore.acquire()：用来请求一个信号量，该方法使信号量个数减 1；一旦没有可使用的信号量，
             * 即信号量个数变为负数时，再次调用该方法请求时就会阻塞，直到其他线程释放了信号量。
             */
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " enter and get the lock!");

        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        /** semaphore.release()：用来释放一个信号量，该方法使信号量个数加 1。*/
        semaphore.release();
        System.out.println(Thread.currentThread().getName() + " exit and release the lock!");
    }
    //endregion

    /**
     * region CountDownLatch 和 CyclicBarrier 都能够实现线程之间的等待.
     * CountDownLatch 是不能够重用的，而 CyclicBarrier 是可以重用的。
     */

    //region CountDownLatch 一般用于某个线程 A 等待若干个其他线程执行完任务之后，它才执行,类似join.
    public static void countDownLatch()
    {
        int count = 2;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++)
        {
            createThread(latch, "thread" + i);
        }
        try
        {
            System.out.println(Thread.currentThread().getName() + " entry execute code");
            latch.await();
            System.out.println(Thread.currentThread().getName() + " exit execute code");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private static void createThread(CountDownLatch latch, String threadName)
    {
        new Thread(() -> {
            try
            {
                System.out.println(Thread.currentThread().getName() + " entry execute code");
                Thread.sleep(new Random().nextInt(10));
                System.out.println(Thread.currentThread().getName() + " exit execute code");
                latch.countDown();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, threadName).start();
    }
    //endregion

    //region Thread join
    public static void joinThread()
        throws InterruptedException
    {
        Thread thread1 = new Thread(() -> {
            try
            {
                System.out.println(Thread.currentThread().getName() + " entry execute code");
                Thread.sleep(5);
                System.out.println(Thread.currentThread().getName() + " exit execute code");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "thread1");
        Thread thread2 = new Thread(() -> {
            try
            {
                System.out.println(Thread.currentThread().getName() + " entry execute code");
                Thread.sleep(5);
                System.out.println(Thread.currentThread().getName() + " exit execute code");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "thread2");
        thread1.start();
        thread2.start();
        System.out.println(Thread.currentThread().getName() + " entry execute code");
        thread1.join();
        thread2.join();
        System.out.println(Thread.currentThread().getName() + " exit execute code");
    }
    //endregion
}
