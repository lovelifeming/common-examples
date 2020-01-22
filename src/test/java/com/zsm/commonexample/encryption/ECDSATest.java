package com.zsm.commonexample.encryption;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/18.
 * @Modified By:
 */
public class ECDSATest
{
    private String plaintext = "ECDSA sign and verify test.";

    @Test
    public void sign()
        throws Exception
    {
        Map<String, byte[]> key = ECDSA.generatePublicKeyAndPrivateKey();
        byte[] sign = ECDSA.sign(plaintext.getBytes(), key.get("privateKey"));
        boolean verify = ECDSA.verify(plaintext.getBytes(), key.get("publicKey"), sign);
        Assert.assertTrue(verify);

        Map<String, String> stringKey = ECDSA.generatePublicKeyAndPrivateKeyHex();
        String sign1 = ECDSA.sign(plaintext, stringKey.get("privateKey"));
        boolean verify1 = ECDSA.verify(plaintext, stringKey.get("publicKey"), sign1);
        Assert.assertTrue(verify1);
    }
}