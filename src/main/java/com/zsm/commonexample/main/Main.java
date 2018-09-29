package com.zsm.commonexample.main;

import java.text.SimpleDateFormat;


/**
 * @Author: zengsm
 * @Description: Date: 2017-11-02
 * Time: 13:38
 * @Modified By:
 */
public class Main
{
    private static final String BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args)
        throws Exception
    {

    }

    public static void sop(Object... obj)
    {
        for (Object o : obj)
        {
            System.out.println(o);
        }
    }
}