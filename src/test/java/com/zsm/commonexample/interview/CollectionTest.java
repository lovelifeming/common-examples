package com.zsm.commonexample.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018-11-30.
 * @Modified By:
 */
public class CollectionTest
{

    @Test
    public void testAdd()
    {
        String[] array = new String[] {"刘一", "陈二", "张三", "李四"};
        List<String> list = Arrays.asList(array);
        list.add("王五");
        System.out.println("集合大小：" + list.size());
    }

    @Test
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
                //不能再循环遍历中不能操作元素，只能在迭代器中对元素操作
                list.remove(name);
            }
        }
    }
}
