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

    public static String readerWord(String filePath)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            if (".doc".equalsIgnoreCase(suffix))
            {
                WordExtractor extractor = new WordExtractor(new FileInputStream(new File(filePath)));
                String[] text = extractor.getParagraphText();
                for (int i = 0; i < text.length; i++)
                {
                    sb.append(text[i]);
                }
            }
            else
            {
                XWPFDocument document = new XWPFDocument(new FileInputStream(new File(filePath)));
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (int i = 0; i < paragraphs.size(); i++)
                {
                    sb.append(paragraphs.get(i));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
