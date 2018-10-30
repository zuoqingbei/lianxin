package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Client {
    private String accout;
    private String password;
    HttpClient client = HttpClients.createDefault();//实例化httpclient
    HttpResponse response = null;
    String rawHtml;
    
    public void login() {
        HttpGet getLoginPage = new HttpGet("http://iecms.mofcom.gov.cn/corpLogin.html");//教务处登陆页面get
        
        try {
            //打开教务处
            client.execute(getLoginPage);
            //获取验证码
            getVerifyingCode(client);
            
            //提醒用户并输入验证码
            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");
            String code;
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            in.close();
            
            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
    	    postData.add(new BasicNameValuePair("formids", "corpCode,corpNaCn,searcc,scCode,identifyingCode"));
            postData.add(new BasicNameValuePair("session:", "T"));
            postData.add(new BasicNameValuePair("updateParts", "listData"));
            postData.add(new BasicNameValuePair("reservedids", "updateParts"));
            postData.add(new BasicNameValuePair("submitmode", ""));
            postData.add(new BasicNameValuePair("submitname", ""));
            postData.add(new BasicNameValuePair("corpCode", ""));
            postData.add(new BasicNameValuePair("corpNaCn", "海尔集团"));
            postData.add(new BasicNameValuePair("searcc", "查  询"));
            postData.add(new BasicNameValuePair("scCode", ""));
            postData.add(new BasicNameValuePair("identifyingCode", code));
            
            HttpPost post = new HttpPost("http://iecms.mofcom.gov.cn/corpLogin_listDate.html");//构建post对象
            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数
            post.setHeader("User-Agent", 
            	    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            response = client.execute(post);//执行登陆行为
            rawHtml = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(rawHtml);
 
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    void getVerifyingCode(HttpClient client) {
        HttpGet getVerifyCode = new HttpGet("http://iecms.mofcom.gov.cn/IdentifyingCode");//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("verifyCode.jpg"));
            response.getEntity().writeTo(fileOutputStream);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}
