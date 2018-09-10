package com.zsm.commonexample.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @JacksonXmlElementWrapper：可用于指定List等集合类，外围标签名；
 * @JacksonXmlProperty：指定包装标签名，或者指定标签内部属性名；
 * @JacksonXmlRootElement：指定生成xml根标签的名字；
 * @JacksonXmlText：指定当前这个值，没有xml标签包裹。
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/29 10:16.
 * @Modified By:
 */
/*  @JsonIgnoreProperties
此注解是类注解，作用是json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响     */
@JsonIgnoreProperties({"modify_time", "register_ip"})
public class JsonAnnotation
{
    private String name;

    private int age;

    private String sex;

    private String birthday;

    private String email;

    /*  @JsonIgnore
    此注解用于属性或者方法上（最好是属性上），作用和上面的@JsonIgnoreProperties一样。
    生成json 时不生成age 属性   */
    @JsonIgnore
    private String address;

    /*  @JsonFormat
    此注解用于属性或者方法上（最好是属性上），可以方便的把Date类型直接转化为我们想要的模式*/
    @JsonFormat(pattern = "yyyy-MM - dd HH-mm - ss")
    private String register_time;

    private String register_ip;

    private String modify_time;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /*@JsonSerialize
    此注解用于属性或者getter方法上，用于在序列化时嵌入我们自定义的代码，比如序列化一个double时在其后面限制两位小数点*/
    public int getAge()
    {
        return age;
    }

    /*@JsonDeserialize
    此注解用于属性或者setter方法上，用于在反序列化时可以嵌入我们自定义的代码，类似于上面的@JsonSerialize*/
    public void setAge(int age)
    {
        this.age = age;
    }

    /*@Transient
    @[email protected]映射,ORM框架将忽略该属性；
    如果一个属性并非数据库表的字段映射，就务必将其标示为@Transient，否则ORM框架默认其注解为@Basic*/
    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getRegister_time()
    {
        return register_time;
    }

    public void setRegister_time(String register_time)
    {
        this.register_time = register_time;
    }

    public String getRegister_ip()
    {
        return register_ip;
    }

    public void setRegister_ip(String register_ip)
    {
        this.register_ip = register_ip;
    }

    public String getModify_time()
    {
        return modify_time;
    }

    public void setModify_time(String modify_time)
    {
        this.modify_time = modify_time;
    }
}
