package com.zsm.commonexample.basis;

import com.zsm.commonexample.serializable.SerializableUtils;
import com.zsm.commonexample.util.ReadKeyboard;
import org.ietf.jgss.ChannelBinding;
import sun.nio.ch.ChannelInputStream;

import javax.xml.stream.events.DTD;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: zengsm
 * @Description: Date: 2017-11-02
 * Time: 13:38
 * @Modified By:
 */
public class DemoMain
{
    public static void main(String[] args)
        throws Exception
    {
        String url = "http://qaszfx-api.fsnip.com/szfx-api/api/getJdtj";

        String temp = "startDate=2017-01-01&endDate=2017-10-01&fzjgbh=1&includeFlag=true&ywlx=11&ywlx=12&state=1";
        String sourceUrl = "http://qaszfx-api.fsnip.com/szfx-api/api/getJdtj?startDate=2017-01-01&endDate=2017-10-01&fzjgbh=1&includeFlag=true&ywlx=11&ywlx=12&state=1";

//        Map<String, Object> map = new HashMap<>();
//        map.put("endDate", "2017-10-01");
//        map.put("fzjgbh", "1");
//        map.put("includeFlag", "true");
//        map.put("ywlx", "11");
//        map.put("ywlx", "12");
//        map.put("state", "1");
//
//        String da = URLOperator.writeMapAsUrlParams(map);
//        String t = URLEncoder.encode(sourceUrl, "UTF-8");
//        sop(t);
//        String un = URLDecoder.decode(t);
//        sop(un);
//        String result = HttpPostGetRequest.sendHttpGetClient(sourceUrl, null, "UTF-8");
//        sop(result);
//        String host = URLOperator.truncateUrlHost(sourceUrl);
//        String params = URLOperator.truncateUrlParams(sourceUrl);
//        sop(host);
//        sop(params);
//        Map<String, Object> str = URLOperator.writeUrlParamsAsMap(sourceUrl);
//        sop(str);

    }

    public static void sop(Object obj)
    {
        System.out.println(obj);
    }
}


class User implements Serializable
{
    private static final long serialVersionUID = 23114L;

    private String name;

    private int age;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return name + age;
    }
}




