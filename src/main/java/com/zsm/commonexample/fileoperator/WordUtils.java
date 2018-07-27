package com.zsm.commonexample.fileoperator;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/27 10:16.
 * @Modified By:
 */
public class WordUtils
{
    /**
     * 读取Word文档
     *
     * @param filePath 文件路径
     * @return
     */
    public static String[] readerWord(String filePath)
    {
        String[] result = null;
        try
        {
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            if (".doc".equalsIgnoreCase(suffix))
            {
                WordExtractor extractor = new WordExtractor(new FileInputStream(new File(filePath)));
                String[] text = extractor.getParagraphText();
                result = text;
            }
            else
            {
                XWPFDocument document = new XWPFDocument(new FileInputStream(new File(filePath)));
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                int size = paragraphs.size();
                result = new String[size];
                for (int i = 0; i < size; i++)
                {
                    result[i] = String.valueOf(paragraphs.get(i));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
