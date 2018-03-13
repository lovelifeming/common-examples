package com.zsm.commonexample.annotation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/1 14:46.
 * @Modified By:
 */
@Ignore
public class AnnotationHelperTest
{
    private static String LOGIN_NAME = "com.zsm.commonexample.annotation.Login";

    @Test
    public void findClassAnnotation()
        throws Exception
    {
        LoginCheck lc = (LoginCheck)AnnotationHelper.findClassAnnotation(LOGIN_NAME, LoginCheck.class);

        LoginCheck loginCheck = AnnotationHelper.findClassAnnotation(Login.class, LoginCheck.class);
        showCheck(lc);
        showCheck(loginCheck);
        Assert.assertEquals(lc.isLogin(), false);
        Assert.assertEquals(loginCheck.isLogin(), false);
    }

    @Test
    public void findMethodAnnotation()
        throws Exception
    {
        LoginCheck[] loginCheck = (LoginCheck[])AnnotationHelper.findMethodAnnotation(LOGIN_NAME, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(loginCheck[0].userType(), "salaryAdmin");
    }

    @Test
    public void findMethodAnnotationByExist()
    {
        LoginCheck[] loginCheck = AnnotationHelper.findMethodAnnotationByExist(Login.class, LoginCheck.class);
        showCheck(loginCheck);
        Assert.assertEquals(loginCheck[0].userType(), "salaryAdmin");
    }

    @Test
    public void findMethodAnnotationByInstance()
    {
        LoginCheck[] loginCheck = AnnotationHelper.findMethodAnnotationByIsInstance(Login.class, LoginCheck.class);
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
