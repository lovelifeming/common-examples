package com.zsm.commonexample.model;

import java.io.Serializable;
import java.util.List;


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

    private List<String> tags;

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

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    @Override
    public String toString()
    {
        return "User{" +
               "name='" + name + '\'' +
               ", age=" + age +
               ", address='" + address + '\'' +
               ", telephone='" + telephone + '\'' +
               ", tags=" + tags +
               '}';
    }
}
