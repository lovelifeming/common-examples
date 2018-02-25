package com.zsm.commonexample.main;

import java.io.Serializable;


/**
 * @Author: zengsm
 * @Description: Date: 2017-11-02
 * Time: 13:38
 * @Modified By:
 */
public class Main
{
    public static void main(String[] args)
        throws Exception
    {

    }

    public static void sop(Object obj)
    {
        System.out.println(obj);
    }
}


class User implements Serializable
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




