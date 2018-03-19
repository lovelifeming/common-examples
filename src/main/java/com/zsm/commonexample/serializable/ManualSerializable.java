package com.zsm.commonexample.serializable;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.*;
import java.util.Date;
import java.util.List;


/**
 * 手动序列化和反序列对象,反序列化后进行验证ObjectInputValidation。
 * <p>
 * 序列化和反序列化的操作类型和顺序必须一致，例如：readObject()对应writeObject();  writeFloat对应readFloat()
 * Externalizable接口继承extends Serializable接口，而且在其基础上增加了两个方法：writeExternal()和readExternal()。
 * 这两个方法会在序列化和反序列化还原的过程中被自动调用，可以执行一些特殊的操作。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/13.
 * @Modified By:
 */
public class ManualSerializable implements Serializable, ObjectInputValidation
{
    //序列版本UID，可序列化类的一个唯一标识号，保持向前和向后兼容
    private static final long serialVersionUID = 146593631306924684L;

    private String name;

    private int age;

    private Date birthday;

    //transient 标记不自动序列化，可以手动序列化
    private transient String remark;

    private int length;

    private List<String> address;

    public ManualSerializable(String name, int age, Date birthday, String remark,
                              List<String> address)
        throws InvalidArgumentException
    {
        if (age < 0 || age > 100)
        {
            throw new InvalidArgumentException(
                new String[] {"invalid data:" + age + " should be more than 0 and less than 100"});
        }
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.remark = remark;
        this.address = address;
        this.length = address.size();
    }

    private void readObject(ObjectInputStream o)
        throws IOException, ClassNotFoundException
    {
        o.defaultReadObject();
        //手动反序列化赋值
        remark = (String)o.readObject();
        for (int i = 0; i < length; i++)
        {
            address.set(i, (String)o.readObject());
        }
        birthday = (Date)o.readObject();
    }

    private void writeObject(ObjectOutputStream o)
        throws IOException
    {
        o.defaultWriteObject();
        //手动序列化域
        o.writeObject(remark);
        for (String s : address)
        {
            o.writeObject(s);
        }
        o.writeObject(birthday);
    }

    @Override
    public void validateObject()
        throws InvalidObjectException
    {
        //验证构造函数,业务逻辑等校验
        if (age < 0 || age > 100)
        {
            throw new InvalidObjectException("invalid data:" + age + " should be more than 0 and less than 100");
        }
    }
}
