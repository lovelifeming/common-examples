package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;


/**
 * DSA(Digital Signature Algorithm): 基于整数有限域离散对数难题的，其安全性与RSA相比差不多，只用于签名。DSA的一个重要特点是两个
 * 素数公开，当使用别人的p和q时，即使不知道私钥，你也能确认它们是否是随机产生的，还是作了手脚。RSA却做不到。
 * 私钥加密生成数字签名，公钥验证数据及签名。数字签名的作用就是校验数据在传输过程中不被修改。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/3.
 * @Modified By:
 */
public class DSA extends BaseSA
{
    private static final String DSA = "DSA";

    /**
     * keySize: 512,576,640,704,768,832,896,960,1088,1024,2048
     * keySize 密钥长度必须是64的倍数，在512到1024位之间或者2048
     */
    private static final int KEY_SIZE = 512;

    /**
     * 数字签名,签名/验证算法
     */
    private static final String SHA1_WITH_DSA = "SHA1withDSA";

    /**
     * 生成标准公钥,公钥是：（p，q，g，y）；私钥是 x。签名信息 m 是 r 和 s 的组合（r，s）。
     */
    public static PublicKey getPublicKey(BigInteger y, BigInteger p, BigInteger q, BigInteger g)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        DSAPublicKeySpec dsaPublicKeySpec = new DSAPublicKeySpec(y, p, q, g);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        //根据提供的密钥规范（密钥材料）生成公钥对象
        return keyFactory.generatePublic(dsaPublicKeySpec);
    }

    /**
     * 生成标准私钥
     */
    public static PrivateKey getPrivateKey(BigInteger x, BigInteger p, BigInteger q, BigInteger g)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        DSAPrivateKeySpec dsaPrivateKeySpec = new DSAPrivateKeySpec(x, p, q, g);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        //根据提供的密钥规范（密钥材料）生成私钥对象
        return keyFactory.generatePrivate(dsaPrivateKeySpec);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param plaintext  签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static byte[] sign(byte[] plaintext, byte[] privateKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, DSA, SHA1_WITH_DSA);
    }

    /**
     * @param plaintext  签名数据
     * @param privateKey 私钥
     * @return
     */
    public static String sign(String plaintext, String privateKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, DSA, SHA1_WITH_DSA);
    }

    /**
     * 校验数字签名
     *
     * @param plaintext 加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     */
    public static boolean verify(byte[] plaintext, byte[] publicKey, byte[] sign)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException

    {
        return verify(plaintext, publicKey, sign, DSA, SHA1_WITH_DSA);
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
     * @return 公钥和私钥对, 经过十六进制转换
     */
    public static Map<String, String> generatePublicKeyAndPrivateKeyHex()
        throws NoSuchAlgorithmException
    {
        return generatePublicKeyAndPrivateKeyHex(KEY_SIZE, DSA);
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
        return generatePublicKeyAndPrivateKey(keySize, DSA);
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
        return generateKeyPair(keySize, DSA);
    }
}
