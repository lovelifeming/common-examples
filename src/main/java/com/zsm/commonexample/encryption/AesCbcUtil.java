package com.zsm.commonexample.encryption;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;


public class AesCbcUtil
{
    static
    {
        // BouncyCastle 是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES解密
     *
     * @param encryptedData           密文，被加密的数据
     * @param key            秘钥
     * @param iv             偏移量
     * @param encodingFormat 解密后的结果需要进行的编码
     * @return
     */
    public static String decryptFormat(String encryptedData, String key, String iv, String encodingFormat)
    {
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0)
            {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密用户分享群信息
     * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
     * 对称解密的目标密文为 Base64_Decode(encryptedData)。
     * 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
     * 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
     *
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param sessionKey    数据进行加密签名的密钥
     * @param iv            加密算法的初始向量
     * @return
     */
    public static String decrypt(String encryptedData, String sessionKey, String iv)
    {
        if (null != encryptedData && null != sessionKey && null != iv)
        {
            //被加密的数据
            byte[] dataByte = Base64.decodeBase64(encryptedData);
            //加密秘钥
            byte[] keyByte = Base64.decodeBase64(sessionKey);
            //偏移量
            byte[] ivByte = Base64.decodeBase64(iv);
            try
            {
                // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
                int base = 16;
                if (keyByte.length % base != 0)
                {
                    int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                    byte[] temp = new byte[groups * base];
                    Arrays.fill(temp, (byte)0);
                    System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                    keyByte = temp;
                }
                // 初始化
                Security.addProvider(new BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
                SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
                AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
                parameters.init(new IvParameterSpec(ivByte));
                cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
                byte[] resultByte = cipher.doFinal(dataByte);
                if (null != resultByte && resultByte.length > 0)
                {
                    String result = new String(resultByte, "UTF-8");
                    return result;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        else
        {
            return null;
        }
    }
}