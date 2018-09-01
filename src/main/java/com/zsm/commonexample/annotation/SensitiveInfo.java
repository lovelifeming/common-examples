package com.zsm.commonexample.annotation;

import java.lang.annotation.*;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/27.
 * @Modified By:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveInfo
{
    SensitiveType value() default SensitiveType.NO_MASK;

    enum SensitiveType
    {
        /**
         * 证件号
         */
        ID_CARD,
        /**
         * 中文名
         */
        CHINESE_NAME,

        /**
         * 密码
         */
        PASSWORD,

        /**
         * 座机号
         */
        FIXED_PHONE,
        /**
         * 手机号
         */
        MOBILE_PHONE,
        /**
         * 地址
         */
        ADDRESS,
        /**
         * 电子邮件
         */
        EMAIL,
        /**
         * 银行卡
         */
        BANK_CARD,
        /**
         * 公司开户银行联号
         */
        CNAPS_CODE,

        /**
         * 不做脱敏
         */
        NO_MASK
    }
}
