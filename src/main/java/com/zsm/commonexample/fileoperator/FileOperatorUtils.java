package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.util.CommonUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:01.
 * @Modified By:
 */
public class FileOperatorUtils
{

    /**
     * 拷贝文件
     *
     * @param sourceFile 源文件路径
     * @param targetFile 目标文件路径
     */
    public static void copyFileByChar(String sourceFile, String targetFile)
    {
        FileReader fr = null;   //读文件
        FileWriter fw = null;   //写文件
        try
        {
            fr = new FileReader(sourceFile);
            fw = new FileWriter(targetFile);

            char[] temp = new char[1024];
            int length = 0;
            while ((length = fr.read(temp)) != -1)
            {
                //length是每次读取文件时返回已读取的字符数量，-1为文件读取完毕
                fw.write(temp, 0, length);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.closeStream(fr, fw);
        }
    }

    /**
     * 缓冲区拷贝文件
     *
     * @param sourceFile 源文件路径
     * @param targetFile 目标文件路径
     */
    public static void copeFileByCharBuffer(String sourceFile, String targetFile)
    {
        BufferedReader bufr = null;
        BufferedWriter bufw = null;
        try
        {
            bufr = new BufferedReader(new FileReader(sourceFile));
            bufw = new BufferedWriter(new FileWriter(targetFile));
            String temp = null;
            while ((temp = bufr.readLine()) != null)
            {
                bufw.write(temp);
                bufw.newLine();
                bufw.flush();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.closeStream(bufr, bufw);
        }
    }

    /**
     * 字节流方式拷贝文件
     *
     * @param sourceFile 源文件路径
     * @param targetFile 目标文件路径
     */
    public static void copyFileByByte(String sourceFile, String targetFile)
    {
        BufferedInputStream bfis = null;
        BufferedOutputStream bfos = null;
        try
        {
            bfis = new BufferedInputStream(new FileInputStream(sourceFile));
            bfos = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] temp = new byte[1024 * 1024];
            int length;
            while ((length = bfis.read(temp)) != -1)
            {
                bfos.write(temp, 0, length);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            CommonUtils.closeStream(bfis, bfos);
        }
    }

    /**
     * 递归遍历删除所以文件
     *
     * @param file 文件路径
     */
    public static void deleteFile(File file)
    {
        if (!file.exists())
        {
            return;
        }
        File[] fileList = file.listFiles();
        for (File f : fileList)
        {
            if (f.isDirectory())
            {
                deleteFile(f);
            }
            else
            {
                f.delete();
            }
        }
        file.delete();
    }

    /**
     * 把多个文件里面的数据合并到一个文件里面
     *
     * @param targetFile  合入文件
     * @param sourceFiles 多个原文件
     * @throws IOException
     */
    public static void combineTFiles(String targetFile, String... sourceFiles)
        throws IOException
    {
        ArrayList<FileInputStream> v = new ArrayList<FileInputStream>();

        for (String s : sourceFiles)
        {
            v.add(new FileInputStream(s));
        }
        Iterator<FileInputStream> it = v.iterator();
        Enumeration<FileInputStream> en = new Enumeration<FileInputStream>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return it.hasNext();
            }

            @Override
            public FileInputStream nextElement()
            {
                return it.next();
            }
        };

        SequenceInputStream sis = new SequenceInputStream(en);
        FileOutputStream fos = new FileOutputStream(targetFile);
        byte[] bf = new byte[1024];
        int length = 0;
        while ((length = sis.read(bf)) != -1)
        {
            fos.write(bf, 0, length);
        }
        sis.close();
        fos.close();
    }
}
