package com.hailian.modules.credit.utils;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {
//	 //发件人地址
    public static String SenderAddress = "2530644578@qq.com";

//    //发件人账户名
    public static String SenderAccount = "2530644578@qq.com";

//    //发件人账户密码
    public static String SenderPassword = "typwolfiqocrecaf";
	
    //收件人地址
    public  String recipientAddress;
    public  String recipientAddressCC;
    //邮件主题
    public String title;
    //邮件内容
    public String content;
    
   


   
    
    public SendMailUtil(String recipientAddress, String recipientAddressCC, String title, String content) {
		super();
		this.recipientAddress = recipientAddress;
		this.recipientAddressCC = recipientAddressCC;
		this.title = title;
		this.content = content;
	}

	public  void sendMail() throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.qq.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,title,content);
        
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(SenderAccount, SenderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
         
        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
         
        //5、关闭邮件连接
        transport.close();
    }
     
    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public  MimeMessage getMimeMessage(Session session,String title,String content) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
      //防止成为垃圾邮件，披上outlook的马甲
        msg.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
        //设置发件人地址
        msg.setFrom(new InternetAddress(SenderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        // 设置邮件接收方
        Address[] internetAddressTo = new InternetAddress().parse(recipientAddress);
//        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        msg.setRecipients(MimeMessage.RecipientType.TO,internetAddressTo);
        msg.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(SenderAddress));
        //设置邮件主题
        msg.setSubject(title,"UTF-8");
        StringBuffer messageText=new StringBuffer();//内容以html格式发送,防止被当成垃圾邮件
        messageText.append(content);

        //设置邮件正文
        msg.setContent(messageText.toString(), "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
         
        return msg;
    }
    public static String sendMailCode(String recipientAddress) {
    	String title="这是一封验证码邮件";
    	String code=getCode();
    	String content="您的验证码是:"+code+"。如果不是本人操作请忽略。";
    	
    	try {
//    		new SendMailUtil("2530644578@qq.com", recipientAddress, "2530644578@qq.com", title, content, "typwolfiqocrecaf").sendMail();
    		new SendMailUtil(recipientAddress, recipientAddress, title, content).sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return code;
		}
    	return code; 
    }
    public void toSendMail(List<String> recipientAddressTO,List<String> recipientAddressCC) throws Exception{
//    	new SendMailUtil(SenderAddress, recipientAddress, SenderAddress, title, content, "typwolfiqocrecaf").sendMail();
    }
    public static void main(String[] args) throws Exception {
    	sendMailCode("15269274025@163.com");
	}
    /**
     * 生成邮箱验证码
    * @author doushuihai  
    * @date 2018年10月10日上午11:03:44  
    * @TODO
     */
    public static String getCode(){
    	StringBuffer sb=new StringBuffer();
    	Random random=new Random();
    	for(int i=0;i<6;i++){
    		sb.append(random.nextInt(10));
    	}
    	return sb.toString();
    }
}
