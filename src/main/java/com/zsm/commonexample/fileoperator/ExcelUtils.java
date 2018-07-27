package com.zsm.commonexample.fileoperator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/2/27 10:17.
 * @Modified By:
 */
public class ExcelUtils
{
    /**
     * 读取Excel文件，返回二维数组
     *
     * @param filePath  文件路径
     * @param sheetName 页签名
     * @return
     */
    public static List<List<String>> readExcel(String filePath, String sheetName)
    {
        String suffix = filePath.substring(filePath.lastIndexOf("."));
        List<List<String>> result = new ArrayList<>();
        try
        {
            Sheet sheet = null;
            if (".xls".equalsIgnoreCase(suffix))
            {
                HSSFWorkbook sheets = new HSSFWorkbook(new FileInputStream(new File(filePath)));
                sheet = sheetName.isEmpty() ? sheets.getSheetAt(0) : sheets.getSheet(sheetName);
            }
            else if (".xlsx".equalsIgnoreCase(suffix))
            {
                XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File(filePath)));
                sheet = sheetName.isEmpty() ? sheets.getSheetAt(0) : sheets.getSheet(sheetName);
            }
            if (sheet == null)
            {
                throw new RuntimeException("read excel sheet not exist!");
            }
            for (Row row : sheet)
            {
                if (row == null)
                {
                    continue;
                }
                List<String> list = new ArrayList<>();
                for (Cell cell : row)
                {
                    String value = new DataFormatter().formatCellValue(cell);
                    list.add(value);
                }
                result.add(list);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
