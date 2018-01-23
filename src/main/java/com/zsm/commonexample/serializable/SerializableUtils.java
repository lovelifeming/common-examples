package com.zsm.commonexample.serializable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/1/23 22:31.
 * @Modified By:
 */
public class SerializableUtils
{
    /**
     * 将对象序列化成字节码
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] objectSerializableToBytes(Object object)
        throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        //read object
        objectOutputStream.writeObject(object);
        //get byte array
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deserializationToObject(byte[] bytes)
        throws IOException, ClassNotFoundException
    {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Object object = objectInputStream.readObject();
        return (T)object;
    }

    /**
     * 把字符数组转换为字节数组
     *
     * @param chars
     * @return
     */
    public static byte[] charsToBytes(char... chars)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(chars.length * 2);
        for (char ch : chars)
        {
            byteBuffer.putChar(ch);
        }
        return byteBuffer.array();
    }

    /**
     * 把字节数组转换为字符数组
     *
     * @param bytes
     * @return
     */
    public static char[] bytesToChars(byte[] bytes)
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        int length = charBuffer.length();
        char[] chars = new char[length];
        int index = 0;
        while (charBuffer.hasRemaining())
        {
            chars[index] = charBuffer.get();
            index++;
        }
        return chars;
    }

}
