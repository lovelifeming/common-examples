package com.zsm.commonexample.model;

/**
 * 服务器配置信息类
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/17 23:01.
 * @Modified By:
 */
public class ServerHostInfo
{
    private String hostname;

    private String ip;

    private int port;

    private String user;

    private String password;

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

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

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
