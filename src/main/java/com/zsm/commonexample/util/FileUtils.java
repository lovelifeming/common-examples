package com.zsm.commonexample.util;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:23.
 * @Modified By:
 */
public class FileUtils
{

    /**
     * 获取当前系统文件分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 获取当前系统文件换行符
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

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

    /**
     * 根据日期创建文件夹
     *
     * @param filePath 文件夹路径
     * @return 根据日期创建的文件夹路径
     */
    public static String makeFilePathByDate(String filePath)
    {
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String s = df.format(date);
        String path = filePath + FILE_SEPARATOR + s;
        File file = new File(path);
        if (!file.exists())
        {
            //创建多级目录，mkdir只创建一级目录
            file.mkdirs();
        }
        return path;
    }

}
