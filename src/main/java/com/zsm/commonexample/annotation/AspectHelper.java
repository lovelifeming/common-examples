package com.zsm.commonexample.annotation;

import java.lang.annotation.*;
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


/**
 * @Target是这个注解的作用域 TYPE(类接口), FIELD(字段声明), METHOD(方法声明), PARAMETER(参数声明),
 * CONSTRUCTOR(构造方法声明),LOCAL VARIABLE(局部变量声明),ANNOTATION_TYPE(注解),
 * PACKAGE(包),TYPE_PARAMETER(类型参数声明),TYPE_USE(使用一个类型)
 * @Retention:设置注解的生命周期 SOURCE(只在源码显示, 编译时丢弃), CLASS(编译时记录到class中, 运行时忽略),
 * RUNTIME(运行时存在, 可以通过反射读取)
 * @Inherited是一个标识性的元注解，它允许子注解继承它
 * @Documented，生成javadoc时会包含注解
 * @注解的语法: @<注解名>(<成员名1>=<成员值1>,<成员名1>=<成员值1>,…)
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/29 11:28.
 * @Modified By:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@interface LoginCheck
{
    //成员类型是受限制的，合法的类型包括基本的数据类型以及String,Class,Annotation,Enumeration等
    //如果注解只有一个成员，则成员名必须取名为value()，在使用时可以忽略成员名和赋值号（=）
    //注解类可以没有成员，没有成员的注解称为标识注解,默认值需要用 default 关键值指定
    String desc();

    String userType();

    boolean isLogin() default false;
}
