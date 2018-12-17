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
 * •421 HL:REP 该IP发送行为异常，存在接收者大量不存在情况，被临时禁止连接。请检查是否有用户发送病毒或者垃圾邮件，并核对发送列表有效性；
 * •421 HL:ICC 该IP同时并发连接数过大，超过了网易的限制，被临时禁止连接。请检查是否有用户发送病毒或者垃圾邮件，并降低IP并发连接数量；
 * •421 HL:IFC 该IP短期内发送了大量信件，超过了网易的限制，被临时禁止连接。请检查是否有用户发送病毒或者垃圾邮件，并降低发送频率；
 * •421 HL:MEP 该IP发送行为异常，存在大量伪造发送域域名行为，被临时禁止连接。请检查是否有用户发送病毒或者垃圾邮件，并使用真实有效的域名发送；
 * •450 MI:CEL 发送方出现过多的错误指令。请检查发信程序；
 * •450 MI:DMC 当前连接发送的邮件数量超出限制。请减少每次连接中投递的邮件数量；
 * •450 MI:CCL 发送方发送超出正常的指令数量。请检查发信程序；
 * •450 RP:DRC 当前连接发送的收件人数量超出限制。请控制每次连接投递的邮件数量；
 * •450 RP:CCL 发送方发送超出正常的指令数量。请检查发信程序；
 * •450 DT:RBL 发信IP位于一个或多个RBL里。请参考http://www.rbls.org/关于RBL的相关信息；
 * •450 WM:BLI 该IP不在网易允许的发送地址列表里；
 * •450 WM:BLU 此用户不在网易允许的发信用户列表里；
 * •451 DT:SPM ,please try again 邮件正文带有垃圾邮件特征或发送环境缺乏规范性，被临时拒收。请保持邮件队列，两分钟后重投邮件。需调整邮件内容或优化发送环境；
 * •451 Requested mail action not taken: too much fail authentication 登录失败次数过多，被临时禁止登录。请检查密码与帐号验证设置；
 * •451 RP:CEL 发送方出现过多的错误指令。请检查发信程序；
 * •451 MI:DMC 当前连接发送的邮件数量超出限制。请控制每次连接中投递的邮件数量；
 * •451 MI:SFQ 发信人在15分钟内的发信数量超过限制，请控制发信频率；
 * •451 RP:QRC 发信方短期内累计的收件人数量超过限制，该发件人被临时禁止发信。请降低该用户发信频率；
 * •451 Requested action aborted: local error in processing 系统暂时出现故障，请稍后再次尝试发送；
 * •500 Error: bad syntaxU 发送的smtp命令语法有误；
 * •550 MI:NHD HELO命令不允许为空；
 * •550 MI:IMF 发信人电子邮件地址不合规范。请参考http://www.rfc-editor.org/关于电子邮件规范的定义；
 * •550 MI:SPF 发信IP未被发送域的SPF许可。请参考http://www.openspf.org/关于SPF规范的定义；
 * •550 MI:DMA 该邮件未被发信域的DMARC许可。请参考http://dmarc.org/关于DMARC规范的定义；
 * •550 MI:STC 发件人当天的连接数量超出了限定数量，当天不再接受该发件人的邮件。请控制连接次数；
 * •550 RP:FRL 网易邮箱不开放匿名转发（Open relay）；
 * •550 RP:RCL 群发收件人数量超过了限额，请减少每封邮件的收件人数量；
 * •550 RP:TRC 发件人当天内累计的收件人数量超过限制，当天不再接受该发件人的邮件。请降低该用户发信频率；
 * •550 DT:SPM 邮件正文带有很多垃圾邮件特征或发送环境缺乏规范性。需调整邮件内容或优化发送环境；
 * •550 Invalid User 请求的用户不存在；
 * •550 User in blacklist 该用户不被允许给网易用户发信；
 * •550 User suspended 请求的用户处于禁用或者冻结状态；
 * •550 Requested mail action not taken: too much recipient  群发数量超过了限额；
 * •552 Illegal Attachment 不允许发送该类型的附件，包括以.uu .pif .scr .mim .hqx .bhx .cmd .vbs .bat .com .vbe .vb .js .wsh等结尾的附件；
 * •552 Requested mail action aborted: exceeded mailsize limit 发送的信件大小超过了网易邮箱允许接收的最大限制；
 * •553 Requested action not taken: NULL sender is not allowed 不允许发件人为空，请使用真实发件人发送；
 * •553 Requested action not taken: Local user only  SMTP类型的机器只允许发信人是本站用户；
 * •553 Requested action not taken: no smtp MX only  MX类型的机器不允许发信人是本站用户；
 * •553 authentication is required  SMTP需要身份验证，请检查客户端设置；
 * •**554 DT:SPM 发送的邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件。请检查是否有用户发送病毒或者垃圾邮件；**
 * •554 DT:SUM 信封发件人和信头发件人不匹配；
 * •554 IP is rejected, smtp auth error limit exceed 该IP验证失败次数过多，被临时禁止连接。请检查验证信息设置；
 * •554 HL:IHU 发信IP因发送垃圾邮件或存在异常的连接行为，被暂时挂起。请检测发信IP在历史上的发信情况和发信程序是否存在异常；
 * •554 HL:IPB 该IP不在网易允许的发送地址列表里；
 * •554 MI:STC 发件人当天内累计邮件数量超过限制，当天不再接受该发件人的投信。请降低发信频率；
 * •554 MI:SPB 此用户不在网易允许的发信用户列表里；
 * •554 IP in blacklist 该IP不在网易允许的发送地址列表里。
 *
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
     * 163邮箱发送邮件到指定邮箱，注：邮件主题要规范，防止邮件被归为垃圾邮件.163 SMTP 密码是授权码
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
