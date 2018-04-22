package com.zsm.commonexample.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


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
                System.out.println("FileUtils-->closeStream-->" + e.getStackTrace());
            }
        }
    }

    /**
     * 交换i和j的位置
     *
     * @param list
     * @param i
     * @param j
     */
    public static void swap(List<?> list, int i, int j)
    {
        swapE(list, i, j);
    }

    public static <E> void swapE(List<E> list, int i, int j)
    {
        list.set(i, list.set(j, list.get(i)));
    }

    /**
     * 类型转换，包括Long，long，String，Double，double，Integer，int，Date
     *
     * @param type
     * @param value
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> Object typeTransfer(Class<T> type, String value)
        throws RuntimeException
    {
        Object t = null;
        if (type.equals(Long.class) || type.equals(long.class))
        {
            t = Long.parseLong(value);
        }
        else if (type.equals(String.class))
        {
            t = value;
        }
        else if (type.equals(Double.class) || type.equals(double.class))
        {
            t = Double.parseDouble(value);
        }
        else if (type.equals(Integer.class) || type.equals(int.class))
        {
            t = Integer.parseInt(value);
        }
        else if (type.equals(Date.class))
        {
            t = Date.parse(value);
        }
        else
        {
            throw new RuntimeException(type.getName() + " type is invalid! value:" + value);
        }
        return t;
    }

    /**
     * 获取文件相对路径,启动文件 /opt/rh/test.jar
     */
    public void showFilePath()
    {
        //  file:/opt/rh/test.jar!/com/zsm/commonexample/util/
        String path = CommonUtils.class.getResource("").getPath();
        // 获取相对路径下路的资源文件 file:/opt/rh/test.jar!/com/zsm/commonexample/util/
        String path1 = this.getClass().getResource("").getPath();

        // /opt/rh/test.jar
        String path2 = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        // /opt/rh/test.jar
        String path3 = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

        //  test.jar
        String path4 = System.getProperty("java.class.path");

        // /opt/rh
        String path5 = System.getProperty("user.dir");
        // /opt/rh
        String path6 = new File("").getAbsolutePath();
    }
}
