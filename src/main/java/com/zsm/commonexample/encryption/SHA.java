package com.zsm.commonexample.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/10.
 * @Modified By:
 */
public class SHA
{
    private static final String SHA_1 = "SHA-1";

    /**
     * 验证MD5 明文是否相同
     *
     * @param sourceString 明文
     * @param ciphertext   密文
     * @return
     */
    public static boolean validate(String sourceString, String ciphertext)
        throws NoSuchAlgorithmException
    {
        return ciphertext.equals(encryptBySHA(sourceString));
    }

    /**
     * SHA签名, SHA是不可逆的加密算法，只可以加密不可以解密。
     *
     * @param bytes 明文字节
     * @return 加密字节数组
     * @throws Exception
     */
    public static byte[] encryptionSHA(byte[] bytes)
        throws Exception
    {
        MessageDigest md5 = MessageDigest.getInstance(SHA_1);
        md5.update(bytes);
        return md5.digest();
    }

    /**
     * 进行SHA签名
     *
     * @param plaintext 要加密的信息
     * @return result 加密后的字符串
     */
    public static String encryptBySHA(String plaintext)
        throws NoSuchAlgorithmException
    {
        // 得到一个SHA-1的消息摘要
        MessageDigest digest = MessageDigest.getInstance(SHA_1);
        // 添加要进行计算摘要的信息
        digest.update(plaintext.getBytes());
        // 得到该摘要
        byte[] bytes = digest.digest();
        // 将摘要转为字符串
        String result = HexBin.encode(bytes);
        return result;
    }
}
