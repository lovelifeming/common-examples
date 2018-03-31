package com.zsm.commonexample.fileoperator;

import com.zsm.commonexample.util.CommonUtils;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 参考资料：
 * http://blog.csdn.net/yangshangwei/article/details/77913798
 * https://www.cnblogs.com/Amaris-Lin/p/7278449.html
 * http://blog.csdn.net/stalwartwill/article/details/17373827
 * <p>
 * Zip 文件解压，压缩操作
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/12 17:44.
 * @Modified By:
 */
public class ZipFileOperator
{
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    //region 压缩成ZIP文件

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void compressZipFile(String srcDir, OutputStream out, boolean keepDirStructure)
    {
        long start = System.nanoTime();
        ZipOutputStream zos = null;
        try
        {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
            long end = System.nanoTime();
            System.out.println("压缩完成，耗时：" + (end - start) / 1000000 + " ms");
        }
        catch (Exception e)
        {
            throw new RuntimeException("zip error from ZipFileOperator", e);
        }
        finally
        {
            IOUtils.closeQuietly(zos);
        }
    }

    /**
     * ZIP压缩多个文件
     *
     * @param sourceFiles 需要压缩的文件列表
     * @param targetPath  压缩文件输出路径
     */
    public static void compressZipFile(List<File> sourceFiles, String targetPath)
    {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        FileInputStream in = null;
        try
        {
            zos = new ZipOutputStream(new FileOutputStream(targetPath));
            for (File file : sourceFiles)
            {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(file.getName()));
                int len;
                in = new FileInputStream(file);
                while ((len = in.read(buf)) != -1)
                {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        }
        catch (Exception e)
        {
            throw new RuntimeException("zip error from ZipFileOperator", e);
        }
        finally
        {
            CommonUtils.closeStream(zos, in);
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws IOException
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure)
        throws IOException
    {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile())
        {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1)
            {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        }
        else
        {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0)
            {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure)
                {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }
            else
            {
                for (File file : listFiles)
                {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure)
                    {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), keepDirStructure);
                    }
                    else
                    {
                        compress(file, zos, file.getName(), keepDirStructure);
                    }
                }
            }
        }
    }

    /**
     * ZIP压缩单个文件到一个文件目录下面
     *
     * @param sourcePath      压缩文件全路径
     * @param targetDirectory 压缩文件存放文件夹
     */
    public static void compressSingleFile(String sourcePath, String targetDirectory)
    {
        try
        {
            File dir = new File(targetDirectory);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(sourcePath);
            //组装zip压缩文件全路径
            String zipFileName = targetDirectory + File.separator + file.getName().concat(".zip");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            zos.putNextEntry(new ZipEntry(file.getName()));
            byte[] bytes = Files.readAllBytes(Paths.get(sourcePath));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();
        }
        catch (FileNotFoundException ex)
        {
            System.err.format("The file %s does not exist", sourcePath);
        }
        catch (IOException ex)
        {
            System.err.println("I/O error: " + ex);
        }
    }

    /**
     * ZIP压缩多个文件到第一个文件目录下面
     *
     * @param name      压缩文件名称
     * @param filePaths 压缩文件集合
     */
    public static void compressMultipleFiles(String name, String... filePaths)
    {
        ZipOutputStream zos = null;
        try
        {
            String zipFileName = name.concat(".zip");
            //获取第一个文件目录
            zos = new ZipOutputStream(new FileOutputStream(new File(filePaths[0]).getParent() + zipFileName));
            for (String aFile : filePaths)
            {
                zos.putNextEntry(new ZipEntry(new File(aFile).getName()));
                byte[] bytes = Files.readAllBytes(Paths.get(aFile));
                zos.write(bytes, 0, bytes.length);
                zos.finish();
                zos.closeEntry();
            }
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("A file does not exist: " + ex.getMessage());
        }
        catch (IOException ex)
        {
            System.err.println("I/O error: " + ex.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(zos);
        }
    }

    //endregion

    //region 解压Zip文件

    /**
     * 解压Zip文件
     *
     * @param sourceFilePath      Zip源文件全路径
     * @param targetFileDirectory 解压到的文件夹全路径
     * @return
     * @throws IOException
     */
    public static boolean uncompressZipFile(String sourceFilePath, String targetFileDirectory)
        throws IOException
    {
        InputStream inputStream = null;
        ZipFile zipFile = null;
        File source = new File(sourceFilePath);
        File target = new File(targetFileDirectory);
        if (!source.exists() && source.isFile())
        {
            throw new FileNotFoundException("File not find:" + sourceFilePath + " not found!");
        }
        try
        {
            zipFile = new ZipFile(source);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements())
            {
                ZipEntry entry = entries.nextElement();
                File file = new File(target, entry.getName());
                if (entry.isDirectory())
                {
                    file.mkdirs();
                }
                else
                {
                    File parent = file.getParentFile();
                    if (!parent.exists())
                    {
                        parent.mkdirs();
                    }
                    inputStream = zipFile.getInputStream(entry);
                    IOUtils.copy(inputStream, new FileOutputStream(file));
                }
            }
            return true;
        }
        finally
        {
            CommonUtils.closeStream(inputStream);
            IOUtils.closeQuietly(zipFile);
        }
    }

    /**
     * 解压Zip文件
     *
     * @param sourceFilePath      Zip源文件全路径
     * @param targetFileDirectory 解压到的文件夹全路径
     * @return
     */
    public static boolean uncompressZipFiles(String sourceFilePath, String targetFileDirectory)
    {
        File destDir = new File(targetFileDirectory);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
        ZipInputStream zipIn = null;
        try
        {
            zipIn = new ZipInputStream(new FileInputStream(sourceFilePath));
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null)
            {
                String filePath = targetFileDirectory + File.separator + entry.getName();
                if (!entry.isDirectory())
                {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                    byte[] bytesIn = new byte[BUFFER_SIZE];
                    int read = 0;
                    while ((read = zipIn.read(bytesIn)) != -1)
                    {
                        bos.write(bytesIn, 0, read);
                    }
                    bos.close();
                }
                else
                {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            CommonUtils.closeStream(zipIn);
        }
    }

    //endregion
}
