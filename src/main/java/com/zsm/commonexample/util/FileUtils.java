package com.zsm.commonexample.util;

import java.io.Closeable;
import java.io.IOException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:23.
 * @Modified By:
 */
public class FileUtils
{

    /**
     * 获取系统文件分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 获取真实路径，用系统分隔符替换
     *
     * @param path
     * @return
     */
    public static String getRealFilePathBySystem(String path)
    {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    /**
     * 获取HttpURL路径，/ 替换\\
     *
     * @param path
     * @return
     */
    public static String getHttpURLPath(String path)
    {
        return path.replace("\\", "/");
    }

}
