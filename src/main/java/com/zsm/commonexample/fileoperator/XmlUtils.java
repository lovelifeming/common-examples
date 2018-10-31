package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.util.CommonUtils;
import com.zsm.commonexample.util.NumberUtils;
import org.dom4j.Element;

import java.lang.reflect.Field;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/28 0:48.
 * @Modified By:
 */
public class XmlUtils
{
    /**
     * 将数据转换对象
     *
     * @param content 转换的Element数据
     * @param type    转换的目标对象类型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T fromXmlToBean(Element content, Class<T> type)
        throws IllegalAccessException, InstantiationException
    {
        //得到Bean所定义的字段
        Field[] fields = type.getDeclaredFields();
        // 根据传入的Class动态生成Bean对象
        T t = type.newInstance();
        for (Field field : fields)
        {
            // 设置字段可访问（必须，否则报错）
            field.setAccessible(true);
            // 得到字段的属性名
            String name = field.getName();
            String value = content.elementTextTrim(name);
            if (value != null && !"".equals(value))
            {
                convertType(t, field, value);
            }
        }
        return t;
    }

    /**
     * 类型转换，包括Long，long，String，Double，double，Integer，int，Date
     *
     * @param t
     * @param field
     * @param value
     * @param <T>
     * @throws IllegalAccessException
     */
    public static <T> void convertType(T t, Field field, String value)
        throws IllegalAccessException
    {
        Class<?> cla = field.getType();
        // 根据字段的类型将值转化为相应的类型，并设置到生成的实例中。
        Object o = NumberUtils.typeTransfer(cla, value);
        field.set(t, o);
    }
}
