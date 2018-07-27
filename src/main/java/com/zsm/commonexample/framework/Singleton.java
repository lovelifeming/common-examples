package com.zsm.commonexample.framework;

/**
 * 懒汉式单例，饿汉式单例
 *
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/24.
 * @Modified By:
 */
public class Singleton
{
    //region 懒汉式单例
    private static Singleton singleton;

    private Singleton()
    {

    }

    public static Singleton getInstance()
    {
        if (singleton == null)
        {
            synchronized (Singleton.class)
            {
                if (singleton == null)
                {
                    singleton = new Singleton();
                    System.out.println("已经初始化完成，生成了Singleton实例");
                }
            }
        }
        return singleton;
    }
    //endregion

    //region 饿汉式单例
    /*private static Singleton singleton;

    private Singleton()
    {
    }

    public static Singleton getInstance()
    {
        if (singleton == null)
        {
            singleton = new Singleton();
        }
        return singleton;
    }*/
    //endregion
}
