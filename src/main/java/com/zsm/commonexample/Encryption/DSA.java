package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
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
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 53;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 64;

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
        return generatePublicKeyAndPrivateKeyHex(DSA);
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



    //region RSA加密解密

    /**
     * 使用公钥对数据加密
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return
     */
    public static String encryptByPublicKey(String plaintext, String publicKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException,
        BadPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        return HexBin.encode(encryptByPublicKey(plaintext.getBytes(), HexBin.decode(publicKey)));
    }

    /**
     * 使用公钥对数据加密
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] plaintext, byte[] publicKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException, IOException
    {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        Key key = keyFactory.generatePublic(keySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(SHA1_WITH_DSA);//keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = doFinalData(plaintext, cipher, MAX_ENCRYPT_BLOCK);
        return bytes;
    }

    /**
     * 使用私钥对数据加密
     *
     * @param plaintext  明文
     * @param privateKey 私钥
     * @return
     */
    public static String encryptByPrivateKey(String plaintext, String privateKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException,
        BadPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        return HexBin.encode(encryptByPrivateKey(plaintext.getBytes(), HexBin.decode(privateKey)));
    }

    /**
     * 使用私钥对数据加密
     *
     * @param plaintext  明文
     * @param privateKey 私钥
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] plaintext, byte[] privateKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException, IOException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        Key key = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("SHA256withDSA");//keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = doFinalData(plaintext, cipher, MAX_ENCRYPT_BLOCK);
        return bytes;
    }

    /**
     * 使用私钥对数据解密
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @return
     */
    public static String decryptByPrivateKey(String ciphertext, String privateKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException,
        BadPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        return new String(decryptByPrivateKey(HexBin.decode(ciphertext), HexBin.decode(privateKey)));
    }

    /**
     * 使用私钥对数据解密
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] ciphertext, byte[] privateKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException, IOException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        Key key = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(SHA1_WITH_DSA);//keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = doFinalData(ciphertext, cipher, MAX_DECRYPT_BLOCK);
        return bytes;
    }

    /**
     * 使用公钥对数据解密
     *
     * @param ciphertext 密文
     * @param publicKey  公钥
     * @return
     */
    public static String decryptByPublicKey(String ciphertext, String publicKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException,
        BadPaddingException, InvalidKeyException, InvalidKeySpecException
    {
        return new String(decryptByPublicKey(HexBin.decode(ciphertext), HexBin.decode(publicKey)));
    }

    /**
     * 使用公钥对数据解密
     *
     * @param ciphertext 密文
     * @param publicKey  公钥
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] ciphertext, byte[] publicKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException, IOException
    {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA);
        Key key = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(SHA1_WITH_DSA);//keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = doFinalData(ciphertext, cipher, MAX_DECRYPT_BLOCK);
        return bytes;
    }

    /**
     * 数据分段加密解密操作
     *
     * @param data   数据
     * @param cipher 密码器
     * @param block  数据块大小
     * @return
     */
    private static byte[] doFinalData(byte[] data, Cipher cipher, int block)
        throws IllegalBlockSizeException, BadPaddingException, IOException
    {
        int length = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (length - offSet > 0)
        {
            if (length - offSet > block)
            {
                cache = cipher.doFinal(data, offSet, block);
            }
            else
            {
                cache = cipher.doFinal(data, offSet, length - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    //endregion

}
