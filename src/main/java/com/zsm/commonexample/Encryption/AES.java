package com.zsm.commonexample.Encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/2.
 * @Modified By:
 */
public class AES
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);

    private static final String AES = "AES";

    private static final String UTF_8 = "utf-8";

    private static final String SHA1_PRNG = "SHA1PRNG";

    /**
     * 加密 1.构造密钥生成器 2.根据encodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
     *
     * @param key       加密规则，加盐
     * @param plaintext 加密内容
     * @return
     */
    public static String AESEncode(String key, String plaintext)
    {
        try
        {
            //防止linux 加密出错
            SecureRandom secureRandom = null;
            secureRandom = SecureRandom.getInstance(SHA1_PRNG);
            secureRandom.setSeed(key.getBytes());
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            // 2.根据encodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组生成
            keygen.init(128, secureRandom);
            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));

            // 3.产生原始对称密钥
            SecretKey secretKey = keygen.generateKey();

            // 4.获得原始对称密钥的字节数组
            byte[] bytes = secretKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey keySpec = new SecretKeySpec(bytes, AES);
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = plaintext.getBytes(UTF_8);
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            // 10.将加密后的数据转换为字符串
            String aesEncode = new String(new BASE64Encoder().encode(byteAES));
            // 11.将字符串返回
            return aesEncode;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反转成byte[]数组 3.将加密内容解密
     *
     * @param key        解密规则，加盐
     * @param ciphertext 解密内容
     * @return
     */
    public static String AESDecode(String key, String ciphertext)
    {
        try
        {
            //SecureRandom secureRandom = new SecureRandom(content.getBytes());
            // 防止linux 解码出错
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1_PRNG);
            secureRandom.setSeed(key.getBytes());
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            // 2.根据encodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            // keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey secretKey = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] bytes = secretKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey keySpec = new SecretKeySpec(bytes, AES);
            // 6.根据指定算法AES生成密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 7.初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // 8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(ciphertext);
            byte[] byteDecode = cipher.doFinal(byteContent);
            String aesDecode = new String(byteDecode, UTF_8);
            return aesDecode;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
