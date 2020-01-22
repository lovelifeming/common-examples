package com.zsm.commonexample.encryption;

import com.sun.jersey.core.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2020/1/22 14:37.
 * @Modified By :
 */
public class CBC
{
    /**
     * CBC加密
     *
     * @param key  密钥
     * @param iv   IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] data, String key, String iv)
        throws Exception
    {
        byte[] bytes = Base64.decode(key);
        DESedeKeySpec spec = new DESedeKeySpec(bytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bout = cipher.doFinal(data);
        return bout;
    }

    /**
     * CBC解密
     *
     * @param key  密钥
     * @param iv   IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] data, String key, String iv)
        throws Exception
    {
        byte[] bytes = Base64.decode(key);
        DESedeKeySpec spec = new DESedeKeySpec(bytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bout = cipher.doFinal(data);
        return bout;
    }
}
