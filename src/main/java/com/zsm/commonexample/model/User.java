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

    private String address;

    private String telephone;

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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    @Override
    public String toString()
    {
        return name + age + address + telephone;
    }
}
