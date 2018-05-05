package com.zsm.commonexample.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/5/3 22:16.
 * @Modified By:
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class Root
{
    //@XmlElement(name = "desc")
    private Integer desc;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "serverhost")
    private List<ServerHostInfo> hostInfoList;

    public Integer getDesc()
    {
        return desc;
    }

    public void setDesc(Integer desc)
    {
        this.desc = desc;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    // @XmlElementWrapper(name = "root")
    // @XmlElement(name = "serverhost")
    public List<ServerHostInfo> getHostInfoList()
    {
        return hostInfoList;
    }

    public void setHostInfoList(List<ServerHostInfo> hostInfoList)
    {
        this.hostInfoList = hostInfoList;
    }

    @Override
    public String toString()
    {
        return "Root{" +
               "desc='" + desc + '\'' +
               ", description='" + description + '\'' +
               ", hostInfoList=" + hostInfoList +
               '}';
    }
}
