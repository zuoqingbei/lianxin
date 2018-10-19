package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.hailian.util.extend.HttpClientUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class HttpTest {
	public static void main(String[] args) throws TesseractException {
		//getVerifyCode();
		File imageFile = new File("D:\\myWork\\lianxin\\QQ图片20181017143832.png");//图片位置
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath("C:\\Users\\Administrator\\Desktop\\tessdata");//设置tessdata位置
        instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
        String result = instance.doOCR(imageFile);//开始识别
        System.out.println("图片验证码:"+result);
		String searchCourtName = "全国法院（包含地方各级法院）";
		String selectCourtId = "1";
		String selectCourtArrange = "2";
		String pname = "海尔集团";
		String cardNum = "";
		String j_captcha = result;
		String captchaId = "366232f0348c442e9599cbd9f4235dd4";
		HttpClientUtils.sendGet("http://zhixing.court.gov.cn/search/newsearch",
				"searchCourtName="+searchCourtName+"&selectCourtId="+selectCourtId+
				"&selectCourtArrange="+selectCourtArrange+"&pname="+pname+"&cardNum="+cardNum+
				"&j_captcha="+j_captcha+"&captchaId="+captchaId);
		
    }
	public static void getVerifyCode(){
		HttpClient client = HttpClients.createDefault();//实例化httpclient
		HttpGet getVerifyCode = new HttpGet("http://zhixing.court.gov.cn/search/captcha.do");//验证码get
		FileOutputStream fileOutputStream = null;
		HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("verifyCode.png"));
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
