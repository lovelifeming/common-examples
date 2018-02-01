package com.zsm.commonexample.annotation;

import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/1 14:46.
 * @Modified By:
 */
public class AspectHelperTest
{
    private static String LOGIN_NAME = "com.zsm.commonexample.annotation.Login";

    @Test
    public void findClassAnnotation()
        throws Exception
    {
        LoginCheck lc = (LoginCheck)AspectHelper.findClassAnnotation(LOGIN_NAME, LoginCheck.class);

        LoginCheck loginCheck = AspectHelper.findClassAnnotation(Login.class, LoginCheck.class);
        showCheck(lc);
        showCheck(loginCheck);
        Assert.assertEquals(lc.isLogin(), false);
        Assert.assertEquals(loginCheck.isLogin(), false);
    }

    @Test
    public void findMethodAnnotation()
        throws Exception
    {
        LoginCheck[] loginCheck = (LoginCheck[])AspectHelper.findMethodAnnotation(LOGIN_NAME, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(loginCheck[0].userType(), "salaryAdmin");
    }

    @Test
    public void findMethodAnnotationByExist()
        throws Exception
    {
        LoginCheck[] loginCheck = AspectHelper.findMethodAnnotationByExist(Login.class, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(loginCheck[0].userType(), "salaryAdmin");
    }

    @Test
    public void findMethodAnnotationByInstance()
        throws Exception
    {
        LoginCheck[] loginCheck = AspectHelper.findMethodAnnotationByIsInstance(Login.class, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(loginCheck[1].userType(), "admin");
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
