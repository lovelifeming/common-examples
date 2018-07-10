package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


/**
 * RSA算法：将两个大素数相乘十分容易，但那时想要对其乘积进行因式分解却极其困难，因此可以将乘积公开作为加密密钥.
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/3.
 * @Modified By:
 */
public class RSA extends BaseSA
{
    private static final String RSA = "RSA";

    private static final String MD5_WITH_RSA = "MD5withRSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    //region sign 数字签名

    /**
     * 用私钥对信息生成数字签名
     *
     * @param plaintext  签名数据
     * @param privateKey 私钥
     * @return
     */
    public static byte[] sign(byte[] plaintext, byte[] privateKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, RSA, MD5_WITH_RSA);
    }

    /**
     * @param plaintext  签名数据
     * @param privateKey 私钥
     * @return
     */
    public static String sign(String plaintext, String privateKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, RSA, MD5_WITH_RSA);
    }

    /**
     * @param plaintext 签名数据
     * @param publicKey 公钥
     * @param sign      签名
     * @return
     */
    public static boolean verify(byte[] plaintext, byte[] publicKey, byte[] sign)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        return verify(plaintext, publicKey, sign, RSA, MD5_WITH_RSA);
    }

    /**
     * 校验数字签名
     *
     * @param plaintext 加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     */
    public static boolean verify(String plaintext, String publicKey, String sign)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        return verify(plaintext.getBytes(), HexBin.decode(publicKey), HexBin.decode(sign));
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     *
     * @return 公钥和私钥对
     */
    public static Map<String, byte[]> generatePublicKeyAndPrivateKey()
        throws NoSuchAlgorithmException
    {
        return generatePublicKeyAndPrivateKey(KEY_SIZE);
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     * keySize: 512,576,640,704,768,832,896,960,1088,1024,2048
     *
     * @param keySize 密钥长度必须是64的倍数，在512到1024位之间或者2048
     * @return 公钥和私钥对
     */
    public static Map<String, byte[]> generatePublicKeyAndPrivateKey(int keySize)
        throws NoSuchAlgorithmException
    {
        return generatePublicKeyAndPrivateKey(keySize, RSA);
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     *
     * @param keySize 密钥长度必须是64的倍数，在512到1024位之间或者2048
     * @return 公钥和私钥对
     */
    public static KeyPair generateKeyPair(int keySize)
        throws NoSuchAlgorithmException
    {
        return generateKeyPair(keySize, RSA);
    }
    //endregion

    //region RSA加密解密
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
        throws Exception
    {
        byte[] keyBytes = HexBin.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key key = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = encryptedData.length;
        byte[] result = new byte[(encryptedData.length / MAX_DECRYPT_BLOCK + 1) * MAX_ENCRYPT_BLOCK];
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int len = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
            for (byte b : cache)
            {
                result[len++] = b;
            }
        }
        return result;
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
        throws Exception
    {
        byte[] keyBytes = HexBin.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > 128)
            {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            }
            else
            {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
        throws Exception
    {
        byte[] keyBytes = HexBin.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
        throws Exception
    {
        byte[] keyBytes = HexBin.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    //endregion

}
