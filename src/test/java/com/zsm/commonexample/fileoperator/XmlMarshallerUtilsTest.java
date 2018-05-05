package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.model.Root;
import com.zsm.commonexample.model.ServerHostInfo;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/5/4 0:01.
 * @Modified By:
 */
public class XmlMarshallerUtilsTest
{
    @Test
    public void convertXmlToBean()
    {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                      File.separator + "resource" + File.separator + "ServerHostInfo.xml";
        Root root = XmlMarshallerUtils.convertXmlToBean(path, Root.class);
        Assert.assertEquals("ServerHost", root.getDescription());
    }

    @Test
    public void convertBeanToXml()
    {
        Root root = new Root();
        root.setDesc(2);
        root.setDescription("test");
        List<ServerHostInfo> serverHostInfos = new ArrayList<>();
        ServerHostInfo hostInfo = new ServerHostInfo();
        hostInfo.setUser("admin");
        hostInfo.setPort(8080);
        hostInfo.setPassword("tets");
        hostInfo.setHostname("test");
        hostInfo.setIp("192.168.1.1");
        serverHostInfos.add(hostInfo);
        root.setHostInfoList(serverHostInfos);
        String xml = XmlMarshallerUtils.convertBeanToXml(root);
        Assert.assertEquals(311, xml.length());
    }
}