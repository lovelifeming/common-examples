package com.zsm.commonexample.interview;

import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/11/2.
 * @Modified By:
 */
public class StringTest
{
    /**
     * 变量命名规则:以字母、下划线(_)或者美元符($)开头，后面跟字母、下划线、美元符、数字，变量名对大小写敏感，无长度限制
     * 不能以数字开头，不能是Java关键字
     */
    @Test
    public void definitionName()
    {
        String $aa = "abcd";
        //String new = "bdef";/* 非法命名，不能以关键字命名 */
        int ab = 12;
        //int 2ab = 12;/* 非法命名，不能以数字开头命名*/
        double _ac = 3.14;
        float $_bc = 1.2f;
        boolean bl = true;
        Boolean _$ = new Boolean("0");
        //字符串和数字类型相加"+"时，自动转换为字符串连接
        String str = $aa + ab + _ac + $_bc + bl + _$;

        System.out.println("字符串和数字相加：" + str);
    }
}
