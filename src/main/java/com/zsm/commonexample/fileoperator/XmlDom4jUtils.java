package com.zsm.commonexample.fileoperator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/17 23:14.
 * @Modified By:
 */
public class XmlDom4jUtils
{
    public static <T> List<T> getObjectList(String path, Class<T> type)
    {
        Document doc = null;
        List<T> list = new ArrayList<T>();
        try
        {
            // 读取并解析XML文档
            // SAXReader就是一个管道，用一个流的方式，把xml文件读出来
             SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
             doc = reader.read(new File(path));

            // 下面的是通过解析xml字符串的
            //doc = DocumentHelper.parseText(path); // 将字符串转为XML
            //doc.setXMLEncoding("UTF-8");

            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            String returnCode = rootElt.elementTextTrim("desc");
            if ("0".equals(returnCode))
            {
                System.out.println("后台数据返回有问题");
                return null;
            }
            Iterator<Element> it = rootElt.elementIterator("serverhost");// 获取根节点下所有serverhost
            while (it.hasNext())
            {
                Element elementGroupService = (Element)it.next();
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
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return t;
    }
}
