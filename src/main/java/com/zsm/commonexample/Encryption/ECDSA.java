package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;


/**
 * ECDSA(椭圆曲线数字签名算法):使用椭圆曲线密码（ECC）对数字签名算法（DSA）的模拟.一般只用于签名，特点是:速度快，强度高，签名短.
 * 公钥是：(E，P，n，Q)；私钥是 d。签名信息 m 是 r 和 s 的组合(r，s).
 * <p>
 * 算法			        密钥长度	    默认长度	签名长度	    实现的方
 * NONEwithECDSA		112-571		256		128			JDK/BC
 * RIPEMD160withECDSA	112-571		256		160			BC
 * SHA1withECDSA		112-571		256		160			JDK/BC
 * SHA224withECDSA		112-571		256		224			BC
 * SHA256withECDSA		112-571		256		256			JDK/BC
 * SHA384withECDSA		112-571		256		384			JDK/BC
 * SHA512withECDSA		112-571		256		512			JDK/BC
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/3.
 * @Modified By:
 */
public class ECDSA extends BaseSA
{
    private static final String EC = "EC";

    private static final String SHA_1_WITH_ECDSA = "SHA1withECDSA";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "publicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * ECDSA KeySize 是256
     */
    private static final int KEY_SIZE = 256;

    public static byte[] sign(byte[] plaintext, byte[] privateKey)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, EC, SHA_1_WITH_ECDSA);
    }

    /**
     * @param plaintext  签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String plaintext, String privateKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        return sign(plaintext, privateKey, EC, SHA_1_WITH_ECDSA);
    }

    public static boolean verify(byte[] plaintext, byte[] publicKey, byte[] sign)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        return verify(plaintext, publicKey, sign, EC, SHA_1_WITH_ECDSA);
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
        return generatePublicKeyAndPrivateKeyHex(KEY_SIZE, EC);
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
        Map<String, byte[]> keyPairs = new HashMap<String, byte[]>();
        KeyPair keyPair = generateKeyPair(keySize);
        ECPublicKey publicKey = (ECPublicKey)keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey)keyPair.getPrivate();
        keyPairs.put(PUBLIC_KEY, publicKey.getEncoded());
        keyPairs.put(PRIVATE_KEY, privateKey.getEncoded());

        return keyPairs;
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
        return generateKeyPair(keySize, EC);
    }
}
