package com.zsm.commonexample.thridtools;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.zsm.commonexample.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/13.
 * @Modified By:
 */
public class AliyunOSSClient
{
    private static final Log LOG = LogFactory.getLog(AliyunOSSClient.class);

    private static OSSClient ossClient;

    // 阿里云API的内或外网域名
    private static String ENDPOINT;

    // 阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID;

    // 阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET;

    // 阿里云API的bucket名称
    private static String BACKET_NAME;

    // 阿里云API的文件夹名称
    private static String FOLDER;

    // 初始化属性
    static
    {
        Properties pro = new Properties();
        Reader reader = null;
        try
        {
            String resource = "oss.properties";
            reader = Resources.getResourceAsReader(resource);
            pro.load(reader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.closeStream(reader);
        }
        ENDPOINT = pro.getProperty("OSS.ENDPOINT");
        ACCESS_KEY_ID = pro.getProperty("OSS.ACCESS_KEY_ID");
        ACCESS_KEY_SECRET = pro.getProperty("OSS.ACCESS_KEY_SECRET");
        BACKET_NAME = pro.getProperty("OSS.BACKET_NAME");
        FOLDER = pro.getProperty("OSS.FOLDER");
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return
     */
    public static OSSClient getOSSClient()
    {
        return ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     *
     * @param ossClient  OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public static String createBucketName(OSSClient ossClient, String bucketName)
    {
        // 存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName))
        {
            // 创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            LOG.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     *
     * @param ossClient  oss对象
     * @param bucketName 存储空间
     */
    public static void deleteBucket(OSSClient ossClient, String bucketName)
    {
        ossClient.deleteBucket(bucketName);
        LOG.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     文件夹
     * @return
     */
    public static String createFolder(OSSClient ossClient, String bucketName, String folder)
    {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash))
        {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            LOG.info("创建文件成功");
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     文件夹
     * @param key        Bucket下的文件全路径
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key)
    {
        ossClient.deleteObject(bucketName, folder + key);
        LOG.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 上传图片至OSS
     *
     * @param file   上传文件
     * @param folder 文件夹名
     * @return 返回的唯一MD5数字签名
     * @throws IOException
     */
    public static String uploadObject2OSS(File file, String folder)
        throws IOException
    {
        String resultStr = null;
        // 以输入流的形式上传文件
        InputStream is = new FileInputStream(file);
        // 文件名
        String fileName = file.getName();
        // 文件大小
        Long fileSize = file.length();
        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        // 上传的文件的长度
        metadata.setContentLength(is.available());
        // 指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        // 指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        // 指定该Object被下载时的内容编码格式
        metadata.setContentEncoding("utf-8");
        // 如果没有扩展名则填默认值application/octet-stream
        metadata.setContentType(getContentType(fileName));
        // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
        metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
        // 上传文件 (上传文件流的形式)
        PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + folder + fileName, is, metadata);
        // 解析结果
        resultStr = putResult.getETag();
        return resultStr;
    }

    /**
     * 获得文件获取路径
     *
     * @param fileUrl
     * @return
     */
    public static String getFileUrl(String fileUrl)
    {
        if (!StringUtils.isEmpty(fileUrl))
        {
            String[] split = fileUrl.split("/");
            return getUrl(FOLDER + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public static String getUrl(String key)
    {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + (3600l * 1000 * 24 * 365 * 10));
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BACKET_NAME, key, expiration);
        if (url != null)
        {
            return url.toString();
        }
        return null;
    }

    public static String getUrl2(String fileName, String foder)
    {

        return FOLDER + foder + fileName;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return
     */
    public static String getContentType(String fileName)
    {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".pdf".equalsIgnoreCase(fileExtension))
        {
            return "image/pdf";
        }
        if (".PDF".equalsIgnoreCase(fileExtension))
        {
            return "image/pdf";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
            || ".png".equalsIgnoreCase(fileExtension))
        {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension))
        {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension))
        {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension))
        {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension))
        {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension))
        {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension))
        {
            return "text/xml";
        }
        if (".mp4".equalsIgnoreCase(fileExtension))
        {
            return "video/mp4";
        }
        // 默认返回类型
        return "text/plain";
    }

    public static final InputStream getOSS2InputStream(OSSClient client, String bucketName, String filePath)
    {
        OSSObject ossObj = client.getObject(bucketName, filePath);
        return ossObj.getObjectContent();
    }

    // 测试
    public static void main(String[] args)
        throws IOException
    {
//		// 初始化OSSClient
//		AliyunOSSClientUtil.getOSSClient();
//		// 上传文件 String
//		String files = "C:/Users/Administrator/Desktop/iot_datacollection.sql";
//		String foder="Lh456";
//		//AliyunOSSClientUtil.createFolder(ossClient, "fscstg", foder);
//		String[] file = files.split(",");
//		for (String filename : file) {
//			File file2 = new File(filename);
//			String md5key = AliyunOSSClientUtil.uploadObject2OSS(file2,foder+"/");
//			LOG.info("文件MD5数字唯一签名:" + md5key);
//			String imgUrl = getFileUrl(file2.getTypeName());
//			LOG.info("上传文件的URL:" + imgUrl);
//		}
        //获取文件
        try
        {
            BufferedInputStream bis = new BufferedInputStream(
                AliyunOSSClient.getOSS2InputStream(AliyunOSSClient.getOSSClient(), BACKET_NAME,
                    "lims/instrument/ysbg/iot/LH569/201802046-苄基腺嘌呤/201802046-苄基腺嘌呤 2018-02-04 15-04-47/NCP52011318020032.D/0db781b6-372e-0ede-e5ec-c8b9d04790c0.PDF"));
            String resfile = "C:\\Users\\Administrator\\Desktop\\1123.pdf";
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(resfile)));
            int itemp = 0;
            while ((itemp = bis.read()) != -1)
            {
                bos.write(itemp);
            }
            LOG.info("文件获取成功"); //console log :文件获取成功
            bis.close();
            bos.close();
        }
        catch (Exception e)
        {
            LOG.error("从OSS获取文件失败:" + e.getMessage(), e);
        }
    }
}
