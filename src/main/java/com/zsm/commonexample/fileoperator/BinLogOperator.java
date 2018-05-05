package com.zsm.commonexample.fileoperator;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.deserialization.ChecksumType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @Author: zengsm.
 * @Description: 读取MySQL二进制日志文件，转换为文本文件
 * @Date:Created in 2018/4/18.
 * @Modified By:
 */
public class BinLogOperator
{
    /**
     * 当前系统的换行符
     */
    private static String NEW_LINE = System.getProperty("line.separator");

    /**
     * 读取MySQL二进制日志文件，并转换为文本文件
     *
     * @param sourceFile MySQL二进制日志文件
     * @param targetFile 文本文件
     * @throws IOException
     */
    public static void eventDeserializer(String sourceFile, String targetFile)
        throws IOException
    {
        File binlogFile = new File(sourceFile);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setChecksumType(ChecksumType.CRC32);
        try (BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
             FileWriter fileWriter = new FileWriter(new File(targetFile)))
        {
            for (Event event; (event = reader.readEvent()) != null; )
            {
                String str = event.toString();
                fileWriter.write(str);
                fileWriter.write(NEW_LINE);
                fileWriter.flush();
                System.out.println(str);
            }
        }
    }
}
