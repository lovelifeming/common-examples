package com.zsm.commonexample.Encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/7/17.
 * @Modified By:
 */
public class SHATest
{
    @Test
    public void encryptBySHA()
        throws Exception
    {
        String key = "sha_test";
        String sha = SHA.encryptBySHA(key);
        Assert.assertEquals("A9DD2922D7DC0E9E2D973095674AB33DA29DA3E8", sha);

        byte[] sha1 = SHA.encryptionSHA(key.getBytes());
        Assert.assertEquals("A9DD2922D7DC0E9E2D973095674AB33DA29DA3E8", HexBin.encode(sha1));

        boolean flag = SHA.validate(key, sha);
        Assert.assertTrue(flag);
    }
}