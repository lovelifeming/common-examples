package com.zsm.commonexample.thread;

import java.util.Random;


/**
 * ThreadLocal是线程局部变量，为每一个使用该变量的线程都提供一个变量值的副本。使每一个线程都可以独立地改变自己的副本，
 * 而不会和其它线程的副本冲突。
 * ThreadLocal是为有且只保存一个独立于线程的变量，使在线程内部操作变量，而不受其他线程影响。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/12 13:57.
 * @Modified By:
 */
public class ThreadLocalDemo implements Runnable
{
    /*创建线程局部变量THREAD_LOCAL，用来保存每个线程单独的变量People*/
    private final static ThreadLocal<People> THREAD_LOCAL = new ThreadLocal<>();

    public static void invoke()
    {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        new Thread(threadLocalDemo, "threadA").start();
        new Thread(threadLocalDemo, "threadB").start();
    }

    @Override
    public void run()
    {
        showPeople();
    }

    public void showPeople()
    {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("This thread name is " + currentThreadName);
        Random random = new Random();
        int age = random.nextInt(100);
        System.out.println("This Thread " + currentThreadName + " age is" + age);
        People people = getPeople();
        people.setAge(age);
        System.out.println("Thread " + currentThreadName + " set age is " + age);
        try
        {
            Thread.sleep(age * 100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("Thread " + currentThreadName + " get age is " + getPeople().getAge());
    }

    private People getPeople()
    {
        /*获取当前线程THREAD_LOCAL里面存储的People变量，如果没有就添加*/
        People people = (People)THREAD_LOCAL.get();
        if (people == null)
        {
            people = new People();
            /*保存People变量到当前线程的THREAD_LOCAL中*/
            THREAD_LOCAL.set(people);
        }
        return people;
    }
}


class People
{
    private int age;

    private String name;

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
