package com.zsm.commonexample.fileoperator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description: TODO()
 * @Date:Created in 2018/8/17.
 * @Modified By:
 */
public class FileLockUtils
{
    /**
     * randomAccessFile.getChannel().lock() 是获取文件独占锁，一个线程占有独占锁后，
     * 另一个线程只有等lock.release();后才能获取独占锁或者共享锁，否则报 OverlappingFileLockException异常。
     *  一个进程占有独占锁后，其他进程无法获取独占锁；一个进程占有共享锁后，其他进程可以获取共享锁，共享锁只能读，不能写。
     *
     * @param filePath
     * @param mode
     * @return
     */
    public static List readLockFile(String filePath, String mode )
    {
        List<String> context = null;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, mode);
             FileChannel channel = randomAccessFile.getChannel())
        {
            //channel.lock() 独占锁，channel.lock(0, Integer.MAX_VALUE, true) 共享锁
            FileLock lock = channel.lock(0, Integer.MAX_VALUE, true);
            context = new ArrayList<>();
            String temp;
            while ((temp=randomAccessFile.readLine())!=null)
            {
                context.add(temp);
            }
            lock.release();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return context;
    }
}
