package com.zsm.commonexample.Encryption;

import com.zsm.commonexample.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;


/**
 * 单密钥算法：是信息的发送方采用密钥A进行数据加密,信息的接收方采用同一个密钥A进行数据解密.
 * DES全称Data Encryption Standard，是一种使用密匙加密的块算法。现在认为是一种不安全的加密算法，因为现在已经有用穷举法攻破DES密码
 * 的报道了。尽管如此，该加密算法还是运用非常普遍，是一种标准的加密算法。3DES是DES的加强版本。
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/3.
 * @Modified By:
 */
public class DES
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DES.class);

    private static final String DES = "DES";

    private static final String DES_KEY = "AR=l@^i+g(&*$!)mA;sq.d?F";
    private static final String DEFAULT_KEY = "AR=l@^i+";

    public static String encryption(String plaintext)
    {
        return encryption(plaintext, DES_KEY);
    }

    public static String decryption(String ciphertext)
    {
        return decryption(ciphertext, DES_KEY);
    }

    /**
     * DES加密
     *
     * @param plaintext 明文
     * @param key       密钥长度必须大于八位
     * @return
     */
    public static String encryption(String plaintext, String key)
    {
        try
        {
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            //从原始密钥数据创建DESKeySpec对象
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            //创建一个密钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            //将DESKeySpec转化成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
            //用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            //将加密后的数据编码成字符串
            String hexString = CommonUtils.parseBytesToHexString(cipher.doFinal(plaintext.getBytes()));
            return hexString;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param ciphertext 密文
     * @param key        密钥长度必须大于八位
     * @return
     */
    public static String decryption(String ciphertext, String key)
    {
        try
        {
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            //从原始密钥数据创建DESKeySpec对象
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            //创建一个密钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            //将DESKeySpec转化成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(keySpec);
            //Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
            //用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            //将加密后的数据解码再解密
            byte[] bytes = cipher.doFinal(CommonUtils.parseHexStringToBytes(ciphertext));
            return new String(bytes);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptionDES(String plaintext)
    {
        return CommonUtils.parseBytesToHexString(encryptionDES(plaintext.getBytes(), DEFAULT_KEY));
    }

    public static String decryptionDES(String ciphertext)
    {
        return new String(decryptionDES(CommonUtils.parseHexStringToBytes(ciphertext), DEFAULT_KEY));
    }

    /**
     * DES加密，加密后的字节数组不能直接转换为字符串，转换过程存在字节丢失,需要先转码后再转为字符串。
     *
     * @param bytes 明文字节数组
     * @param key   密钥只能是八个字节
     * @return
     */
    public static byte[] encryptionDES(byte[] bytes, String key)
    {
        try
        {
            byte[] keyBytes = key.getBytes();
            // 初始化向量
            IvParameterSpec spec = new IvParameterSpec(keyBytes);
            DESKeySpec desKey = new DESKeySpec(keyBytes);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, spec);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(bytes);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param bytes 密文字节数组
     * @param key   密钥只能是八个字节
     * @return
     * @throws Exception
     */
    public static byte[] decryptionDES(byte[] bytes, String key)
    {
        try
        {
            byte[] keyBytes = key.getBytes();
            // 初始化向量
            IvParameterSpec iv = new IvParameterSpec(keyBytes);
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(keyBytes);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            // 真正开始解密操作
            return cipher.doFinal(bytes);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
