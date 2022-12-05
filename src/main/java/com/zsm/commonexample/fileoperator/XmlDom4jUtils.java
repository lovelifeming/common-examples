package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.Main;
import com.zsm.commonexample.util.NumberUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Dom4j XML文件解析
 * dom解析是直接把整个xml做parse操作，转成了document对象，这会消耗很多内存资源。所以一般用于一些配置文件或者较小的xml文件。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/17 23:14.
 * @Modified By:
 */
public class XmlDom4jUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

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

    /**
     * 获取节点属性，生成对应的对象
     *
     * @param element
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getXmlAttribute(Element element, Class<T> type)
        throws RuntimeException
    {
        try
        {
            // 获得节点的所有属性
            List<Attribute> attributes = element.attributes();
            T t = type.newInstance();
            for (Attribute attribute : attributes)
            {
                // 获得节点属性名
                String attrName = attribute.getName();
                // 获得节点属性值
                String attrValue = attribute.getValue();
                // 通过属性名获取type对象中对应属性的setter方法
                Field field = type.getDeclaredField(attrName);
                // 获取属性类型
                Class cla = field.getType();
                //根据字段类型，把字符串转换为对应类型的值
                //XmlUtils.convertType(type,field,attrValue);
                Object o = NumberUtils.typeTransfer(cla, attrValue);

                //获得setter方法
                String methodName = "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
                Method method = type.getDeclaredMethod(methodName, cla);
                //调用setter方法赋值
                method.invoke(t, o);
            }
            return t;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            new RuntimeException(e.getMessage());
        }
        return null;
    }

    /**
     * 读取dom解析XML文件
     *
     * @param filePath
     */
    public static org.w3c.dom.Document readXml(String filePath)
    {
        try
        {
            //获取documentBuilder的工厂对象实例(newInstance)
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            //用工厂对象生产出一个DocumentBuilder
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //获取document
            org.w3c.dom.Document document = builder.parse(filePath);
            //获得Node节点或者元素
            NodeList nodeList = document.getChildNodes();
            org.w3c.dom.Element element = document.getDocumentElement();
            //遍历节点
            for (int i = 0, len = nodeList.getLength(); i < len; i++)
            {
                Node node = nodeList.item(i);
                String nodeName = node.getNodeName();
                String content = node.getTextContent();
                System.out.println("Name:" + nodeName + " content:" + content);
                NamedNodeMap attributes = node.getAttributes();
                for (int j = 0, length = attributes.getLength(); j < length; j++)
                {
                    Node attr = attributes.item(i);
                    String name = attr.getNodeName();
                    System.out.println();
                    NamedNodeMap nameMap = attr.getAttributes();
                    String textContent = attr.getTextContent();
                    System.out.println("Name:" + name + " content:" + textContent);
                }
                Node firstNode = node.getFirstChild();
                NodeList childs = node.getChildNodes();
            }
            document.normalize();
            return document;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
