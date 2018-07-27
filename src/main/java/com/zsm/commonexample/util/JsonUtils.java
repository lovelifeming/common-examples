package com.zsm.commonexample.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 检查JSONObject字符串是否可以转换为JSONObject对象
     *
     * @param json
     * @return
     */
    public static boolean checkedJson(String json)
    {
        try
        {
            JSONObject jsonObject = JSONObject.fromObject(json);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

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
        try
        {
            return OBJECT_MAPPER.writeValueAsString(object);
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
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> beanType)
    {
        try
        {
            return OBJECT_MAPPER.readValue(json, beanType);
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
     * @param typeClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToListByCollectionType(String json, Class<?> typeClass)
    {
        CollectionType type = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, typeClass);
        List<T> list;
        try
        {
            list = OBJECT_MAPPER.readValue(json, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("json数据转化为对象失败，可能是对象类型不匹配");
        }
        return list;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToListByJavaType(String jsonData, Class<T> beanType)
    {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try
        {
            List<T> list = OBJECT_MAPPER.readValue(jsonData, javaType);
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    /**
     * 将xml字符串转换为JSON字符串
     *
     * @param xmlString xml字符串
     * @return
     */
    public static String xmlToJson(String xmlString)
    {
        XMLSerializer serializer = new XMLSerializer();
        JSON json = serializer.read(xmlString);
        return json.toString(1);
    }

    /**
     * 将xmlDocument转换为JSON对象
     *
     * @param document document对象
     * @return
     */
    public static String xmlToJson(Document document)
    {
        return xmlToJson(document.toString());
    }

    /**
     * 将JSON(数组)字符串转换成XML字符串
     *
     * @param jsonString
     * @return
     */
    public static String jsonToXml(String jsonString)
    {
        return new XMLSerializer().write(JSONSerializer.toJSON(jsonString));
    }
}
