package com.zsm.commonexample.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @Author: zengsm.
 * @Description:
 * @Date:Created in 2018/6/4.
 * @Modified By:
 */
public class SendEMail
{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEMail.class);

    /**
     * QQ邮箱或者企业邮箱发送邮件到指定邮箱
     *
     * @param from     发件邮箱账号
     * @param password 发件邮箱密码
     * @param to       收件人邮箱
     * @param subject  主题
     * @param content  邮件内容
     */
    public static void sendMail(String from, String password, String to, String subject, String content)
    {
        //qq邮箱host,采用QQ邮箱,密码使用 设置->账户->开启POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务的字符码
        String host = "smtp.qq.com";
        if (!from.contains("@qq.com"))
        {
            //qq企业邮箱host,企业邮箱密码默认开启了SSL,密码使用邮箱登录密码
            host = "smtp.exmail.qq.com";
        }
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory mails = null;
        try
        {
            mails = new MailSSLSocketFactory();
        }
        catch (GeneralSecurityException e)
        {
            LOGGER.error("create MailSSLSocketFactory class fail", e);
            e.printStackTrace();
        }
        mails.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", mails);
        // 获取默认session对象
        final String fromMail = from;
        Session session = Session.getDefaultInstance(properties, new Authenticator()
        {
            public javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                //发件人邮件账户、密码
                return new javax.mail.PasswordAuthentication(fromMail, password);
            }
        });
        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(MimeUtility.encodeWord(subject, "UTF-8", "Q"));
            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
        }
        catch (Exception e)
        {
            LOGGER.error("send email fail ", e);
            e.printStackTrace();
        }
    }

    /**
     * 163邮箱发送邮件到指定邮箱，注：邮件主题要规范，防止邮件被归为垃圾邮件
     *
     * @param from      发件邮箱账号
     * @param password  发件邮箱密码
     * @param to        收件人邮箱
     * @param subject   主题
     * @param content   邮件内容
     * @param jpgPaths  上传图片
     * @param filePaths 上传文件
     */
    public static void javaMail(String from, String password, String to, String subject, String content,
                                String[] jpgPaths, String[] filePaths)
    {
        // 创建一个程序与邮件服务器会话对象 Session 必须是163邮箱
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.163.com");
        props.setProperty("mail.smtp.port", "25");
        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.timeout", "1000");
        // 验证账号及密码，密码需要是第三方授权码
        Session session = Session.getInstance(props, new Authenticator()
        {
            //发件人邮件账号、密码或授权码
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, password);
            }
        });
        try
        {
            // 创建一个Message，它相当于邮件发送者
            Message message = new MimeMessage(session);
            // 设置发送者
            message.setFrom(new InternetAddress(from));
            // 设置发送方式与接收者
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            // 设置主题
            message.setSubject(subject);
            // 设置内容
            message.setContent(content, "text/html;charset=utf-8");

            List<MimeMultipart> images = new ArrayList<>();
            //遍历上传图片附件
            for (String jpg : jpgPaths)
            {
                // 创建图片节点
                MimeBodyPart image = new MimeBodyPart();
                // 读取本地图片文件， 将图片数据添加到节点
                image.setDataHandler(new DataHandler(new FileDataSource(jpg)));
                // 为节点设置一个唯一编号（在文本“节点”将引用该ID）
                String cid = jpg.substring(jpg.lastIndexOf(File.separator) + 1);
                image.setContentID(cid);
                // 创建文本节点
                MimeBodyPart text = new MimeBodyPart();
                //  这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
                text.setContent("这是图片: <br/><img src='cid:" + cid + "'/>", "text/html;charset=UTF-8");

                // （文本+图片）设置 文本 和 图片 节点的关系（将 文本 和 图片节点合成一个混合节点）
                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(text);
                multipart.addBodyPart(image);
                multipart.setSubType("related");    // 关联关系
                images.add(multipart);
            }

            List<MimeBodyPart> bodyParts = new ArrayList<>();
            // 将 文本+图片 的混合节点封装成一个普通节点
            //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
            //    上面的 cid 并非 BodyPart, 所有要把 cid 封装成一个 BodyPart
            if (images.size() > 0)
            {
                MimeBodyPart text_image = new MimeBodyPart();
                for (MimeMultipart multipart : images)
                {
                    text_image.setContent(multipart);
                }
                bodyParts.add(text_image);
            }

            if (filePaths.length > 0)
            {
                // 创建附件节点
                MimeBodyPart attachment = new MimeBodyPart();
                for (String file : filePaths)
                {
                    DataHandler dh2 = new DataHandler(new FileDataSource(file));        // 读取本地文件
                    attachment.setDataHandler(dh2);                                     // 将附件数据添加到“节点”
                    attachment.setFileName(MimeUtility.encodeText(dh2.getName()));      // 设置附件的文件名（需要编码）
                }
                bodyParts.add(attachment);
            }

            // 把附件设置（文本+图片）和 附件 的关系（合成一个大的混合节点 / Multipart ）
            MimeMultipart mm = new MimeMultipart();
            if (bodyParts.size() > 0)
            {
                MimeBodyPart text = new MimeBodyPart();
                text.setContent(content, "text/html;charset=utf-8");
                mm.addBodyPart(text);
                for (MimeBodyPart part : bodyParts)
                {
                    mm.addBodyPart(part);     // 如果有多个附件，可以创建多个多次添加
                }
                mm.setSubType("mixed");         // 混合关系
                // 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象
                message.setContent(mm);
            }

            // 创建 Transport用于将邮件发送
            Transport.send(message);
        }
        //报异常：554 DT:SPM 发送的邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件。请检查是否有用户发送病毒或者垃圾邮件.
        catch (MessagingException e)
        {
            LOGGER.error("send email fail：Messaging Exception", e);
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("send email fail：UnsupportedEncodingException", e);
            e.printStackTrace();
        }
    }

}
