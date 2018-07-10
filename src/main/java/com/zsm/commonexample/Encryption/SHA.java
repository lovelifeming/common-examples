package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/10.
 * @Modified By:
 */
public class SHA
{
    /**
     * 进行SHA加密
     *
     * @param plaintext 要加密的信息
     * @return result 加密后的字符串
     */
    public String encryptBySHA(String plaintext)
        throws NoSuchAlgorithmException
    {
        // 得到一个SHA-1的消息摘要
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        // 添加要进行计算摘要的信息
        digest.update(plaintext.getBytes());
        // 得到该摘要
        byte[] bytes = digest.digest();
        // 将摘要转为字符串
        String result = HexBin.encode(bytes);
        return result;
    }
}
