package com.zsm.commonexample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.xml.bind.annotation.XmlElement;


/**
 * 服务器配置信息类
 *
 * @JsonIgnoreProperties #类注解，作用是json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响。
 * @JsonIgnore #用于属性或者方法上，作用和上面的@JsonIgnoreProperties一样
 * @JsonFormat #用于属性或者方法上，可以方便的把Date类型直接转化为我们想要的模式，比如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
 * @JsonSerialize #用于属性或者getter方法上，用于在序列化时嵌入我们自定义的代码
 * @JsonDeserialize #用于属性或者setter方法上，用于在反序列化时可以嵌入我们自定义的代码，类似于上面的@JsonSerialize
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/17 23:01.
 * @Modified By:
 */
//@XmlAccessorType(XmlAccessType.FIELD)
//JsonAutoDetect.Visibility.ANY : 表示所有字段都可以被发现, 包括private修饰的字段, 解决大小写问题
//JsonAutoDetect.Visibility.NONE : 表示get方法不可见,解决字段重复问题
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServerHostInfo
{
    private String hostname;

    private String ip;

    private int port;

    private String user;

    private String password;

    @XmlElement(name = "hostname")
    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public String getIp()
    {
        return ip;
    }

    @XmlElement(name = "ip")
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    @XmlElement(name = "port")
    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUser()
    {
        return user;
    }

    @XmlElement(name = "user")
    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    @XmlElement(name = "password")
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ServerHostInfo{" +
               "hostname='" + hostname + '\'' +
               ", ip='" + ip + '\'' +
               ", port=" + port +
               ", user='" + user + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
