package com.zsm.commonexample.demo;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 支持在JVM上运行动态类型语言。在字节码层面支持了InvokeDynamic。
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2019-01-20.
 * @Modified By:
 */
public class Java7InvokeDynamicTest
{
    @Test
    public void testInvoke()
    {
        InvokeManager manager = new InvokeManager();

        Method method = manager.makeReflective();
        try
        {
            String args = "This is the traditional way of call!";
            method.invoke(manager, args);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }

        MethodHandle handle = manager.makeMethodHandle();
        try
        {
            String args = "This is the new way of call!";
            handle.invokeExact(manager, args);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}


class InvokeManager
{
    public InvokeManager()
    {
    }

    public void invoke(String message)
    {
        System.out.println("InvokeManager--->cancel method: " + message);
    }

    /**
     * 传统反射获取可调用方法
     */
    public Method makeReflective()
    {
        Method method = null;
        Class<?> argTypes = String.class;
        try
        {
            method = InvokeManager.class.getDeclaredMethod("invoke", argTypes);
            method.setAccessible(true);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 使用Java7的新api，MethodHandle
     * invoke virtual 动态绑定后调用 obj.xxx
     * invoke special 静态绑定后调用 super.xxx
     */
    public MethodHandle makeMethodHandle()
    {
        MethodHandle handle = null;
        MethodType type = MethodType.methodType(void.class, String.class);
        try
        {
            handle = MethodHandles.lookup().findVirtual(InvokeManager.class, "invoke", type);
        }
        catch (NoSuchMethodException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return handle;
    }
}
