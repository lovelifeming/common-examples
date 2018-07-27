package com.zsm.commonexample.framework;

import net.sf.cglib.beans.BeanGenerator;

import java.util.Iterator;
import java.util.Map;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/27.
 * @Modified By:
 */
public class BeanGenerators
{
    /**
     * 动态生成JavaBean
     *
     * @param propertyMap
     * @return
     */
    public static Object generateBean(Map propertyMap)
    {
        BeanGenerator bean = new BeanGenerator();
        Iterator iterator = propertyMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry next = (Map.Entry)iterator.next();
            bean.addProperty((String)next.getKey(), (Class)next.getValue());
        }
        return bean.createClass();
    }
}
