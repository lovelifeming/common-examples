package com.zsm.commonexample.thread;

import static java.lang.System.err;
import static java.lang.System.out;
import static java.lang.Thread.UncaughtExceptionHandler;


/**
 * @Author: zengsm.
 * @Description: 捕获线程运行时异常
 * @Date:Created in 2018/12/5.
 * @Modified By:
 */
public class UncaughtExceptionHandlerDemo implements UncaughtExceptionHandler
{
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        err.printf("%s: %s at line %d of %s%n",
            t.getName(),
            e.toString(),
            e.getStackTrace()[0].getLineNumber(),
            e.getStackTrace()[0].getFileName());
    }

    public Thread getThread()
    {
        Thread thread = new Thread(() -> out.println("The Thread started run!"));
        thread.setName("UncaughtExceptionHandlerDemo test thread!");
        thread.setUncaughtExceptionHandler(new UncaughtExceptionHandlerDemo());
        return thread;
    }
}
