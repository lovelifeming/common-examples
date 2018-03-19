package com.zsm.commonexample.framework;

/**
 * 枚举单例
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/14.
 * @Modified By:
 */
public enum SingletonEnum
{
    //枚举项，可以包含构造参数和包级方法
    THREAD("thread", 1)
        {
            public String apply()
            {
                return DESCRIBE + getTypeName() + getCount();
            }

            double calArea(int value)
            {
                return value * Math.PI;
            }
        },
    CLASS("class", 2)
        {
            @Override
            public String apply()
            {
                return DESCRIBE + getTypeName() + getCount();
            }
        },
    FIELD("field", 3)
        {
            @Override
            public String apply()
            {
                return DESCRIBE + getTypeName() + getCount();
            }
        };

    //枚举内部私有静态域
    private static final String DESCRIBE = "This is a singleton enum.";

    //枚举内部私有域
    private String typeName;

    private int count;

    SingletonEnum(String name, int count)
    {
        this.typeName = name;
        this.count = count;
    }

    //枚举抽象方法,相当于在接口或抽象父类中定义抽象方法
    public abstract String apply();

    //获取枚举域
    public String getTypeName()
    {
        return typeName;
    }

    public int getCount()
    {
        return count;
    }

    public String toString()
    {
        return typeName + count;
    }
}
