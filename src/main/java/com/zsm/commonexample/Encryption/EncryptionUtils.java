package com.zsm.commonexample.Encryption;

import com.zsm.commonexample.util.CommonUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;


/**
 * SHA加解密,HMAC加解密,BASE64加密解密,自定义非运算加密,
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/2.
 * @Modified By:
 */
public class EncryptionUtils
{
    /**
     * MAC算法可选择多种算法
     * HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
     */
    private static final String H_MAC_KEY = "HmacMD5";

    //region SHA(Secure Hash Algorithm，安全散列算法）,较之MD5更为安全,也是一种单向算法。

    /**
     * SHA加密
     *
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static byte[] encryptionSHA(String plaintext)
        throws Exception
    {
        return encryptionSHA(plaintext.getBytes());
    }

    /**
     * SHA加密
     *
     * @param plaintext
     * @return
     * @throws Exception
     */
    public static String encryptionSHAHex(String plaintext)
        throws Exception
    {
        byte[] bytes = encryptionSHA(plaintext.getBytes());
        String hexString = CommonUtils.byteArrayToHexString(bytes);
        return hexString.toUpperCase();
    }

    /**
     * SHA加密
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static byte[] encryptionSHA(byte[] bytes)
        throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(bytes);
        return md.digest();
    }
    //endregion

    //region HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。消息鉴别码实现鉴别的原理是，
    // 用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。

    /**
     * HMAC加密
     *
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static String encryptionHMAC(String plaintext)
        throws Exception
    {
        byte[] bytes = encryptionHMAC(plaintext.getBytes());
        return CommonUtils.byteArrayToHexString(bytes);
    }

    /**
     * HMAC加密
     *
     * @param plaintext 明文字节
     * @return
     * @throws Exception
     */
    public static byte[] encryptionHMAC(byte[] plaintext)
        throws Exception
    {
        return encryptionHMAC(plaintext, initHMacKey());
    }

    /**
     * HMAC加密
     *
     * @param bytes 明文字节
     * @param key   HMAC密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptionHMAC(byte[] bytes, String key)
        throws Exception
    {
        SecretKey secretKey = new SecretKeySpec(decryptionBASE64(key), H_MAC_KEY);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(bytes);
    }

    /**
     * 生成HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initHMacKey()
        throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(H_MAC_KEY);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptionBASE64(secretKey.getEncoded());
    }
    //endregion

    //region BASE64 加密解密，把任意序列的8位字节描述为一种不易被人直接识别的形式。

    /**
     * BASE64加密
     *
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static String encryptionBASE64(String plaintext)
        throws Exception
    {
        return encryptionBASE64(plaintext.getBytes());
    }

    /**
     * BASE64加密
     *
     * @param bytes 明文字节
     * @return
     * @throws Exception
     */
    public static String encryptionBASE64(byte[] bytes)
        throws Exception
    {
        return (new BASE64Encoder()).encodeBuffer(bytes);
    }

    /**
     * BASE64解密
     *
     * @param ciphertext 密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptionBASE64(String ciphertext)
        throws Exception
    {
        return (new BASE64Decoder()).decodeBuffer(ciphertext);
    }

    /**
     * BASE64解密
     *
     * @param ciphertext 密文
     * @return
     * @throws Exception
     */
    public static String decryptionBASE64(String ciphertext, String charset)
        throws Exception
    {
        return new String((new BASE64Decoder()).decodeBuffer(ciphertext), charset);
    }
    //endregion

    //region 自定义非运算加密

    /**
     * 加密:可逆加密，加密明文
     *
     * @param plaintext 明文
     * @return
     * @throws Exception
     */
    public static String encryption(String plaintext)
        throws Exception
    {
        return nonOperation(plaintext);
    }

    /**
     * 解密：需要解密的密文
     *
     * @param ciphertext 密文
     * @return 明文
     * @throws Exception
     */
    public static String decryption(String ciphertext)
        throws Exception
    {
        return nonOperation(ciphertext);
    }

    /**
     * 采用二进制非运算加解密
     *
     * @param plaintext
     * @return
     */
    private static String nonOperation(String plaintext)
    {
        char[] ch = plaintext.toCharArray();
        for (int i = 0; i < ch.length; i++)
        {
            ch[i] = (char)(~(ch[i]));
        }
        return new String(ch);
    }
    //endregion
}
