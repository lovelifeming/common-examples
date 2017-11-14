package com.zsm.commonexample.util;

import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.IOException;
import java.util.*;


/**
 * Json转换工具,主要用到JSONObject，JSON，objectMapper
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 16:26.
 * @Modified By:
 */
public class JsonUtils
{
    /**
     * 将对象转化成JSON类型的字符串
     *
     * @param object
     * @return
     */
    public static String objectToJSON(Object object)
    {
        return objectToJSONObject(object).toString();
    }

    /**
     * 将对象转化成JSONObject
     *
     * @param object
     * @return
     */
    public static JSONObject objectToJSONObject(Object object)
    {
        return JSONObject.fromObject(object);
    }

    /**
     * 将对象转化成JSON的字符串
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.writeValueAsString(object);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("对象转化为json数据失败，可能是对象类型不匹配");
        }
    }

    /**
     * 将String对象转化成 T 对象
     *
     * @param json
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> valueType)
    {
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            return objectMapper.readValue(json, valueType);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Json转化为对象数据失败，可能是对象类型不匹配");
        }
    }

    /**
     * 将对象转化成指定对象集合
     *
     * @param json
     * @param elementClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<?> elementClass)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType type = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, elementClass);
        List<T> list = null;
        try
        {
            list = objectMapper.readValue(json, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("json数据转化为对象失败，可能是对象类型不匹配");
        }
        return null;
    }

    /**
     * 将对象转化成Map对象集合
     *
     * @param json
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<?> keyClass, Class<?> valueClass)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        MapType type = TypeFactory.defaultInstance().constructMapType(HashMap.class, keyClass, valueClass);
        Map<K, V> map = null;
        try
        {
            map = objectMapper.readValue(json, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("json数据转化为Map对象失败，可能是对象类型不匹配");
        }
        return map;
    }

}
