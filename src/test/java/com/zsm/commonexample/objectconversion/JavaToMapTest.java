package com.zsm.commonexample.objectconversion;

import com.zsm.commonexample.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2019/10/15 17:05.
 * @Modified By :
 */

public class JavaToMapTest
{
    @Test
    public void BeanUtils()
        throws Exception
    {
        User user = getUser();
        System.out.println("before user conversion:" + user);
        Map<?, ?> map = JavaToMap.BeanUtils.objectToMap(user);
        System.out.println("after map conversion:" + map);
        User user1 = (User)JavaToMap.BeanUtils.mapToObject((Map<String, Object>)map, User.class);
        System.out.println("after user1 conversion:" + user1);
        Assert.assertEquals(user.getName(), user1.getName());
    }

    @Test
    public void IntrospectorUtils()
        throws Exception
    {
        User user = getUser();
        System.out.println("before user conversion:" + user);
        Map<?, ?> map = JavaToMap.IntrospectorUtils.objectToMap(user);
        System.out.println("after map conversion:" + map);
        User user1 = (User)JavaToMap.IntrospectorUtils.mapToObject((Map<String, Object>)map, User.class);
        System.out.println("after user1 conversion:" + user1);
        Assert.assertEquals(user.getName(), user1.getName());
    }

    @Test
    public void Reflect()
        throws Exception
    {
        User user = getUser();
        System.out.println("before user conversion:" + user);
        Map<?, ?> map = JavaToMap.Reflect.objectToMap(user);
        System.out.println("after map conversion:" + map);
        User user1 = (User)JavaToMap.Reflect.mapToObject((Map<String, Object>)map, User.class);
        System.out.println("after user1 conversion:" + user1);
        Assert.assertEquals(user.getName(), user1.getName());
    }

    private User getUser()
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
        return user;
    }

}