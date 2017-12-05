package com.zsm.commonexample.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2017/12/4 9:36.
 * @Modified By:
 */
public class ReadKeyboard
{
    private static char VK_COLON = ':';

    private static char VK_Q = 'q';

    // escape
    private static char VK_ESCAPE = 27;

    //enter return
    private static char VK_RETURN = 13;

    /**
     * 获取键盘输入字符，:q 退出输入
     *
     * @return
     * @throws IOException
     */
    public String readCharByIn()
        throws IOException
    {
        StringBuilder sb = new StringBuilder();
        char temp = Character.MIN_VALUE;
        while (true)
        {
            char ch = (char)System.in.read();
            System.out.println("input character is " + ch);
            if (ch == VK_Q && temp == VK_COLON)
            {
                break;
            }
            if (ch == VK_RETURN)
            {
                continue;
            }
            sb.append(ch);
            temp = ch;
        }
        return sb.substring(0, sb.lastIndexOf(":"));
    }

    /**
     * 从控制台接收一个字符串
     *
     * @return
     * @throws IOException
     */
    public String readLineByBufferedReader()
        throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null)
        {
            if ("exit".equals(str))
            {
                break;
            }
            sb.append(str);
        }
        System.out.println(sb);
        return sb.toString();
    }

    /**
     * next()方法是不接收空格的，在接收到有效数据前，所有的空格或者tab键等输入被忽略，若有有效数据，则遇到这些键退出。
     * nextLine()可以接收空格或者tab键，其输入应该以enter键结束。
     *
     * @return
     */
    public String readScanner()
    {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int no = scanner.nextInt();
        String name = scanner.next();
        String pwd = scanner.next();
        sb.append(no);
        sb.append(name);
        sb.append(pwd);

        while (true)
        {
            String content = scanner.nextLine();
            if ("exit".equals(content))
            {
                break;
            }
            sb.append(content);
        }
        System.out.println(sb);
        return sb.toString();
    }

}
