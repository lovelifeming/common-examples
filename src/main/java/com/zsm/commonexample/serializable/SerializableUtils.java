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
@SuppressWarnings("unchecked")
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
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try
        {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //read object
            objectOutputStream.writeObject(object);
            //get byte array
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        }
        finally
        {
            byteArrayOutputStream.close();
            objectOutputStream.close();
        }
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
        ObjectInputStream objectInputStream = null;
        try
        {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = objectInputStream.readObject();
            return (T)object;
        }
        finally
        {
            objectInputStream.close();
        }

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
        try
        {
            for (char ch : chars)
            {
                byteBuffer.putChar(ch);
            }
            return byteBuffer.array();
        }
        finally
        {
            byteBuffer.clear();
        }
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
        try
        {
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
        finally
        {
            byteBuffer.clear();
            charBuffer.clear();
        }
    }

    /**
     * 序列化深拷贝，先将对象序列化成字节，再反序列化成对象
     *
     * @param source 源对象
     * @param <T>    目标对象
     * @return
     */
    public static <T> T deepCopy(T source)
        throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(source);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        T dest = (T)in.readObject();
        return dest;
    }

}
