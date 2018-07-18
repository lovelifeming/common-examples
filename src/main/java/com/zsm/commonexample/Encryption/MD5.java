package com.zsm.commonexample.Encryption;

import com.zsm.commonexample.util.CommonUtils;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;


/**
 * MD5(message-digest algorithm 5)(信息-摘要算法)缩写，是一个数据的数字指纹.即对一个任意长度的数据进行计算,产生一个唯一指纹号.
 * 广泛用于加密和解密技术，常用于文件校验。属于单向加密算法。
 * 特性：1.两个不同的数据,难以生成相同的指纹号。2.对于指定的指纹号,难以逆向计算出原始数据
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/2.
 * @Modified By:
 */
public class MD5
{
    /**
     * 验证MD5 明文是否相同
     *
     * @param sourceString 明文
     * @param ciphertext   密文
     * @return
     */
    public static boolean validate(String sourceString, String ciphertext)
    {
        return ciphertext.equals(encodeByMD5(sourceString));
    }

    /**
     * MD5签名, MD5是不可逆的加密算法，只可以加密不可以解密。
     *
     * @param bytes 明文字节
     * @return 加密字节数组
     * @throws Exception
     */
    public static byte[] encryptionMD5(byte[] bytes)
        throws Exception
    {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return md5.digest();
    }

    /**
     * MD5签名, MD5是不可逆的加密算法，只可以加密不可以解密。
     *
     * @param sourceString 加密明文
     * @return 十六进制加密密文
     */
    public static String encodeByMD5(String sourceString)
    {
        if (!StringUtils.isEmpty(sourceString))
        {
            try
            {
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要,获得密文
                byte[] bytes = md.digest(sourceString.getBytes("UTF-8"));
                // 把密文转换成十六进制的字符串形式
                String hexString = CommonUtils.byteArrayToHexString(bytes);
                return hexString.toUpperCase();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
