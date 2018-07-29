package com.zsm.commonexample.thridtools;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/7/28 1:23.
 * @Modified By:
 */
public class FastDFSClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSClient.class);

    private TrackerClient trackerClient;

    private TrackerServer trackerServer;

    private StorageClient1 storageClient;

    public FastDFSClient(String conf)
        throws IOException, MyException
    {
        if (conf.contains("classpath:"))
        {
            String path = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().toString(),
                "UTF-8");
            path = path.substring(6);
            conf = conf.replace("classpath:", URLDecoder.decode(path, "UTF-8"));
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    //region 上传文件方法

    /**
     * 上传文件
     *
     * @param local_filename 本地文件全路径
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     */
    public String uploadFile(String local_filename)
    {
        return uploadFile(local_filename, null, null);
    }

    /**
     * 上传文件
     *
     * @param local_fileName 本地文件全路径
     * @param file_ext_name  文件扩展名，不包含（.）
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     */
    public String uploadFile(String local_fileName, String file_ext_name)
    {
        return uploadFile(local_fileName, file_ext_name, null);
    }

    /**
     * 上传文件
     *
     * @param local_filename 文件全路径
     * @param file_ext_name  文件扩展名，不包含（.）
     * @param meta_list      文件扩展信息
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     * @throws IOException
     * @throws MyException
     */
    public String uploadFile(String local_filename, String file_ext_name, NameValuePair[] meta_list)
    {
        String result = null;
        try
        {
            result = storageClient.upload_file1(local_filename, file_ext_name, meta_list);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        catch (MyException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param fileContent 文件的内容，字节数组
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     */
    public String uploadFile(byte[] fileContent)
    {
        return uploadFile(fileContent, null, null);
    }

    /**
     * 上传文件
     *
     * @param fileContent   文件的内容，字节数组
     * @param file_ext_name 文件扩展名，不包含（.）
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     */
    public String uploadFile(byte[] fileContent, String file_ext_name)
    {
        return uploadFile(fileContent, file_ext_name, null);
    }

    /**
     * 上传文件
     *
     * @param fileContent   文件的内容，字节数组
     * @param file_ext_name 文件扩展名，不包含（.）
     * @param meta_list     文件扩展信息
     * @return 如果成功返回文件ID（包括组名和文件名），否则返回null.
     */
    public String uploadFile(byte[] fileContent, String file_ext_name, NameValuePair[] meta_list)
    {
        String result = null;
        try
        {
            result = storageClient.upload_file1(fileContent, file_ext_name, meta_list);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        catch (MyException e)
        {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    //endregion

    //region 下载文件

    /**
     * 从存储服务器下载文件
     *
     * @param file_id      文件ID（包括组名和文件名）
     * @param outputStream 输出流
     * @return 如果成功返回0，否则为-1.
     */
    public int downloadFile(String file_id, FileOutputStream outputStream)
    {
        try
        {
            byte[] bytes = storageClient.download_file1(file_id);
            outputStream.write(bytes);
            return 0;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        catch (MyException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return -1;
    }

    /**
     * 从存储服务器下载文件字节数组
     *
     * @param file_id 文件ID（包括组名和文件名）
     * @return 字节数组
     */
    public byte[] downloadBytes(String file_id)
    {
        byte[] result = null;
        try
        {
            result = storageClient.download_file1(file_id);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        catch (MyException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return result;
    }
    //endregion

    //region 从存储服务器删除文件

    /**
     * 从存储服务器删除文件
     *
     * @param group_name      存储服务器的组名
     * @param remote_filename 存储服务器上的文件名
     * @return 0表示成功，-1表示失败
     */
    public int deleteFile(String group_name, String remote_filename)
    {
        int flag = -1;
        try
        {
            flag = storageClient.delete_file(group_name, remote_filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        catch (MyException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 从存储服务器删除文件
     *
     * @param file_id 文件ID（包括组名和文件名）
     * @return 0表示成功，-1表示失败
     */
    public int deleteFile(String file_id)
    {
        int flag = -1;
        try
        {
            flag = storageClient.delete_file1(file_id);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        catch (MyException e)
        {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return flag;
    }
    //endregion
}
