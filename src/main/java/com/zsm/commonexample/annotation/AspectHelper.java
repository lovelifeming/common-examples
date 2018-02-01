package com.zsm.commonexample.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/29 14:23.
 * @Modified By:
 */
public class AspectHelper
{
    private AspectHelper()
    {

    }

    /**
     * 根据类命名、注解类型查找类上的注解
     *
     * @param className
     * @param annotationClass
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T extends Annotation> Annotation findClassAnnotation(String className, Class<T> annotationClass)
        throws ClassNotFoundException
    {
        //反射使用类加载器加载类
        Class type = Class.forName(className);
        return findClassAnnotation(type, annotationClass);
    }

    /**
     * 根据类类型、注解类型查找类上的注解
     *
     * @param type
     * @param annotationClass
     * @param <T>
     * @param <U>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T, U extends Annotation> U findClassAnnotation(Class<T> type, Class<U> annotationClass)
        throws ClassNotFoundException
    {
        U annotation = null;
        //判断类上面的注解是否存在
        boolean isExist = type.isAnnotationPresent(annotationClass);
        if (isExist)
        {
            annotation = type.getAnnotation(annotationClass);
        }
        return annotation;
    }

    /**
     * 根据类类型、注解类型查找方法上的注解
     *
     * @param className
     * @param annotationClass
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T extends Annotation> Annotation[] findMethodAnnotation(String className, Class<T> annotationClass)
        throws ClassNotFoundException
    {
        Class type = Class.forName(className);
        return findMethodAnnotationByExist(type, annotationClass);
    }

    /**
     * 根据类类型、注解类型查找方法上的注解，使用了isAnnotationPresent();判断注解是否存在
     *
     * @param type
     * @param annotationClass
     * @param <T>
     * @param <U>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T, U extends Annotation> U[] findMethodAnnotationByExist(Class<T> type, Class<U> annotationClass)
        throws ClassNotFoundException
    {
        //获取类的所有方法
        Method[] method = type.getMethods();
        List<U> lists = new ArrayList<>();
        for (int i = 0; i < method.length; i++)
        {
            //判断当前方法上是否存在对应注解
            boolean isExist = method[i].isAnnotationPresent(annotationClass);
            if (isExist)
            {
                lists.add(method[i].getAnnotation(annotationClass));
            }
        }
        return lists.toArray((U[])Array.newInstance(annotationClass, lists.size()));
    }

    /**
     * 根据类类型、注解类型查找方法上的注解，使用了isInstance();判断注解是否是对应注解
     *
     * @param type
     * @param annotationClass
     * @param <T>
     * @param <U>
     * @return
     * @throws ClassNotFoundException
     */
    public static <T, U extends Annotation> U[] findMethodAnnotationByIsInstance(Class<T> type,
                                                                                 Class<U> annotationClass)
        throws ClassNotFoundException
    {
        Method[] method = type.getMethods();
        List<U> lists = new ArrayList<>();
        for (int i = 0; i < method.length; i++)
        {
            //获取方法上的所有的注解遍历
            Annotation[] annotations = method[i].getAnnotations();
            for (Annotation ann : annotations)
            {
                //判断annotationClass注解是否ann的实例,此外还有instanceof和isAssignableFrom
                if (annotationClass.isInstance(ann))
                {
                    lists.add((U)ann);
                }
            }
        }
        return lists.toArray((U[])Array.newInstance(annotationClass, lists.size()));
    }
}


/**
 * 注解测试类
 */
@LoginCheck(desc = "登陆权限", userType = "admin")
class Login
{
    @LoginCheck(desc = "管理员账号", userType = "admin", isLogin = true)
    public String getAllEmployee()
    {
        return "employee";
    }

    @LoginCheck(desc = "财务账号", userType = "salaryAdmin", isLogin = true)
    public String findEmloyeeSalary(String userId)
    {
        return "1000";
    }
}
