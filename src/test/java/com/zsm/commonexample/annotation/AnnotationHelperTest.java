package com.zsm.commonexample.annotation;

import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/1 14:46.
 * @Modified By:
 */
public class AnnotationHelperTest
{
    private static String LOGIN_NAME = "com.zsm.commonexample.annotation.Login";

    @Test
    public void findClassAnnotation()
        throws ClassNotFoundException
    {
        LoginCheck loginCheck = (LoginCheck)AnnotationHelper.findClassAnnotation(LOGIN_NAME, LoginCheck.class);
        LoginCheck loginCheck1 = AnnotationHelper.findClassAnnotation(Login.class, LoginCheck.class);

        showCheck(loginCheck);
        showCheck(loginCheck1);
        Assert.assertEquals(loginCheck.isLogin(), false);
        Assert.assertEquals(loginCheck1.isLogin(), false);
    }

    @Test
    public void findMethodAnnotation()
        throws ClassNotFoundException
    {
        LoginCheck[] loginCheck = (LoginCheck[])AnnotationHelper.findMethodAnnotation(LOGIN_NAME, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(2, loginCheck.length);
    }

    @Test
    public void findMethodAnnotationByExist()
    {
        LoginCheck[] loginCheck = AnnotationHelper.findMethodAnnotationByExist(Login.class, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(2, loginCheck.length);
    }

    @Test
    public void findMethodAnnotationByInstance()
    {
        LoginCheck[] loginCheck = AnnotationHelper.findMethodAnnotationByIsInstance(Login.class, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(2, loginCheck.length);
    }

    public static void showCheck(LoginCheck... loginChecks)
    {
        for (LoginCheck loginCheck : loginChecks)
        {
            System.out.println(loginCheck.userType());
            System.out.println(loginCheck.isLogin());
            System.out.println(loginCheck.desc());
        }
        System.out.println("");
    }
}
