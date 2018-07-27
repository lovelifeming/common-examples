package com.zsm.commonexample.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:33.
 * @Modified By:
 */
public class ExceptionUtils
{
    /**
     * 获取异常Throwable里面的异常信息
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t)
    {

        try (StringWriter sw = new StringWriter(); PrintWriter ps = new PrintWriter(sw))
        {
            t.printStackTrace(ps);
            return sw.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
