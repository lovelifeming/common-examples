package com.zsm.commonexample.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/4.
 * @Modified By:
 */
@SuppressWarnings("all")
public abstract class SABase
{
    /**
     * 公钥
     */
    protected static final String PUBLIC_KEY = "publicKey";

    /**
     * 私钥
     */
    protected static final String PRIVATE_KEY = "privateKey";

    /**
     * @param plaintext     签名数据
     * @param privateKey    私钥
     * @param keyAlgorithm  密钥算法名
     * @param signAlgorithm 签名算法名
     * @return 签名
     */
    protected static String sign(String plaintext, String privateKey, String keyAlgorithm, String signAlgorithm)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        return HexBin.encode(sign(plaintext.getBytes(), HexBin.decode(privateKey), keyAlgorithm, signAlgorithm));
    }

    /**
     * @param plaintext     签名数据
     * @param privateKey    私钥
     * @param keyAlgorithm  密钥算法名
     * @param signAlgorithm 签名算法名
     * @return 签名
     */
    protected static byte[] sign(byte[] plaintext, byte[] privateKey, String keyAlgorithm, String signAlgorithm)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        // DSA 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        // 取私钥匙对象
        PrivateKey key = keyFactory.generatePrivate(encodedKeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(key);
        signature.update(plaintext);
        return signature.sign();
    }

    /**
     * @param plaintext     签名数据
     * @param publicKey     公钥
     * @param sign          签名
     * @param keyAlgorithm  密钥算法名
     * @param signAlgorithm 签名算法名
     * @return
     */
    protected static boolean verify(byte[] plaintext, byte[] publicKey, byte[] sign, String keyAlgorithm,
                                    String signAlgorithm)
        throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
    {
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        // SHA1withDSA 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        // 取公钥匙对象
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(key);
        signature.update(plaintext);
        // 验证签名是否正常
        return signature.verify(sign);
    }

    /**
     * 取得私钥
     *
     * @param keyPairs 密钥对集合
     * @return 私钥
     */
    public static Object getPrivateKey(Map<String, Object> keyPairs)
    {
        return keyPairs.get(PRIVATE_KEY);
    }

    /**
     * 取得公钥
     *
     * @param keyPairs 密钥对集合
     * @return 公钥
     */
    public static Object getPublicKey(Map<String, Object> keyPairs)
    {
        return keyPairs.get(PUBLIC_KEY);
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     *
     * @param keySize      密钥长度必须是64的倍数，在512到1024位之间或者2048
     * @param keyAlgorithm 算法名称
     * @return 公钥和私钥对
     * @throws NoSuchAlgorithmException
     */
    protected static KeyPair generateKeyPair(int keySize, String keyAlgorithm)
        throws NoSuchAlgorithmException
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyAlgorithm);
        keyPairGenerator.initialize(keySize);
        // 生成密钥对
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     * keySize: 512,576,640,704,768,832,896,960,1088,1024,2048
     *
     * @param keySize 密钥长度必须是64的倍数，在512到1024位之间或者2048
     * @return 公钥和私钥对
     */
    protected static Map<String, byte[]> generatePublicKeyAndPrivateKey(int keySize, String keyAlgorithm)
        throws NoSuchAlgorithmException
    {
        Map<String, byte[]> keyPairs = new HashMap<String, byte[]>();
        KeyPair keyPair = generateKeyPair(keySize, keyAlgorithm);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyPairs.put(PUBLIC_KEY, publicKey.getEncoded());
        keyPairs.put(PRIVATE_KEY, privateKey.getEncoded());
        return keyPairs;
    }

    /**
     * 生成公钥和私钥对，publicKey，privateKey
     *
     * @return 公钥和私钥对, 经过十六进制转换
     */
    protected static Map<String, String> generatePublicKeyAndPrivateKeyHex(int keySize, String keyAlgorithm)
        throws NoSuchAlgorithmException
    {
        Map<String, byte[]> map = generatePublicKeyAndPrivateKey(keySize, keyAlgorithm);
        Iterator<Map.Entry<String, byte[]>> iterator = map.entrySet().iterator();
        Map<String, String> keyPairs = new HashMap<>();
        while (iterator.hasNext())
        {
            Map.Entry<String, byte[]> entry = iterator.next();
            keyPairs.put(entry.getKey(), HexBin.encode(entry.getValue()));
        }
        return keyPairs;
    }
}
