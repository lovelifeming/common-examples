package com.zsm.commonexample.util;

import com.zsm.commonexample.serializable.SerializableUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 数组列表项操作
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2019/1/7 16:16.
 * @Modified By:
 */
public class ArrayUtils
{
    /**
     * 数组与数组列表之间的转换
     */
    public static void convertArray()
    {
        ArrayList<String> list = buildArrayList();
        //1.数组列表 转 数组 toArray(T[] a)
        String[] array = list.toArray(new String[list.size()]);

        //2.数组列表 转 数组 toArray(),获得的是Object[]
        Object[] objects = list.toArray();

        //3.数组列表 转 数组 循环遍历数组列表
        int size = list.size();
        String[] each = new String[size];
        for (int i = 0; i < size; i++)
        {
            each[i] = list.get(i);
        }

        String[] arr = new String[] {"A", "C", "B", "D", "C",};
        //1.数组转数组列表 Stream.of
        ArrayList<String> collect = Stream.of(arr).collect(Collectors.toCollection(ArrayList::new));

        //2.数组转数组列表(只读)，结果集里面不能删除、添加操作，可以修改元素。因为该方法返回的是基于原数组的List视图
        List<String> asList = Arrays.asList(arr);

        //3.数组转数组列表,新建列表对象
        List<String> newList = new ArrayList<>(Arrays.asList(arr));

        //4.数组转数组列表,Collections.addAll() 拷贝
        List<String> collections = new ArrayList<String>(arr.length);
        Collections.addAll(collections, arr);

        //5.数组转数组列表,循环填充
        List<String> list1 = new ArrayList<String>(arr.length);
        for (int i = 0; i < arr.length; i++)
        {
            list1.add(arr[i]);
        }
    }

    /**
     * 浅拷贝和深拷贝示例
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void shallowCopyAndDeepCopy()
        throws IOException, ClassNotFoundException
    {
        ArrayList<String> list = buildArrayList();
        //1.如果集合元素是值类型，是深拷贝，如果集合元素包含引用类型，则是浅拷贝 addAll
        ArrayList<String> copy = new ArrayList<String>(list.size());
        copy.addAll(list);
        copy.set(0, "copy");

        //2.如果集合元素是值类型，是深拷贝，如果集合元素包含引用类型，则是浅拷贝 clone()
        List<String> copy1 = (List<String>)list.clone();
        copy1.set(1, "copy1");

        //4.递归遍历，新建对象填充，新建引用对象则是深拷贝
        //5.深拷贝，序列化创建新对象
        ArrayList<String> copy2 = SerializableUtils.deepCopy(list);
        copy2.set(2, "copye2");

        //6.本地方法拷贝数组
        String[] source = {"1", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
        String[] target = {"2", "12", "22", "32", "42", "52", "62", "72", "82", "92"};
        System.arraycopy(source, 2, target, 3, 4);
        //输出结果： 2 12 22 20 30 40 50 72 82 92
    }

    private static ArrayList<String> buildArrayList()
    {
        return new ArrayList<String>()
        {
            {add("AB");}

            {add("AB");}

            {add("BC");}

            {add("CD");}

            {add("BD");}
        };
    }
}
