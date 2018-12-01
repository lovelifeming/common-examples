package com.zsm.commonexample.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018-11-30.
 * @Modified By:
 */
public class CollectionTest
{

    /**
     * Arrays.asList集合不能添加删除元素
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAdd()
    {
        String[] array = new String[] {"刘一", "陈二", "张三", "李四"};
        //数组转换为集合后，返回一个final集合，可以修改里面的值，但不能对集合做添加删除操作
        List<String> list = Arrays.asList(array);
        list.set(0, "刘二");
        list.add("王五");
        list.remove(2);
        System.out.println("集合大小：" + list.size());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testRemove()
    {
        List<String> list = new ArrayList<>();
        list.add("王五");
        list.add("赵六");
        list.add("孙七");
        list.add("周八");
        list.add("吴九");
        list.add("郑十");
        for (String name : list)
        {
            if (name.equals("王五"))
            {
                //不能在for循环遍历中删除添加元素，只能在迭代器中对元素操作
                list.remove(name);
                list.add("十一");
            }
        }
    }
}
