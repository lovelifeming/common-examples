package com.zsm.commonexample.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/4/23.
 * @Modified By:
 */
@SuppressWarnings("all")
public class Base64Utils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Utils.class);

    /**
     * BASE64加密
     *
     * @param ciphertext 密文
     * @return
     */
    public static String encryBASE64(byte[] ciphertext)
    {
        return new BASE64Encoder().encodeBuffer(ciphertext);
    }

    /**
     * BASE64加密
     *
     * @param ciphertext 密文
     * @return
     */
    public static String encryBASE64ByDecode(String ciphertext)
    {
        return new BASE64Encoder().encodeBuffer(Base64.decode(ciphertext));
    }

    /**
     * BASE64解密
     *
     * @param plaintext 明文
     * @return
     * @throws IOException
     */
    public static byte[] decryptBASE64(String plaintext)
        throws IOException
    {
        return new BASE64Decoder().decodeBuffer(plaintext);
    }

    /**
     * BASE64解密
     *
     * @param plaintext 明文
     * @return
     * @throws IOException
     */
    public static String decryptBASE64ByEncode(String plaintext)
        throws IOException
    {
        return Base64.encode(new BASE64Decoder().decodeBuffer(plaintext));
    }

    /**
     * 保存base64字符串到文件
     *
     * @param base64Code
     * @param targetPath
     * @return
     */
    public static boolean decodeBase64File(String base64Code, String targetPath)
    {
        try
        {
            FileOutputStream out = new FileOutputStream(targetPath);
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out.write(buffer);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param base64Code
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean decodeBase64File(String base64Code, String filePath, String fileName)
    {
        if (StringUtils.isEmpty(base64Code))
        { // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64Code);
            for (int i = 0; i < bytes.length; ++i)
            {
                if (bytes[i] < 0)
                {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            String imgFilePath = FileUtils.createFilePath(filePath, fileName);
            // 生成图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理，转为字符串
     *
     * @param imagePath
     * @return
     */
    public static String encodeBase64Image(String imagePath)
    {
        byte[] data = null;
        try (InputStream in = new FileInputStream(imagePath))
        {
            // 读取图片字节数组
            data = new byte[in.available()];
            in.read(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        BASE64Encoder encoder = new BASE64Encoder();
        // 对字节数组Base64编码,返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }
}
