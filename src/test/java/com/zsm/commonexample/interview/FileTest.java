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
        String path = "D:/home/nginx/file";
        new File(path).mkdir();
        new File(path).mkdirs();
    }
}
