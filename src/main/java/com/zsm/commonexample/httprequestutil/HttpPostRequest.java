package main.java.com.zsm.demo.httprequestutil;

import sun.rmi.runtime.Log;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:01.
 * @Modified By:
 */
public class HttpPostRequest
{
    public String sendPost(String url, String params)
    {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";
        try
        {
            //1.创建连接
            URL httpUrl = new URL(url);
            //2.建立连接
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            //设置请求头信息
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            //3.发送POST请求
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(params);
            out.flush();

            //4.读取响应信息
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null)
            {
                lines = new String(lines.getBytes(), "utf-8");
                response += lines;
            }
            reader.close();

            //5.断开连接
            conn.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest sendPost " + e.getMessage());

        }
        finally
        {
            //6.使用finally块来关闭输出流、输入流
            out.close();
            reader.close();
        }
    }
}
