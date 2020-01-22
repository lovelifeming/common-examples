package com.zsm.commonexample.encryption;

import com.zsm.commonexample.util.NumberUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/18.
 * @Modified By:
 */
public class EncryptionUtilsTest
{
    private String plaintext = "encryption util all method test.";

    @Test
    public void encryptionSHA()
        throws Exception
    {
        byte[] sha = EncryptionUtils.encryptionSHA(plaintext);
        String hex = EncryptionUtils.encryptionSHAHex(plaintext);
        Assert.assertEquals(NumberUtils.byteArrayToHexString(sha).toUpperCase(), hex);
    }

    @Test
    public void encryptionHMAC()
        throws Exception
    {
        String hmac = EncryptionUtils.encryptionHMAC(plaintext);
        byte[] bytes = EncryptionUtils.encryptionHMAC(plaintext.getBytes());
        Assert.assertNotEquals(hmac, NumberUtils.byteArrayToHexString(bytes));
    }

    @Test
    public void encryptionBASE64()
        throws Exception
    {

        String base64 = EncryptionUtils.encryptionBASE64(plaintext);
        byte[] bytes = EncryptionUtils.decryptionBASE64(base64);
        Assert.assertEquals(plaintext, new String(bytes));
    }

    @Test
    public void encryption()
        throws Exception
    {
        String encryption = EncryptionUtils.encryption(plaintext);
        String decryption = EncryptionUtils.decryption(encryption);
        Assert.assertEquals(plaintext, decryption);
    }
}