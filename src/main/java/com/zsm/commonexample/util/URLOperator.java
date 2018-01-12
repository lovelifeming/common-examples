package com.zsm.commonexample.util;

import org.apache.commons.lang.StringUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/10 9:53.
 * @Modified By:
 */
public class URLOperator
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
    public static String getRealFilePath(String path)
    {
        return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
    }

    /**
     * 获取HttpURL路径，/替换\\
     *
     * @param path
     * @return
     */
    public static String getHttpURLPath(String path)
    {
        return path.replace("\\", "/");
    }

    /**
     * 获取本类项目相对路径
     *
     * @return
     */
    public static String getPropertiesPath(String filePath)
    {
        String path = null;
        try
        {
            path = new CommonUtils().getClass().getResource("/").toURI().getPath() + filePath;
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        return getRealFilePath(path);
    }

    /**
     * 将Map转换成url params形式，name=text&psw=123456
     *
     * @param map
     * @return
     */
    public static String convertMapToUrlParams(Map<String, Object> map)
    {
        if (null == map)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry m : map.entrySet())
        {
            sb.append(m.getKey() + "=" + m.getValue());
            sb.append("&");
        }
        String result = sb.toString();
        if (result.endsWith("&"))
        {
            result = result.substring(0, result.lastIndexOf("&"));
        }

        return result;
    }

    /**
     * 将Url里面的参数获取出来转换成Map集合
     * 例如 https://www.baidu.com?username=admin&pws=123456
     * 解析出来username:admin,pws:123456存入map中
     *
     * @param url
     * @return
     */
    public static Map<String, Object> convertUrlParamsToMap(String url)
    {
        if (StringUtils.isBlank(url))
        {
            return null;
        }
        String params = truncateUrlParams(url);
        if (null == params || "".equals(params))
        {
            return new HashMap<>();
        }
        return convertURLParamToMap(params);
    }

    /**
     * 将url参数部分字符串转换为Map集合
     *
     * @param params
     * @return
     */
    public static Map<String, Object> convertURLParamToMap(String params)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] arrSplit = params.split("&");
        for (String s : arrSplit)
        {
            String[] temp = s.split("[=]");
            if (temp.length >= 2 && !"".equals(temp[0]))
            {
                result.put(temp[0], StringArrayUtils.convertStringArrayToString(temp, 1, temp.length - 1));
            }
        }
        return result;
    }

    /**
     * 截取url主机连接路径，http://localhost:8080/get/finduser5?username="admin"&password="123456"
     *
     * @param url
     * @return http://localhost:8080/get/finduser5
     */
    public static String truncateUrlHost(String url)
    {
        return truncateUrl(url, 0);
    }

    /**
     * 截取url参数部分，http://localhost:8080/get/finduser5?username="admin"&password="123456"
     *
     * @param url
     * @return username="admin"&password="123456"
     */
    public static String truncateUrlParams(String url)
    {
        return truncateUrl(url, 1);
    }

    private static String truncateUrl(String url, int index)
    {
        String result = "";
        if (StringUtils.isBlank(url))
        {
            return result;
        }

        String[] split = url.split("[?]");
        if (url.length() > 1 && split.length > 1)
        {
            if (index < 1)
            {
                result = split[index];
            }
            else
            {
                for (int i = 1; i < split.length; i++)
                {
                    result += split[i];
                }
            }
        }
        return result;
    }

}
