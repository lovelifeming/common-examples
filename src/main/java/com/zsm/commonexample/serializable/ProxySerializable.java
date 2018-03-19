package com.zsm.commonexample.serializable;

import java.io.*;


/**
 * 序列化代理模式即实例的反序列化交给构造函数来完成。
 * 实现了序列化的类。在反序列化时，实例的创建是由readObject方法来完成的。这是一个不同于构造函数的创建类实例的通道，
 * 因此在构造函数中的状态约束条件在readObject中也得一条条的实现。实现Serializable接口, 会增加出错和出现安全问题的可能性,
 * 因为它开放了实例的另一种来源---反序列化，采用序列化代理模式可以避免风险。
 * <p>
 * 1. 序列化Person时, 会调用调用writeReplace()生成一个PersonProxy对象, 然后对此对象进行序列化
 * 2. 反序列化时, 会调用PersonProxy的readResolve()方法生成一个Person对象, 最后返回此对象的拷贝
 * 3. 因此, Person类的序列化工作完全交给PersonProxy类, 正如此模式的名称所表达的一样
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/15.
 * @Modified By:
 */
public class ProxySerializable implements Serializable
{
    public static void invoke()
        throws IOException, ClassNotFoundException
    {
        Person source = new Person("张三@$@", 18, "唱歌|游泳&&跑步");
        byte[] bytes = SerializableUtils.objectSerializableToBytes(source);
        System.out.println(source);
        Person target = SerializableUtils.deserializationToObject(bytes);
        System.out.println(target);
        System.out.println("equals :"+source.equals(target));
    }
}


class Person implements Serializable
{
    private final String name;

    private final int age;

    private final String hobby;

    public Person(String name, int age, String hobby)
    {
        //约束条件
        if (age < 0 || age > 100)
        {
            throw new IllegalArgumentException("age is illegality!");
        }
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getHobby()
    {
        return hobby;
    }

    //内部代理类
    public static class PersonProxy implements Serializable
    {
        private final String name;

        private final int age;

        private final String hobby;

        public PersonProxy(Person person)
        {
            this.name = person.getName();
            this.age = person.getAge();
            this.hobby = person.getHobby();
        }

        private Object readResolve()
        {
            Person person = new Person(name, age, hobby);
            return person;
        }
    }

    private Object writeReplace()
    {
        //readObject的时候是调用, PersonProxy的readResolve()
        return new PersonProxy(this);
    }

    //此方法不会执行,
    private void writeObject(ObjectOutputStream oos)
        throws IOException
    {
        oos.writeChars(name);
        oos.write(age);
        oos.writeChars(hobby);
    }

    //防止攻击者伪造数据, 企图违反约束条件 (如: 违反年龄约束)
    private void readObject(ObjectInputStream ois)
        throws InvalidObjectException
    {
        throw new InvalidObjectException("PersonProxy required");
    }

    public String toString()
    {
        return name + " " + age + " " + hobby;
    }
}
