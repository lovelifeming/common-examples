package com.zsm.commonexample.annotation;

import java.lang.annotation.*;


/**
 * @Target是这个注解的作用域 TYPE(类接口), FIELD(字段声明), METHOD(方法声明), PARAMETER(参数声明),
 * CONSTRUCTOR(构造方法声明),LOCAL VARIABLE(局部变量声明),ANNOTATION_TYPE(注解),
 * PACKAGE(包),TYPE_PARAMETER(类型参数声明),TYPE_USE(使用一个类型)
 * @Retention:设置注解的生命周期 SOURCE(只在源码显示, 编译时丢弃), CLASS(编译时记录到class中, 运行时忽略), RUNTIME(运行时存在,可以通过反射读取)
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
public @interface LoginCheck
{
    //成员类型是受限制的，合法的类型包括基本的数据类型以及String,Class,Annotation,Enumeration等
    //如果注解只有一个成员，则成员名必须取名为value()，在使用时可以忽略成员名和赋值号（=）
    //注解类可以没有成员，没有成员的注解称为标识注解,默认值需要用 default 关键值指定
    String desc();

    String userType();

    boolean isLogin() default false;
}
