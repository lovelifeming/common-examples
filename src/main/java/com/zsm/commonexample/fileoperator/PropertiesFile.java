package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.util.CommonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * 读写操作Properties文件
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/13 11:34.
 * @Modified By:
 */
public class PropertiesFile
{
    private static final Logger LOGGER = Logger.getLogger(PropertiesFile.class.getName());

    /**
     * 根据key查询Properties文件里面的对应键的值
     *
     * @param filePath 文件路径
     * @param key      键
     * @return
     */
    public static String getPropertyByKey(String filePath, String key)
    {
        String value = null;
        Properties properties = new Properties();
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(filePath);
            properties.load(fis);
            value = properties.getProperty(key);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->getPropertyByKey-->" + e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->getPropertyByKey-->" + e.getMessage());
        }
        finally
        {
            CommonUtils.closeStream(fis);
        }
        return value;
    }

    /**
     * 设置Properties文件里面的键值
     *
     * @param filePath 文件路径
     * @param key
     * @param value
     * @param comment  注解
     * @param append   是否追加
     * @return
     */
    public static boolean setProperty(String filePath, String key, String value, String comment, boolean append)
    {
        boolean flag = false;
        Properties properties = new Properties();
        FileOutputStream fis = null;

        try
        {
            fis = new FileOutputStream(filePath, append);
            properties.setProperty(key, value);
            properties.store(fis, comment);
            flag = true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->setProperty-->" + e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->setProperty-->" + e.getMessage());
        }
        finally
        {
            CommonUtils.closeStream(fis);
        }
        return flag;
    }

    /**
     * 获取Properties文件里面的信息，返回Map集合
     *
     * @param filePath 文件路径
     * @return
     */
    public static Map<String, String> getPropertyMap(String filePath)
    {
        HashMap<String, String> valueMap = new HashMap<String, String>();
        Properties properties = new Properties();
        FileInputStream fis = null;

        try
        {
            fis = new FileInputStream(filePath);
            properties.load(fis);
            Iterator it = properties.keySet().iterator();
            while (it.hasNext())
            {
                String temp = String.valueOf(it.next());
                valueMap.put(temp, properties.getProperty(temp));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->getPropertyMap-->" + e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.info("PropertiesFile-->getPropertyMap-->" + e.getMessage());
        }
        return valueMap;
    }
}
