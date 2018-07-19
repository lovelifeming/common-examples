package com.zsm.commonexample.Encryption;

import javax.crypto.Cipher;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/11.
 * @Modified By:
 */
public abstract class CertificateSSL
{
    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    public static final String X509 = "X.509";

    public static final String SunX509 = "SunX509";

    public static final String SSL = "SSL";

    /**
     * 由KeyStore获得私钥
     *
     * @param keyStorePath 密钥库存储路径
     * @param alias        别名
     * @param password     密码
     * @return 私钥
     */
    private static PrivateKey getPrivateKey(String keyStorePath, String alias, String password)
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
     * @param keyStorePath 密钥库存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password)
    {
        KeyStore ks = getKeyStore(keyStorePath, password);
        Certificate certificate = ks.getCertificate(alias);
        return certificate;
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath 密钥库存储路径
     * @param password     密码
     * @return
     */
    private static KeyStore getKeyStore(String keyStorePath, String password)
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
     * @param keyStorePath 密钥库存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] plaintext, String keyStorePath, String alias, String password)
    {
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param ciphertext   密文
     * @param keyStorePath 密钥库存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] ciphertext, String keyStorePath, String alias, String password)
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
    {
        // 取得公钥
        PublicKey publicKey = getPublicKey(certificatePath);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 验证Certificate
     *
     * @param certificatePath 证书路径
     * @return
     */
    public static boolean verifyCertificate(String certificatePath)
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
        X509Certificate x509Certificate = (X509Certificate)certificate;
        x509Certificate.checkValidity(date);
        return status;
    }

    /**
     * 签名
     *
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static String sign(byte[] sign, String keyStorePath, String alias, String password)
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
        signature.update(sign);
        return encryptBASE64(signature.sign());
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
    {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate)getCertificate(certificatePath);
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(plaintext);
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 校验证书是否有效
     *
     * @param date         日期
     * @param keyStorePath 密钥存储路径
     * @param alias        别名
     * @param password     密码
     * @return
     */
    public static boolean verifyCertificate(Date date, String keyStorePath, String alias, String password)
    {
        Certificate certificate = getCertificate(keyStorePath, alias, password);
        status = verifyCertificate(date, certificate);
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
    {
        return verifyCertificate(new Date(), keyStorePath, alias, password);
    }

    /**
     * 获得SSLSocektFactory
     *
     * @param password          密码
     * @param keyStorePath      密钥库路径
     * @param trustKeyStorePath 信任库路径
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory(String password, String keyStorePath, String trustKeyStorePath)
    {
        // 初始化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(SunX509);
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        keyManagerFactory.init(keyStore, password.toCharArray());
        // 初始化信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(SunX509);
        KeyStore trustkeyStore = getKeyStore(trustKeyStorePath, password);
        trustManagerFactory.init(trustkeyStore);
        // 初始化SSL上下文
        SSLContext ctx = SSLContext.getInstance(SSL);
        ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        SSLSocketFactory sf = ctx.getSocketFactory();
        return sf;
    }

    /**
     * 为HttpsURLConnection配置SSLSocketFactory
     *
     * @param conn              HttpsURLConnection
     * @param password          密码
     * @param keyStorePath      密钥库路径
     * @param trustKeyStorePath 信任库路径
     */
    public static void configSSLSocketFactory(HttpsURLConnection conn, String password, String keyStorePath,
                                              String trustKeyStorePath)
    {
        conn.setSSLSocketFactory(getSSLSocketFactory(password, keyStorePath, trustKeyStorePath));
    }
}
