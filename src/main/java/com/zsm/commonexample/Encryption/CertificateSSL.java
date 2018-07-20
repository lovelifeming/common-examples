package com.zsm.commonexample.Encryption;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/11.
 * @Modified By:
 */
public class CertificateSSL extends CertificateBase
{
    public static final String SunX509 = "SunX509";

    public static final String SSL = "SSL";

    /**
     * 获得SSLSocektFactory
     *
     * @param password          密码
     * @param keyStorePath      密钥库路径
     * @param trustKeyStorePath 信任库路径
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory(String password, String keyStorePath, String trustKeyStorePath)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException,
        UnrecoverableKeyException, KeyManagementException
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
        throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
        KeyManagementException, IOException
    {
        conn.setSSLSocketFactory(getSSLSocketFactory(password, keyStorePath, trustKeyStorePath));
    }
}
