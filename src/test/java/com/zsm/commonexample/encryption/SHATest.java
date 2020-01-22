package com.zsm.commonexample.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/17.
 * @Modified By:
 */
@SuppressWarnings("all")
public class SHATest
{
    @Test
    public void encryptBySHA()
        throws Exception
    {
        String plaintext = "SHA encryption and validate method test.";
        String key = "B095E8CFFF21090AB304F6DB3E00572464382A6D";
        String sha = SHA.encryptBySHA(plaintext);
        Assert.assertEquals(key, sha);

        byte[] sha1 = SHA.encryptionSHA(plaintext.getBytes());
        Assert.assertEquals(key, HexBin.encode(sha1));

        boolean flag = SHA.validate(plaintext, sha);
        Assert.assertTrue(flag);
    }
}