package com.zsm.commonexample.model;

import java.io.Serializable;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/27 10:26.
 * @Modified By:
 */
public class User implements Serializable
{
    private static final long serialVersionUID = 23114L;

    private String name;

    private int age;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return name + age;
    }
}
