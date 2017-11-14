package com.zsm.commonexample.util;

import org.apache.commons.lang.StringUtils;

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
     * 将Map转换成url params形式，name=text&psw=123456
     *
     * @param map
     * @return
     */
    public static String writeMapAsUrlParams(Map<String, Object> map)
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
    public static Map<String, Object> writeUrlParamsAsMap(String url)
    {
        if (StringUtils.isBlank(url))
        {
            return null;
        }

        Map<String, Object> result = new HashMap<String, Object>();
        String params = truncateUrlParams(url);
        if (null == params)
        {
            return result;
        }
        String[] arrSplit = params.split("&");
        for (String s : arrSplit)
        {
            String[] temp = s.split("[=]");
            if (temp.length >= 2 && !"".equals(temp[0]))
            {
                result.put(temp[0], StringArrayUtils.toStringArrayAsString(temp, 1, temp.length - 1));
            }
        }

        return result;
    }

    /**
     * 截取url主机连接路径
     *
     * @param url
     * @return
     */
    public static String truncateUrlHost(String url)
    {
        return truncateUrl(url, 0);
    }

    /**
     * 截取url参数部分
     *
     * @param url
     * @return
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
