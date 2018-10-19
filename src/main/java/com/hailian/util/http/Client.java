package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
    
    public Client(String accout, String password) {
        super();
        this.accout = accout;
        this.password = password;
    }
 
    public void login() {
        HttpGet getLoginPage = new HttpGet("http://218.7.49.34/loginAction.do");//教务处登陆页面get
        
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
            
            //设定post参数，和上图中的内容一致
            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("zjh1", ""));
            postData.add(new BasicNameValuePair("tips", ""));
            postData.add(new BasicNameValuePair("lx", ""));
            postData.add(new BasicNameValuePair("eflag", ""));
            postData.add(new BasicNameValuePair("fs", ""));
            postData.add(new BasicNameValuePair("dzslh", ""));
            postData.add(new BasicNameValuePair("zjh", accout));//学号
            postData.add(new BasicNameValuePair("mm", password));//密码
            postData.add(new BasicNameValuePair("v_yzm", code));//验证码
            
            HttpPost post = new HttpPost("http://218.7.49.34/loginAction.do");//构建post对象
            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数
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
        HttpGet getVerifyCode = new HttpGet("http://218.7.49.34/validateCodeAction.do");//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));
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
