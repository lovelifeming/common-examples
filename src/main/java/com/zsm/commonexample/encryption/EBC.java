package com.zsm.commonexample.encryption;

import com.sun.jersey.core.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;


/**
 * @Author :zengsm.
 * @Description :
 * @Date:Created in 2020/1/22 14:38.
 * @Modified By :
 */
public class EBC
{
    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] data, String key)
        throws Exception
    {
        byte[] bytes = Base64.decode(key);
        DESedeKeySpec spec = new DESedeKeySpec(bytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bout = cipher.doFinal(data);
        return bout;
    }

    /**
     * ECB解密,不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeECB(byte[] data, String key)
        throws Exception
    {
        byte[] bytes = Base64.decode(key);
        DESedeKeySpec spec = new DESedeKeySpec(bytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bout = cipher.doFinal(data);
        return bout;
    }
}
