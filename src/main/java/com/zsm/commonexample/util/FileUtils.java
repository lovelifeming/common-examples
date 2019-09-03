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

    public static final String LINE_SEPARATOR_A = java.security.AccessController.doPrivileged(
        new sun.security.action.GetPropertyAction("line.separator"));

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

    /**
     * 获取文件相对路径,启动文件 /opt/rh/test.jar
     */
    public void showFilePath()
    {
        //  file:/opt/rh/test.jar!/com/zsm/commonexample/util/
        String path = FileUtils.class.getResource("").getPath();
        // 获取相对路径下路的资源文件 file:/opt/rh/test.jar!/com/zsm/commonexample/util/
        String path1 = this.getClass().getResource("").getPath();
        // 获取相对路径下路的资源文件 file:/opt/rh/test.jar!/
        String path11 = this.getClass().getResource("/").getPath();

        // /opt/rh/test.jar
        String path2 = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        // /opt/rh/test.jar
        String path3 = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

        //  test.jar    启动jar包命令参数
        String path4 = System.getProperty("java.class.path");

        // /opt/rh  启动命令目录
        String path5 = System.getProperty("user.dir");
        // /opt/rh  启动命令目录
        String path6 = new File("").getAbsolutePath();

        //  file:/opt/rh/test.jar!/
        String path7 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * 创建文件路径
     *
     * @param directory 文件目录
     * @param fileName  文件名称
     * @return 全文件路径
     */
    public static String createFilePath(String directory, String fileName)
    {
        if (!directory.endsWith(FILE_SEPARATOR))
        {
            directory += FILE_SEPARATOR;
        }
        File file = new File(directory);
        if (!file.isDirectory())
        { // 如果文件夹不存在就新建
            file.mkdirs();
        }
        return directory + fileName;
    }
}
