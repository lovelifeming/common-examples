package com.zsm.commonexample.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 静态代理：若代理类在程序运行前就已经存在，那么这种代理方式被成为静态代理。
 * 静态代理中的代理类和委托类会实现同一接口或是派生自相同的父类
 * 静态代理可以通过聚合来实现，让代理类持有一个委托类的引用即可。
 * 动态代理：代理类在程序运行时创建的代理方式被成为动态代理。这种代理类并不是在Java代码中定义的，而是在运行时根据我们在Java代码中的
 * “指示”动态生成的。相比于静态代理，动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类的函数。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/27 10:30.
 * @Modified By:
 */
public class DynamicProxyer
{
    public static void invoke()
    {
        //创建动态代理类，注册实际操作者
        DynamicProxy dynamicProxy = new DynamicProxy(new Actor());
        //设置这句将会产生一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //获取代理类实例action
        Action action = (Action)Proxy.newProxyInstance(Action.class.getClassLoader(), new Class[] {Action.class},
            dynamicProxy);
        String result = action.execute("test");
        System.out.println(result);
    }
}


class DynamicProxy implements InvocationHandler
{
    private Action action;

    public DynamicProxy(Action action)
    {
        this.action = action;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        System.out.println("before");
        Object rebut = method.invoke(action, args);
        System.out.println("after");
        return rebut;
    }
}


class Actor implements Action
{
    @Override
    public String execute(String args)
    {
        //TODO 执行实际操作
        System.out.println("Actor execute real operate.");
        return "actor:" + args;
    }
}


interface Action
{
    String execute(String args);
}


