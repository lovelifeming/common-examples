package com.zsm.commonexample.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Condition 能够更加精细的控制多线程的休眠与唤醒。对于同一个锁我们可以创建多个 Condition（即多个监视器），
 * 从而在不同的情况下使用不同的 Condition。
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/9/12.
 * @Modified By:
 */
public class ConditionDemo
{
    public static void invoke()
        throws InterruptedException
    {
        final Eat eat = new Eat();
        new Thread(() -> eat.washUp("Sam")).start();
        new Thread(() -> eat.restMeal("werewolf kill")).start();
        new Thread(() -> eat.eat("Jason")).start();
        new Thread(() -> eat.washHands()).start();

        Thread.sleep(1000);
        eat.returnHome("Jason");
    }

    static class Eat
    {
        private Lock lock = new ReentrantLock();

        private Condition cond1 = lock.newCondition();

        private Condition cond2 = lock.newCondition();

        private Condition cond3 = lock.newCondition();

        private Condition cond4 = lock.newCondition();

        public void returnHome(String name)
        {
            try
            {
                lock.lock();
                cond1.signal();
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("Return home eat " + name);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        }

        public void washHands()
        {
            try
            {
                lock.lock();
                cond1.await();
                TimeUnit.MICROSECONDS.sleep(1000000);
                System.out.println("Washing hands before meals");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                cond2.signal();
                lock.unlock();
            }
        }

        public void eat(String name)
        {
            try
            {
                lock.lock();
                cond2.await();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + " eating, temporarily unavailable");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                cond3.signal();
                lock.unlock();
            }
        }

        public void restMeal(String activity)
        {
            try
            {
                lock.lock();
                cond3.await();
                TimeUnit.NANOSECONDS.sleep(1000000000);
                System.out.println("After dinner done " + activity);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                cond4.signal();
                lock.unlock();
            }
        }

        public void washUp(String name)
        {
            try
            {
                lock.lock();
                cond4.await();
                //TimeUnit.MINUTES.sleep(1);
                System.out.println("Today," + name + " has washed the dishes.");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                cond1.signal();
                lock.unlock();
            }
        }
    }
}



