package com.zsm.commonexample.database;

import com.zsm.commonexample.util.CommonUtils;

import java.io.*;


/**
 * 备份数据库数据
 *
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/3/15.
 * @Modified By:
 */
public class BackupMySQLDatabase
{
    /**
     * 可以自定义配置host，port，userName，passWord，databaseName，tableName，exportPath
     *
     * @param config
     */
    public static void backup(DBConfig config)
    {
        OutputStreamWriter writer = null;
        BufferedReader br = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();
            String command = new StringBuilder(
                " mysqldump -h " + config.host + " -P" + config.port + " -u" + config.userName + " -p" +
                config.passWord + " " + config.databaseName + " " + config.tableName).toString();
            System.out.println(command);
            Process process = runtime.exec(command);

            // 设置导出编码为utf-8。这里必须是utf-8,，否则从流中读入的是乱码
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            String inStr;
            StringBuffer sb = new StringBuffer("");
            // 组合控制台输出信息字符串
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((inStr = br.readLine()) != null)
            {
                sb.append(inStr + "\r\n");
            }
            // 要用来做导入用的sql目标文件
            writer = new OutputStreamWriter(new FileOutputStream(config.exportPath), "utf-8");
            writer.write(sb.toString());
            writer.flush();
            System.out.println("数据库备份成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("数据库备份失败");
        }
        finally
        {
            CommonUtils.closeStream(br, writer);
        }
    }

    /**
     * 可以自定义配置host，port，userName，passWord，tableName，importPath
     *
     * @param config
     */
    public static void restore(DBConfig config)
    {
        Runtime runtime = Runtime.getRuntime();
        OutputStreamWriter writer = null;
        BufferedReader br = null;
        try
        {
            //-u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字，--default-character-set=utf8
            String command = new StringBuilder("mysql -h" + config.host + " -P" + config.port + " -u" + config.userName
                                               + " -p" + config.passWord + " " + config.databaseName + " " +
                                               config.tableName).toString();
            System.out.println(command);
            Process process = runtime.exec(command);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(config.importPath)));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null)
            {
                sb.append(str + "\r\n");
            }
            writer = new OutputStreamWriter(process.getOutputStream(), "utf-8");
            writer.write(String.valueOf(sb.toString().getBytes()));
            writer.flush();
            System.out.println("数据库恢复成功");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("数据库恢复失败");
        }
        finally
        {
            CommonUtils.closeStream(br, writer);
        }
    }

    /**
     * 备份数据库配置类
     */
    public static class DBConfig
    {
        private String host = "localhost";

        private String userName = "root";

        private String passWord = "123456";

        private String databaseName = "test_db";

        private String tableName;

        private String importPath;

        private String exportPath;

        private String port = "3306";

        private String databasePath;

        public String getHost()
        {
            return host;
        }

        public void setHost(String host)
        {
            this.host = host;
        }

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public String getPassWord()
        {
            return passWord;
        }

        public void setPassWord(String passWord)
        {
            this.passWord = passWord;
        }

        public String getDatabaseName()
        {
            return databaseName;
        }

        public void setDatabaseName(String databaseName)
        {
            this.databaseName = databaseName;
        }

        public String getTableName()
        {
            return tableName;
        }

        public void setTableName(String tableName)
        {
            this.tableName = tableName;
        }

        public String getImportPath()
        {
            return importPath;
        }

        public void setImportPath(String importPath)
        {
            this.importPath = importPath;
        }

        public String getExportPath()
        {
            return exportPath;
        }

        public void setExportPath(String exportPath)
        {
            this.exportPath = exportPath;
        }

        public String getPort()
        {
            return port;
        }

        public void setPort(String port)
        {
            this.port = port;
        }

        public String getDatabasePath()
        {
            return databasePath;
        }

        public void setDatabasePath(String databasePath)
        {
            this.databasePath = databasePath;
        }
    }
}
