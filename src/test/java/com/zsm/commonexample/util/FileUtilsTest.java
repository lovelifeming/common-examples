package com.zsm.commonexample.util;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/9 17:55.
 * @Modified By:
 */
@Ignore
public class FileUtilsTest
{
    @Test
    public void makeFilePathByDate()
        throws Exception
    {
        String path = "D:\\test";

        String filePath = FileUtils.makeFilePathByDate(path);
        System.out.println(filePath);
    }

}
