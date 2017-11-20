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
        try
        {
            for (Closeable closeable : closeables)
            {
                if (closeable != null)
                {
                    closeable.close();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("FileUtils-->closeStream-->" + e.getStackTrace());
        }
    }
}
