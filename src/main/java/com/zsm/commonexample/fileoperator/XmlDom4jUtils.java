package com.zsm.commonexample.fileoperator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Dom4j XML文件解析
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/17 23:14.
 * @Modified By:
 */
public class XmlDom4jUtils
{
    /**
     * 获取多个节点对象
     *
     * @param path
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getXmlObjectList(String path, String node, Class<T> type)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            //通过文件路径读取并解析XML文档，SAXReader就是一个管道，用一个流的方式，把xml文件读出来
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(path));

            //通过解析xml字符串的读取，先将字符串转为XML
            //doc = DocumentHelper.parseText(path);
            //doc.setXMLEncoding("UTF-8");

            // 获取根节点
            Element root = doc.getRootElement();
            // 获取根节点的名称
            System.out.println("root name：" + root.getName());
            //根据节点名称获取元素
            Element element = root.element(node);
            //根据节点名称获取节点内容信息
            String desc = root.elementTextTrim("desc");
            if ("0".equals(desc))
            {
                System.out.println(element.getTextTrim());
                System.out.println(String.format("desc is %s,config is invalid!", desc));
                return null;
            }
            // 获取根节点下所有节点元素
            Iterator<Element> it = root.elementIterator(node);
            while (it.hasNext())
            {
                Element elementGroupService = it.next();
                T baseBean = XmlUtils.fromXmlToBean(elementGroupService, type);
                list.add(baseBean);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("xml file resolve error!");
        }
        return list;
    }

    /**
     * 获取单个对象
     *
     * @param path 文件路径
     * @param node 查询节点
     * @param type 对象类型
     * @param <T>
     * @return
     */
    public static <T> T getSingleObject(String path, String node, Class<T> type)
    {
        SAXReader saxReader = new SAXReader();
        Document document;
        T t = null;
        try
        {
            InputSource in = new InputSource(new FileReader(path));
            in.setEncoding("UTF-8");
            document = saxReader.read(in);
            Element element = document.getRootElement();
            Element el = element.element(node);
            t = XmlUtils.fromXmlToBean(el, type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("xml file resolve error!");
        }
        return t;
    }
}
