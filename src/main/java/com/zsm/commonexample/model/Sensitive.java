package com.zsm.commonexample.model;

import com.zsm.commonexample.annotation.SensitiveInfo;
import com.zsm.commonexample.annotation.SensitiveToString;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/30.
 * @Modified By:
 */
public class Sensitive
{
    @SensitiveInfo(value = SensitiveInfo.SensitiveType.CHINESE_NAME)
    private String chinesename;

    @SensitiveInfo(value = SensitiveInfo.SensitiveType.PASSWORD)
    private String password;

    @SensitiveInfo(value = SensitiveInfo.SensitiveType.ID_CARD)
    private String idcard;

    @SensitiveInfo(value = SensitiveInfo.SensitiveType.MOBILE_PHONE)
    private String mobilephone;

    @SensitiveInfo(value = SensitiveInfo.SensitiveType.ADDRESS)
    private String address;

    @SensitiveInfo(value = SensitiveInfo.SensitiveType.EMAIL)
    private String email;

    public String getChinesename()
    {
        return chinesename;
    }

    public void setChinesename(String chinesename)
    {
        this.chinesename = chinesename;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getIdcard()
    {
        return idcard;
    }

    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    public String getMobilephone()
    {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone)
    {
        this.mobilephone = mobilephone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return new SensitiveToString(this, ToStringStyle.SHORT_PREFIX_STYLE, true).toString();
    }
}
