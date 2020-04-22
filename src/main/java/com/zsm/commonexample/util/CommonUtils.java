package com.zsm.commonexample.util;

import java.io.Closeable;
import java.io.IOException;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 22:58.
 * @Modified By:
 */
public class CommonUtils
{
    /**
     * 关闭继承于Closeable接口的流对象
     *
     * @param closeables
     */
    public static void closeStream(Closeable... closeables)
    {
        for (Closeable closeable : closeables)
        {
            try
            {
                if (closeable != null)
                {
                    closeable.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println("关闭流对象closeStream-->" + e.getMessage());
            }
        }
    }

    /**
     * 判断对象是否是基本数据类型或者基本数据类型包装类
     */
    public static boolean isSimpleType(Class<?> clazz)
    {
        if (clazz == String.class)
        {
            return true;
        }
        else if (clazz == int.class || clazz == Integer.class)
        {
            return true;
        }
        else if (clazz == long.class || clazz == Long.class)
        {
            return true;
        }
        else if (clazz == boolean.class || clazz == Boolean.class)
        {
            return true;
        }
        else if (clazz == byte.class || clazz == Byte.class)
        {
            return true;
        }
        else if (clazz == char.class || clazz == Character.class)
        {
            return true;
        }
        else if (clazz == short.class || clazz == Short.class)
        {
            return true;
        }
        else if (clazz == float.class || clazz == Float.class)
        {
            return true;
        }
        else if (clazz == double.class || clazz == Double.class)
        {
            return true;
        }
        return false;
    }

}
