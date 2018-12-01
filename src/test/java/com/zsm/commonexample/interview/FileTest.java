package com.zsm.commonexample.interview;

import org.junit.Test;

import java.io.File;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018-11-30.
 * @Modified By:
 */
public class FileTest
{
    @Test
    public void testMKDir()
    {
        String path = "D:/test/home/nginx/file";
        //只创建最后一级文件夹
        new File(path).mkdir();
        //递归创建文件夹
        new File(path).mkdirs();
    }
}
