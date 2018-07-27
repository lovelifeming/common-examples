package com.zsm.commonexample.httprequest;

import com.zsm.commonexample.util.CommonUtils;
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
import java.net.*;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/11/8 10:01.
 * @Modified By:
 */
@SuppressWarnings("deprecation")
public class HttpPostGetRequest
{
    /**
     * 使用URL类connection
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendHttpPostURL(String url, String params)
    {
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = null;
        try
        {
            //1.创建URL对象
            URL httpUrl = new URL(url);
            //2.调用URL对象的openConnection()来获取HttpURLConnection对象实例
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            //3.设置请求头信息，请求方式
            conn.setRequestMethod("POST");
            //Content-Type  application/x-www-form-urlencoded   application/json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            //不能缓存
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            //设置连接超时为5秒
            conn.setConnectTimeout(5000);
            //发送POST请求必须设置允许输入输出都为true
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //4.发送POST请求
            conn.connect();
            //输出流写入要发送的数据
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(params);
            out.flush();

            //5.读取响应信息
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String lines;
            StringBuilder sb = new StringBuilder();
            while ((lines = reader.readLine()) != null)
            {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            response = sb.toString();

            //6.断开连接
            reader.close();
            conn.disconnect();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostGetRequest-->sendHttpPostURL-->MalformedURLException-->" + e.getMessage());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            System.out.println(
                "HttpPostGetRequest-->sendHttpPostURL-->UnsupportedEncodingException-->" + e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("HttpPostGetRequest-->sendHttpPostURL-->IOException-->" + e.getMessage());
        }
        finally
        {
            //7.使用finally块来关闭输出流、输入流
            CommonUtils.closeStream(out, reader);
        }
        return response;
    }

    /**
     * 使用URL类connection
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String sendHttpGetURL(String url, String params)
        throws IOException
    {
        StringBuilder sb = new StringBuilder();
        //1.创建URL对象，url是服务器API
        //创建GET方式请求url
        if (null != params)
        {
            url = url + "?" + URLEncoder.encode(params);
        }

        URL url1 = new URL(url);
        //2.调用URL对象的openConnection()来获取HttpURLConnection对象实例
        HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
        //3.设置请求方法为GET
        conn.setRequestMethod("GET");
        //4.设置连接超时为5秒
        conn.setConnectTimeout(5000);
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        //5.服务器返回响应，先对响应码判断
        if (conn.getResponseCode() == 200)
        {
            InputStream in = conn.getInputStream();
            byte[] bt = new byte[1024];
            int len;
            while ((len = in.read(bt)) != -1)
            {
                String temp = new String(bt, 0, len, "UTF-8");
                sb.append(temp);
            }
            in.close();
        }
        return sb.toString();
    }

    /**
     * 使用CloseableHttpClient发送POST请求
     *
     * @param url
     * @param params
     * @param encoding "UTF-8"
     * @return
     */
    public static String sendHttpPostClient(String url, String params, String encoding)
        throws IOException
    {
        String result = null;
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //设置参数到请求对象中
        httpPost.setEntity(new StringEntity(params, encoding));
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
     * 使用CloseableHttpClient发送Get请求
     *
     * @param url
     * @param params
     * @param encoding "UTF-8"
     * @return
     */
    public static String sendHttpGetClient(String url, String params, String encoding)
        throws IOException
    {
        String result = null;
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (params != null)
        {
            url = url + "?" + URLEncoder.encode(params, encoding);
        }
        //创建post方式请求对象
        HttpGet httpGet = new HttpGet(url);

        //设置头信息,指定报文头【Content-type】、【User-Agent】
        httpGet.setHeader("Content-type", "application/json");

        //执行请求操作，得到结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

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
        httpClient.close();
        return result;
    }

    /**
     * 使用DefaultHttpClient发送POST请求
     *
     * @param url
     * @param params
     * @param needResponse 是否需要响应结果,默认是需要true，false为不需要响应结果
     * @return
     */
    public static String sendHttpPostDefaultClient(String url, String params, boolean needResponse)
    {
        String result = null;
        //创建DefaultHttpClient对象
        DefaultHttpClient httpClient = new DefaultHttpClient();

        //创建POST方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //设置请求头信息
        StringEntity entity = new StringEntity(params, "UTF-8");
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
            System.out.println("HttpPostGetRequest-->sendHttpPostDefaultClient-->IOException-->" + e.getMessage());
        }

        //获取返回数据

        return result;
    }

    /**
     * 使用DefaultHttpClient发送GET请求
     *
     * @param url
     * @param params
     * @param encoding "UTF-8"
     * @return
     */
    public static String sendHttpGetDefaultClient(String url, String params, String encoding)
        throws MalformedURLException, UnsupportedEncodingException
    {
        String result = null;
        //创建DefaultHttpClient对象
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //创建GET方式请求对象
        if (null != params)
        {
            url = url + "?" + URLEncoder.encode(params, encoding);
        }
        HttpGet httpGet = new HttpGet(url);

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
            System.out.println("HttpPostGetRequest-->sendHttpGetDefaultClient-->IOException-->" + e.getMessage());
        }
        return result;
    }

}
