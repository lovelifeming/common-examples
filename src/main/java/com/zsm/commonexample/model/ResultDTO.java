package com.zsm.commonexample.model;

import java.io.Serializable;


/**
 * Rpc 服务接口返回值.数据传输对象(DTO)(Data Transfer Object)
 *
 * @Author  zengsm.
 * @Description:
 * @Date:Created in 2019/2/27 10:24.
 * @Modified By:
 */

public class ResultDTO<T extends Serializable> implements Serializable
{
    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 模块信息
     */
    private T module;

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public T getModule()
    {
        return module;
    }

    public void setModule(T module)
    {
        this.module = module;
    }

    @Override
    public String toString()
    {
        return "ResultDTO{" +
               "errorMsg='" + errorMsg + '\'' +
               ", errorCode='" + errorCode + '\'' +
               ", success=" + success +
               ", module=" + module +
               '}';
    }
}
