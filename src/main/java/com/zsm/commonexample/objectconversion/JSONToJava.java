package com.zsm.commonexample.objectconversion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2019/10/15 23:58.
 * @Modified By :
 */
public class JSONToJava
{
    //region   fastjson 与 JSONArray之间的转换

    /**
     * 将List集合对象转换为JSONArray
     */
    public static <T> JSONArray listToJSONArray(List<T> list)
    {
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return jsonArray;
    }

    /**
     * 将JSONArray 对象转换 List集合
     */
    public static <T> List<T> jsonArrayToList(JSONArray jsonArray, Class<T> claz)
    {
        List<T> list = JSONArray.parseArray(jsonArray.toJSONString(), claz);
        return list;
    }

    /**
     * 将JSONString 字符串转换List集合
     */
    public static <T> List<T> jsonStringToList(String json, Class<T> clz)
    {
        List<T> list = JSONObject.parseArray(json, clz);
        return list;
    }
    //endregion
}
