package com.hailian.modules.credit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class SendMailUtil {
//	 //发件人地址
//    public static String SenderAddress = "2530644578@qq.com";
    public static String SenderAddress = "international@inter-credit.net";

//    //发件人账户名
//    public static String SenderAccount = "2530644578@qq.com";
    public static String SenderAccount = "international@inter-credit.net";

//    //发件人账户密码
//    public static String SenderPassword = "typwolfiqocrecaf";
    public static String SenderPassword = "Chris777";
	
    //收件人地址
    public static  String recipientAddress;
    public  String recipientAddressCC;
    //邮件主题
    public static String title;
    //邮件内容
    public static String content;
    public  static List<Map<String,String>> list;
   


   
    
    public SendMailUtil(String recipientAddress, String recipientAddressCC, String title, String content) {
		super();
		this.recipientAddress = recipientAddress;
		this.recipientAddressCC = recipientAddressCC;
		this.title = title;
		this.content = content;
	}
    public SendMailUtil(String recipientAddress,String recipientAddressCC,String title, String content,List<Map<String,String>> list) {
		super();
		this.recipientAddress = recipientAddress;
		this.recipientAddressCC = recipientAddressCC;
		this.title = title;
		this.content = content;
		this.list = list;
	}

	public  boolean sendMail() throws Exception {
		boolean result=false;
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties props = new Properties();
        props.setProperty("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.store.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.inter-credit.net");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SenderAddress, SenderPassword);
            }
        });

        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session,title,content,list);
        Transport.send(msg);
        result=true;
        return result;
       
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public  MimeMessage getMimeMessage(Session session,String title,String content,List<Map<String,String>> list) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        String nick=javax.mail.internet.MimeUtility.encodeText("Inter-Credit"); 
        msg.setFrom(new InternetAddress(SenderAddress, nick));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientAddress, false));
        msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(SenderAddress));
        msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(recipientAddressCC));
        msg.setSubject(title);
        msg.setSentDate(new Date());
        
        MimeMultipart multipart = new MimeMultipart("mixed");
        // 邮件内容，采用HTML格式
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.removeHeader("Content-Type");
        messageBodyPart.removeHeader("Content-Transfer-Encoding");
        messageBodyPart.addHeader("Content-Type", "text/html; charset=gbk");
        messageBodyPart.addHeader("Content-Transfer-Encoding", "base64");
        
        messageBodyPart.setContent(content, "text/html;charset=GBK");
        
        
        multipart.addBodyPart(messageBodyPart);
        //内嵌图片
        try {
            //添加附件
//            messageBodyPart=new MimeBodyPart();
//            DataSource dataSource1=new FileDataSource("d:/aa.doc");
//            dataHandler=new DataHandler(dataSource1);
//            messageBodyPart.setDataHandler(dataHandler);
//            messageBodyPart.setFileName(MimeUtility.encodeText(dataSource1.getName()));
        	if(CollectionUtils.isNotEmpty(list) && list != null){
        		for(Map<String,String> map:list){
        			for(String name : map.keySet()){
        				   String fileURL = map.get(name);
        				   messageBodyPart=new MimeBodyPart();
        	                 InputStream is=downLoadFromUrl(fileURL);
        	                 //DataSource dataSource1=new FileDataSource("d:/aa.doc");
        	                 DataSource dataSource1=new ByteArrayDataSource(is, "application/png");
        	                 DataHandler dataHandler=new DataHandler(dataSource1);
        	                 messageBodyPart.setDataHandler(dataHandler);
        	                 String subStringB = fileURL.substring(fileURL.lastIndexOf("/")+1);
//        	                 messageBodyPart.setFileName(MimeUtility.encodeText(subStringB));
        	                 messageBodyPart.setFileName(MimeUtility.encodeText(name));
        	                 
        	                 multipart.addBodyPart(messageBodyPart);
        				  }
        		}
        		
        	}
           
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
        
        msg.setContent(multipart);
        msg.saveChanges();
         
        return msg;
    }
    
    
    
   
    public  boolean sendEmail() throws Exception  {
    	boolean result=false;
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Properties props = new Properties();
            props.setProperty("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.store.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.inter-credit.net");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SenderAddress, SenderPassword);
                }
            });
            Message msg = getMimeMessage(session,title,content,list);
            Transport.send(msg);
            result=true;
            return result;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return result;
    }
    public static InputStream  downLoadFromUrl(String urlStr) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        return inputStream;
 }
    public static String sendMailCode(String recipientAddress) {
    	String title="这是一个重置密码的验证码";
    	String code=getCode();
    	String content="您的验证码是:"+code+"。如果不是本人操作请忽略。";
    	
    	try {
    		new SendMailUtil(recipientAddress, "", title, content).sendMail();
		} catch (Exception e) {
			e.printStackTrace();
			return code;
		}
    	return code; 
    }
    public static void main(String[] args) throws Exception {
//    	sendMailCode("dou_shai@163.com");
    	//new SendMailUtil("15269274025@163.com", "", "你好", "mycontent", "http://60.205.229.238:9980/zhengxin_File/2018-11-16/1a183ad043a64af0bde653aa718cd144.doc").sendEmail();
    	List<Map<String,String>> list=new ArrayList<Map<String,String>>();
    	Map<String,String> map=new HashMap<String, String>();
    	map.put("哈哈.doc", "http://60.205.229.238:9980/zhengxin_File/2018-11-16/1a183ad043a64af0bde653aa718cd144.doc");
    	list.add(map);
    	new SendMailUtil("dou_shuihai@163.com", "", "你好", "mycontent", list).sendEmail();
    	System.out.println("ok");
    	
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
