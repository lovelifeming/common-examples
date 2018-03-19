package com.zsm.commonexample.database;

import com.zsm.commonexample.util.CommonUtils;

import java.io.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/15.
 * @Modified By:
 */
public class BackupMySQLDatabase
{
    public static boolean exportMySQLDatabase(String hostIP, String userName, String password, String saveDirectory,
                                              String fileName, String databaseName)
    {
        File file = new File(saveDirectory);
        if (!(file.exists() && file.isDirectory()))
        {
            file.mkdirs();
        }
        if (!saveDirectory.endsWith(File.separator))
        {
            saveDirectory = saveDirectory + File.separator;
        }
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try
        {
            printWriter = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(saveDirectory + fileName), "UTF-8"));
            String command =
                " mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --add-drop-database -B " + databaseName+ " -r "+saveDirectory;
            Process process = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf8"));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                printWriter.write(line);
            }
            printWriter.flush();
            if (process.waitFor() == 0)
            {
                return true;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            printWriter.close();
            CommonUtils.closeStream(bufferedReader);
        }
        return true;
    }
}
