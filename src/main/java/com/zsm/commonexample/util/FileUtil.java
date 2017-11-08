package main.java.com.zsm.demo.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:23.
 * @Modified By:
 */
public static  class FileUtil
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
                closeable.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("FileUtil-->closeStream-->" + e.getStackTrace());
        }
    }

}
