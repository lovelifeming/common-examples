package com.zsm.commonexample.util;

import com.fasterxml.jackson.databind.JavaType;
import com.zsm.commonexample.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2020/1/22 14:00.
 * @Modified By :
 */
public class JsonUtilsTest
{
    @Test
    public void testListToJSON()
    {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("def");
        list.add("hji");
        list.add("mnq");
        String listString = JsonUtils.listToJSON(list);
        Assert.assertEquals("[\"abc\",\"def\",\"hji\",\"mnq\"]", listString);
        System.out.println(listString);
    }

    @Test
    public void testGetCollectionType()
    {
        HashMap<String, User> map = new HashMap<>();
        User user = new User();
        user.setName("admin");
        user.setAge(18);
        map.put("admin", user);

        User user1 = new User();
        user1.setName("user");
        user1.setAge(20);
        map.put("user", user1);

        String toJson = JsonUtils.objectToJson(map);
        System.out.println(toJson);
        JavaType type = JsonUtils.getCollectionType(HashMap.class, String.class, User.class);
        System.out.println(type);
    }
}