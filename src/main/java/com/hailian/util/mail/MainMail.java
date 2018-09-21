package com.hailian.util.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
* @author dyc:
* @time 2018年9月21日 上午10:51:00
* @todo
*/
public class MainMail {
	/**
     * 发送邮件
     * @param to 给谁发
     * @param i 发送内容
     */
    public static void send_mail(String to,String text) throws MessagingException {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器
        properties.put("mail.smtp.host", "smtp.qq.com");
        //发送端口
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //设置发送邮件的账号和密码
         Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和密码
                return new PasswordAuthentication("2378546068@qq.com","llskyinhrwgfdjae");
            }
        });

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress("2378546068@qq.com"));
        //设置收件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //设置主题
        ((MimeMessage) message).setSubject("这是一份测试邮件");
        //设置邮件正文  第二个参数是邮件发送的类型
        ((Part) message).setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
        Transport.send(message);
    }

}





