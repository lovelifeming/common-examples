package com.zsm.commonexample.Encryption;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/18.
 * @Modified By:
 */
public class DSATest
{
    private String plaintext = "DSA sign and verify test.";

    @Test
    public void sign()
        throws Exception
    {
        Map<String, byte[]> key = DSA.generatePublicKeyAndPrivateKey();
        byte[] sign = DSA.sign(plaintext.getBytes(), key.get("privateKey"));
        boolean verify = DSA.verify(plaintext.getBytes(), key.get("publicKey"), sign);
        Assert.assertTrue(verify);

        Map<String, String> stringKey = DSA.generatePublicKeyAndPrivateKeyHex();
        String sign1 = DSA.sign(plaintext, stringKey.get("privateKey"));
        boolean verify1 = DSA.verify(plaintext, stringKey.get("publicKey"), sign1);
        Assert.assertTrue(verify1);
    }
}
