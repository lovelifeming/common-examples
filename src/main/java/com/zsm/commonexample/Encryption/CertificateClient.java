package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;


/**
 * 证书组件
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/10.
 * @Modified By:
 */
public abstract class CertificateClient
{
    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    public static final String X509 = "X.509";

    /**
     * 由KeyStore获得私钥
     *
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    private static PrivateKey getPrivateKey(String keyStorePath, String alias, String password)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException
    {
        KeyStore ks = getKeyStore(keyStorePath, password);
        PrivateKey key = (PrivateKey)ks.getKey(alias, password.toCharArray());
        return key;
    }

    /**
     * 由Certificate获得公钥
     *
     * @param certificatePath 证书路径
     * @return
     */
    private static PublicKey getPublicKey(String certificatePath)
        throws CertificateException, IOException
    {
        Certificate certificate = getCertificate(certificatePath);
        PublicKey key = certificate.getPublicKey();
        return key;
    }

    /**
     * 获得Certificate
     *
     * @param certificatePath 证书路径
     * @return
     */
    private static Certificate getCertificate(String certificatePath)
        throws CertificateException, IOException
    {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /**
     * 获得Certificate
     *
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException
    {
        KeyStore ks = getKeyStore(keyStorePath, password);
        Certificate certificate = ks.getCertificate(alias);
        return certificate;
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath 密钥存储路径
     * @param password     密码
     * @return
     */
    private static KeyStore getKeyStore(String keyStorePath, String password)
        throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException
    {
        FileInputStream is = new FileInputStream(keyStorePath);
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    /**
     * 私钥加密
     *
     * @param plaintext    明文
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] plaintext, String keyStorePath, String alias, String password)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException
    {
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(plaintext);
    }

    /**
     * 私钥解密
     *
     * @param ciphertext   密文
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] ciphertext, String keyStorePath, String alias, String password)
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException,
        IOException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(ciphertext);
    }

    /**
     * 公钥加密
     *
     * @param plaintext       明文
     * @param certificatePath 证书路径
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] plaintext, String certificatePath)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
        IllegalBlockSizeException, CertificateException, IOException
    {
        // 取得公钥
        PublicKey publicKey = getPublicKey(certificatePath);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext);
    }

    /**
     * 公钥解密
     *
     * @param ciphertext      密文
     * @param certificatePath 证书路径
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] ciphertext, String certificatePath)
        throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException,
        NoSuchAlgorithmException, CertificateException, IOException
    {
        // 取得公钥
        PublicKey publicKey = getPublicKey(certificatePath);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(ciphertext);
    }

    /**
     * 验证Certificate
     *
     * @param certificatePath 证书路径
     * @return
     */
    public static boolean verifyCertificate(String certificatePath)
        throws CertificateException, IOException
    {
        return verifyCertificate(new Date(), certificatePath);
    }

    /**
     * 验证Certificate是否过期或无效
     *
     * @param date            时间
     * @param certificatePath 证书路径
     * @return
     */
    public static boolean verifyCertificate(Date date, String certificatePath)
        throws CertificateException, IOException
    {
        // 取得证书
        Certificate certificate = getCertificate(certificatePath);
        // 验证证书是否过期或无效
        boolean status = verifyCertificate(date, certificate);
        return status;
    }

    /**
     * 验证证书是否过期或无效
     *
     * @param date        时间
     * @param certificate 证书
     * @return
     */
    private static boolean verifyCertificate(Date date, Certificate certificate)
    {
        boolean status = true;
        try
        {
            X509Certificate x509Certificate = (X509Certificate)certificate;
            x509Certificate.checkValidity(date);
        }
        catch (Exception e)
        {
            status = false;
        }
        return status;
    }

    /**
     * 签名
     *
     * @param plaintext    明文
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static String sign(byte[] plaintext, String keyStorePath, String alias, String password)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
        UnrecoverableKeyException, InvalidKeyException, SignatureException
    {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate)getCertificate(keyStorePath, alias, password);
        // 获取私钥
        KeyStore ks = getKeyStore(keyStorePath, password);
        // 取得私钥
        PrivateKey privateKey = (PrivateKey)ks.getKey(alias, password.toCharArray());
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(plaintext);
        return Base64.encode(signature.sign());
    }

    /**
     * 验证签名
     *
     * @param plaintext       明文
     * @param sign            签名
     * @param certificatePath 证书路径
     * @return
     */
    public static boolean verify(byte[] plaintext, String sign, String certificatePath)
        throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException,
        Base64DecodingException
    {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate)getCertificate(certificatePath);
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(plaintext);
        return signature.verify(Base64.decode(sign));
    }

    /**
     * 验证Certificate
     *
     * @param date         时间
     * @param keyStorePath 密钥存储路径
     * @param alias别名
     * @param password     密码
     * @return
     */
    public static boolean verifyCertificate(Date date, String keyStorePath, String alias, String password)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException
    {
        Certificate certificate = getCertificate(keyStorePath, alias, password);
        boolean status = verifyCertificate(date, certificate);
        return status;
    }

    /**
     * 验证Certificate
     *
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static boolean verifyCertificate(String keyStorePath, String alias, String password)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException
    {
        return verifyCertificate(new Date(), keyStorePath, alias, password);
    }

}
