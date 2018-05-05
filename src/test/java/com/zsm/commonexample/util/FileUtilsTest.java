package com.zsm.commonexample.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/9 17:55.
 * @Modified By:
 */
public class FileUtilsTest
{
    @Test
    public void makeFilePathByDate()
        throws Exception
    {
        String path = this.getClass().getResource("/").getPath();
        String filePath = FileUtils.makeFilePathByDate(path);
        String fileName = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
        System.out.println(filePath);
        Assert.assertEquals(filePath, path + File.separator + fileName);
        Assert.assertEquals(new File(filePath).delete(), true);
    }
}
