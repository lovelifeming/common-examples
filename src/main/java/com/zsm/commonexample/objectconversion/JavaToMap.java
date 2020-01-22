package com.zsm.commonexample.objectconversion;

import javassist.Modifier;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2019/10/15 16:57.
 * @Modified By :
 */
public class JavaToMap
{
    public static class BeanUtils
    {
        //  使用 org.apache.commons.beanutils 进行转换
        public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
            throws Exception
        {
            if (map == null)
                return null;
            Object obj = beanClass.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
            return obj;
        }

        public static Map<?, ?> objectToMap(Object obj)
        {
            if (obj == null)
                return null;
            return new org.apache.commons.beanutils.BeanMap(obj);
        }
    }


    public static class IntrospectorUtils
    {
        public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
            throws Exception
        {
            if (map == null)
                return null;
            Object obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors)
            {
                Method setter = property.getWriteMethod();
                if (setter != null)
                {
                    setter.invoke(obj, map.get(property.getName()));
                }
            }
            return obj;
        }

        public static Map<String, Object> objectToMap(Object obj)
            throws Exception
        {
            if (obj == null)
                return null;
            Map<String, Object> map = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors)
            {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0)
                {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        }
    }


    public static class Reflect
    {
        public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
            throws Exception
        {
            if (map == null)
                return null;
            Object obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod))
                {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
            return obj;
        }

        public static Map<String, Object> objectToMap(Object obj)
            throws Exception
        {
            if (obj == null)
            {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields)
            {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
            return map;
        }
    }
}
