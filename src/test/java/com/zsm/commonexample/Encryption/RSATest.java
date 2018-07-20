package com.zsm.commonexample.Encryption;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/17.
 * @Modified By:
 */
public class RSATest
{
    private String plaintext = "RSA sign and verify test.";

    @Test
    public void sign()
        throws Exception
    {
        Map<String, byte[]> key = RSA.generatePublicKeyAndPrivateKey();
        byte[] sign = RSA.sign(plaintext.getBytes(), key.get("privateKey"));
        boolean verify = RSA.verify(plaintext.getBytes(), key.get("publicKey"), sign);
        Assert.assertTrue(verify);

        Map<String, String> stringKey = RSA.generatePublicKeyAndPrivateKeyHex();
        String sign1 = RSA.sign(plaintext, stringKey.get("privateKey"));
        boolean verify1 = RSA.verify(plaintext, stringKey.get("publicKey"), sign1);
        Assert.assertTrue(verify1);
    }

    @Test
    public void encrypt()
        throws Exception
    {
        Map<String, String> stringKey = RSA.generatePublicKeyAndPrivateKeyHex();
        String encrypt = RSA.encryptByPrivateKey(plaintext, stringKey.get("privateKey"));
        String decrypt = RSA.decryptByPublicKey(encrypt, stringKey.get("publicKey"));
        Assert.assertEquals(plaintext, decrypt);

        String encrypt1 = RSA.encryptByPublicKey(plaintext, stringKey.get("publicKey"));
        String decrypt1 = RSA.decryptByPrivateKey(encrypt1, stringKey.get("privateKey"));
        Assert.assertEquals(plaintext, decrypt1);
    }
}