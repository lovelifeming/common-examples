package com.zsm.commonexample.util;

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
        StringWriter sw = new StringWriter();
        PrintWriter ps = new PrintWriter(sw);
        try
        {
            t.printStackTrace(ps);
            return sw.toString();
        }
        finally
        {
            ps.close();
        }
    }
}
