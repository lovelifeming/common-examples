package com.zsm.commonexample.fileoperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;


/**
 * @XmlType 将Java类或枚举类型映射到XML模式类型
 * @XmlAccessorType(XmlAccessType.FIELD) 控制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的
 * （static）、非瞬态的（由@XmlTransient标注）字段到XML。其他值还有XmlAccessType.PROPERTY和XmlAccessType.NONE.
 * @XmlAccessorOrder 控制JAXB 绑定类中属性和字段的排序。
 * @XmlRootElement 将Java类或枚举类型映射到XML元素。
 * @XmlElement 将Java类的一个属性映射到与属性同名的一个XML元素。
 * @XmlAttribute 将Java类的一个属性映射到与属性同名的一个XML属性。
 * @XmlElementWrapper 对于数组或集合（即包含多个元素的成员变量），生成一个包装该数组或集合的XML元素（称为包装器）,集合属性注解。
 * @XmlJavaTypeAdapter 使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法）， 以序列化Java类为XML。
 * 1.添加@XmlAccessorType(XmlAccessType.FIELD)标识的类中，整个类字段可被序列化，@XmlElement只能标记字段。
 * 2.未加@XmlAccessorType(XmlAccessType.FIELD)标识的类中，@XmlElement注解只能标记在get或者set方法上
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/5/1 0:46.
 * @Modified By:
 */
@SuppressWarnings("uncheck")
public class XmlMarshallerUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlMarshallerUtils.class);

    /**
     * 将XML文件转化为自定义数据对象
     *
     * @param xmlPath
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convertXmlToBean(String xmlPath, Class<T> type)
    {
        T t = null;
        try
        {
            File file = new File(xmlPath);
            JAXBContext context = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T)unmarshaller.unmarshal(file);
        }
        catch (JAXBException e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将自定义数据对象转化为XML字符串
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String convertBeanToXml(T t)
    {
        String result = null;
        try
        {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Writer writer = new StringWriter();
            marshaller.marshal(t, writer);
            result = writer.toString();
        }
        catch (JAXBException e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
