package com.zsm.commonexample.encryption;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/2.
 * @Modified By:
 */
@SuppressWarnings("all")
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

    /**
     * AES加密字符串
     *
     * @param content        需要被加密的字符串
     * @param password       加密需要的密码
     * @param encodingFormat 加密内容编码格式 UTF-8
     * @return 密文
     */
    public static byte[] encrypt(String content, String password, String encodingFormat)
    {
        try
        {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 利用用户密码作为随机数初始化出
            kgen.init(128, new SecureRandom(password.getBytes()));
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes(encodingFormat);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content        AES加密过过的内容
     * @param password       加密时的密码
     * @param encodingFormat 加密内容编码格式 UTF-8
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password, String encodingFormat)
    {
        try
        {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            return result; // 明文
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密微信用户登录信息
     * <p>
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
    public static String decodeGroupInfo(String encryptedData, String sessionKey, String iv)
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
                LOGGER.error(e.getMessage());
            }
            return null;
        }
        else
        {
            return null;
        }
    }
}
