package com.zsm.commonexample.util;

import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/14 13:39.
 * @Modified By:
 */
public class ControllerUtils
{
    /**
     * 打印日志
     */
    private final static Logger logger = LoggerFactory.getLogger(ControllerUtils.class);

    /**
     * 将参数写入到Response里面，响应数据写入
     *
     * @param response
     * @param param
     */
    public static void writeValueToResponse(HttpServletResponse response, Object param)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String map = mapper.writeValueAsString(param);
            response.getWriter().write(map);
            response.getWriter().close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.info("BaseController---" + "writeValueToResponse---error" + e.getMessage());
        }
    }

    /**
     * 获取请求里面的参数字符串
     *
     * @param request
     * @return
     */
    public static String readValueFromRequest(HttpServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements())
        {
            sb.append(en.nextElement());
        }
        return sb.toString();
    }

    /**
     * 获取请求体里面的参数，返回Map集合，value集合以,分割
     *
     * @param request
     * @return
     */
    public static Map<String, String> readMapFromRequest(HttpServletRequest request)
    {
        HashMap<String, String> result = new HashMap<String, String>();
        Map<String, String[]> maps = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : maps.entrySet())
        {
            String key = entry.getKey();
            String[] valueArr = entry.getValue();
            String value = null;
            for (int i = 0; valueArr != null && i < valueArr.length; i++)
            {
                if (i == valueArr.length - 1)
                {
                    value += valueArr[i];
                }
                else
                {
                    value += valueArr[i] + ",";
                }
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 获取请求Request里面的参数,转换为JSONObject
     *
     * @param request
     * @return
     */
    public static JSONObject readValueToJSONObject(HttpServletRequest request)
    {
        JSONObject json = new JSONObject();
        Map<String, String[]> map = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String[]> m = iterator.next();
            json.put(m.getKey(), m.getValue());
        }
        return json;
    }
}
