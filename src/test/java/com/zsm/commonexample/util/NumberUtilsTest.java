package com.zsm.commonexample.util;

import org.junit.Assert;
import org.junit.Test;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/9/19.
 * @Modified By:
 */

public class NumberUtilsTest
{
    @Test
    public void mulDiv()
        throws Exception
    {
        String str = "abc";
        String encode = NumberUtils.encode(str, 3);
        String decode = NumberUtils.encode(encode, 3);
        Assert.assertEquals(str, decode);
        int mulTwo = NumberUtils.mulTwo(4);
        int divTwo = NumberUtils.divTwo(16);
        int mulTwoPower = NumberUtils.mulTwoPower(5, 3);
        int divTwoPower = NumberUtils.divTwoPower(8, 2);
        boolean oddNumber = NumberUtils.isOddNumber(5);
        NumberUtils.swapInt(3, 4);
        String hex = NumberUtils.decimalismToHex(15);
        String toHex = NumberUtils.decimalismToHex(56);
        Assert.assertEquals("F", hex);
    }
}