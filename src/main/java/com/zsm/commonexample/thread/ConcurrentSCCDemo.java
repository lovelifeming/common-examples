package com.zsm.commonexample.thread;

import java.util.concurrent.Exchanger;
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
    /**
     * permits 初始化信号量，控制并发数。 fair 公平竞争资源 true为公平，false为不公平
     */
    private static Semaphore semaphore = new Semaphore(3, true);

    public void semaphoretest()
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

}
