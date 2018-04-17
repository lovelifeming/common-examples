package com.zsm.commonexample.fileoperator;

import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.Date;


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
            String node = content.elementTextTrim(name);
            if (node != null && !"".equals(node))
            {
                Class<?> cla = field.getType();
                // 根据字段的类型将值转化为相应的类型，并设置到生成的实例中。
                if (cla.equals(Long.class) || cla.equals(long.class))
                {
                    field.set(t, Long.parseLong(node));
                }
                else if (cla.equals(String.class))
                {
                    field.set(t, node);
                }
                else if (cla.equals(Double.class) || cla.equals(double.class))
                {
                    field.set(t, Double.parseDouble(node));
                }
                else if (cla.equals(Integer.class) || cla.equals(int.class))
                {
                    field.set(t, Integer.parseInt(node));
                }
                else if (cla.equals(Date.class))
                {
                    field.set(t, Date.parse(node));
                }
            }
        }
        return t;
    }
}
