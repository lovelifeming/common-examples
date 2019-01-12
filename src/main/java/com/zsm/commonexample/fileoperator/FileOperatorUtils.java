package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.util.CommonUtils;
import com.zsm.commonexample.util.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 * @Author: zsm.
 * @Description:
 * @Date:Created in 2017/11/20 23:01.
 * @Modified By:
 */
public class FileOperatorUtils
{

    /**
     * 根据文件全路径创建新文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean createNewFile(String filePath)
        throws IOException
    {
        File file = new File(filePath);
        if (file.exists())
        {
            return false;
        }
        else
        {
            int index = filePath.lastIndexOf(FileUtils.FILE_SEPARATOR);
            return createNewFile(filePath.substring(0, index), filePath.substring(index + 1));
        }
    }

    /**
     * 根据文件夹和文件名创建新文件
     *
     * @param directory
     * @param fileName
     * @return
     * @throws IOException
     */
    public static boolean createNewFile(String directory, String fileName)
        throws IOException
    {
        File dir = new File(directory);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        String filePath = dir.getPath() + FileUtils.FILE_SEPARATOR + fileName;
        File file = new File(filePath);
        if (!dir.isDirectory() || file.exists())
        {
            return false;
        }
        else
        {
            return file.createNewFile();
        }
    }

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
     * 递归遍历删除所有文件及文件夹
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
     * 递归遍历所有文件，获取所有文件名
     *
     * @param dirPath 文件夹路径
     * @param filter  过滤器
     * @return
     */
    public static String[] recursionAllFileName(String dirPath, FilenameFilter filter)
    {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isFile())
        {
            files.add(file.getName());
            return files.toArray(new String[files.size()]);
        }
        else if (file.isDirectory())
        {
            recursionAllFileNames(file, files, filter);
            return files.toArray(new String[files.size()]);
        }
        return new String[] {"There is problem with the file path entered!"};
    }

    /**
     * 递归遍历所有文件，获取所有文件名
     *
     * @param file   文件对象
     * @param names  文件名
     * @param filter 过滤器
     * @return
     */
    public static List<String> recursionAllFileNames(File file, List<String> names, FilenameFilter filter)
    {
        if (!file.exists())
        {
            return names;
        }
        File[] fileList = file.listFiles(filter);
        for (File f : fileList)
        {
            if (f.isDirectory())
            {
                recursionAllFileNames(f, names, filter);
            }
            else
            {
                names.add(f.getName());
            }
        }
        if (file.isFile())
        {
            names.add(file.getName());
        }
        return names;
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

    //region 读取大文件

    /**
     * 文件流边读边用，使用Buffered文件流的 read() 方法每次读取指定长度的数据到内存中
     *
     * @param filePath
     * @param handler
     * @throws IOException
     */
    public static void readBigFile(String filePath, Object handler)
        throws IOException
    {
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(filePath), 8192);
        byte[] bytes = new byte[8192];
        int len = -1;
        while ((len = stream.read(bytes)) != -1)
        {
            //TODO  handler bytes
        }
        stream.close();
    }

    /**
     * 对大文件建立 NIO 的 FileChannel，每次调用 read() 会先将文件数据读取到已分配固定长度的 java.nio.ByteBuffer 中，
     * 接着从中获取读取的数据，将数据转存到数组操作。
     *
     * @param filePath
     * @param handler
     * @throws IOException
     */
    public static void readBigFileByNIO(String filePath, Object handler)
        throws IOException
    {
        FileInputStream stream = new FileInputStream(filePath);
        ByteBuffer buffer = ByteBuffer.allocate(65535);
        FileChannel channel = stream.getChannel();
        int len = -1;
        while ((len = channel.read(buffer)) != -1)
        {
            byte[] bytes = new byte[len];
            buffer.flip();
            buffer.get(bytes);
            buffer.clear();

            //TODO handler bytes
        }
        buffer.clear();
        channel.close();
        stream.close();
    }

    /**
     * 内存文件映射，就是把文件内容映射到虚拟内存的一块区域中，从而可以直接操作内存当中的数据而无需每次都通过 I/O 去物理硬盘读取文件
     *
     * @param filePath
     * @param handler
     * @throws IOException
     */
    public static void readBigFileByMapper(String filePath, Object handler)
        throws IOException
    {
        FileInputStream stream = new FileInputStream(filePath);
        FileChannel channel = stream.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        boolean end = false;
        do
        {
            int limit = map.limit();
            int position = map.position();
            if (limit <= position)
            {
                end = true;
            }
            int maxSize = 2048;
            if (limit - position < maxSize)
            {
                maxSize = limit - position;
            }
            byte[] bytes = new byte[maxSize];
            map.get(bytes);

            //TODO  handler  bytes
        }
        while (!end);
        map.clear();
        channel.close();
        stream.close();
    }

    //endregion

    /**
     * 删除文件前面指定行内容。
     *
     * @param file             文件
     * @param clearHeaderLines 指定删除行数
     * @return
     */
    public static boolean removeFileHeaderLines(File file, int clearHeaderLines)
    {
        RandomAccessFile accessFile = null;
        try
        {
            accessFile = new RandomAccessFile(file, "rw");
            //文件起始删除读取游标
            long pointer = accessFile.getFilePointer();
            for (int i = 0; i < clearHeaderLines; i++)
            {
                String line = accessFile.readLine();
                //读取文件内容，当为null时，文件读取到结尾或者文件为空
                if (null == line)
                {
                    break;
                }
            }
            //文件起始读取游标，从这儿开始读取文件内容放到文件头部
            long readPosition = accessFile.getFilePointer();
            byte[] bytes = new byte[1024];
            int index;
            while ((index = accessFile.read(bytes)) != -1)
            {
                accessFile.seek(pointer);
                accessFile.write(bytes, 0, index);
                readPosition += index;
                pointer += index;
                accessFile.seek(readPosition);
            }
            accessFile.setLength(pointer);
            return true;
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
            CommonUtils.closeStream(accessFile);
        }
        return false;
    }
}
