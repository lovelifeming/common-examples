package com.zsm.commonexample.objectconversion;

import com.alibaba.fastjson.JSONArray;
import com.zsm.commonexample.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2019/10/15 23:59.
 * @Modified By :
 */
public class JSONToJavaTest
{
    @Test
    public void listToJSONArray()
    {
        List<User> user = getUser();
        System.out.println("before conversion users: " + user);
        JSONArray array = JSONToJava.listToJSONArray(user);
        System.out.println("before once jsonarray: " + array);

        List<User> users = JSONToJava.jsonArrayToList(array, User.class);
        System.out.println("after once conversion users: " + users);

        List<User> list = JSONToJava.jsonStringToList(array.toString(), User.class);
        System.out.println("after two conversion users: " + list);
    }

    private List<User> getUser()
    {
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        user.setAddress("四川省成都市高新区188号");
        user.setTelephone("13973489238");
        user.setTags(new ArrayList<String>()
        {
            {add("美食");}

            {add("旅游");}

            {add("Code!@#$%^&*())__+");}
        });
        User user1 = new User();
        user1.setName("李四");
        user1.setAge(20);
        user1.setAddress("四川省成都市高新区007号");
        user1.setTags(new ArrayList<String>()
        {
            {add("看剧");}

            {add("Code1234567890.,/;'[]|");}
        });
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        return list;
    }
}