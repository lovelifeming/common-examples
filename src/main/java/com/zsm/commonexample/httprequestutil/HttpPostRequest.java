package com.zsm.commonexample.httprequestutil;

import com.zsm.commonexample.util.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:01.
 * @Modified By:
 */
public class HttpPostRequest
{
    /**
     * 使用URL类connection
     *
     * @param url
     * @param data
     * @return
     */
    public static String sendHttpPostURL(String url, String data)
    {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = null;
        try
        {
            //1.创建连接
            URL httpUrl = new URL(url);
            //2.建立连接
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            //设置请求头信息
            conn.setRequestMethod("POST");
            //Content-Type  application/x-www-form-urlencoded   application/json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            //3.发送POST请求
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data);
            out.flush();

            //4.读取响应信息
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            StringBuilder sb = new StringBuilder();
            while ((lines = reader.readLine()) != null)
            {
                lines = new String(lines.getBytes(), "utf-8");

                sb.append(lines);
            }
            response = sb.toString();

            //5.断开连接
            reader.close();
            conn.disconnect();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest-->sendHttpPostURL-->MalformedURLException-->" + e.getMessage());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest-->sendHttpPostURL-->UnsupportedEncodingException-->" + e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest-->sendHttpPostURL-->IOException-->" + e.getMessage());
        }
        finally
        {
            //6.使用finally块来关闭输出流、输入流
            FileUtils.closeStream(out, reader);
        }
        return response;
    }

    /**
     * 使用CloseableHttpClient发送POST请求
     *
     * @param url
     * @param data
     * @return
     */
    public static String sendHttpPostClient(String url, String data, String encoding)
        throws IOException
    {
        String result = null;
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //设置参数到请求对象中
        httpPost.setEntity(new StringEntity(data, encoding));
        //设置头信息,指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");

        //执行请求操作，得到结果
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //获取返回数据
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            //按指定编码转换结果实体为String类型
            result = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);

        //释放链接
        response.close();

        return result;
    }

    /**
     * 使用DefaultHttpClient发送POST请求
     *
     * @param url
     * @param data
     * @param needResponse
     * @return
     */
    public static String sendHttpPostDefaultClient(String url, String data, boolean needResponse)
    {
        String result = null;
        //创建DefaultHttpClient对象
        DefaultHttpClient httpClient = new DefaultHttpClient();

        //创建POST方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //设置请求头信息
        StringEntity entity = new StringEntity(data, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        //设置参数到请求对象中
        httpPost.setEntity(entity);

        //执行请求操作，得到请求结果
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            url = URLDecoder.decode(url, "UTF-8");
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String temp = "";
                //读取服务器返回过来的json字符串数据
                temp = EntityUtils.toString(response.getEntity());
                if (!needResponse)
                {
                    return null;
                }
                result = temp;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest-->sendHttpPostDefaultClient-->IOException-->" + e.getMessage());
        }

        //获取返回数据

        return result;
    }

    public static String sendHttpGetDefaultClient(String rul)
    {
        String result = null;
        //创建DefaultHttpClient对象
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //创建GET方式请求对象
        HttpGet httpGet = new HttpGet();
        //执行请求操作，得到请求结果
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                result = EntityUtils.toString(response.getEntity());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostRequest-->sendHttpGetDefaultClient-->IOException-->" + e.getMessage());
        }
        return result;
    }
}
