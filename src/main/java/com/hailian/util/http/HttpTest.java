package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import com.hailian.util.extend.HttpClientUtils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class HttpTest {
	
	public static void main(String[] args) throws TesseractException {
		getMofcomVerifyCode();
//		CookieStore cookieStore = getCookie();
//		List<Cookie> cookies = null;
//		if(null != cookieStore){
//			cookies = cookieStore.getCookies();
//			for (int i = 0; i < cookies.size(); i++) {
//				System.out.println("Local cookie: " + cookies.get(i));
//			}
//		}
		File imageFile = new File("mofcomVerifyCode.jpg");//图片位置
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath("C:\\Users\\Administrator\\Desktop\\tessdata");//设置tessdata位置
        instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
        String result = null;
		try {
			result = instance.doOCR(imageFile);
			System.out.println("图片验证码:"+result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//开始识别
		String name = "海尔集团";
		String verifyCode = result;
		//HttpClientUtils.sendGet("","");
		System.out.println(getSource("http://iecms.mofcom.gov.cn/pages/corp/NewCMvCorpInfoTabList.html", "sp=S&sp=S"+name+"&sp=S&sp=Ssearch&sp=S"+verifyCode));
		//HttpClientUtils.sendGet("http://wsjs.saic.gov.cn/txnS02.do", "y7bRbp=qmMEweVsmDl9_cN6OQYJvmxp_gbEk8.KgI.mouS16BABs9C4.A7NcK_F_zIxwkWtQHERRNxbxt8j469VFNXeZR4eMLZxKZkJiJ8sqy1gT5PRFQDP9hyeWue5nEOD1VxhiQeIt2mWemsaaD8_zKMPL_6BMVSiMeOe0vBRPh2SNDLS6GqX&c1K5tw0w6_=2d2XcxYZ7RcRNijUy8YPC6ahXg2of22SgU7RkwuP4m_o9um3vxueRoIrMYSXxpGJe3fnageWWOEiGpFVPs6sCfeCXX51Nrrf91urOx1zA9zOV7E.VZIjC5KgP6I4ov0Ga");
		
    }
	
	public static String getSource(String url,String param) {
	    String html = new String();
	    HttpGet httpget = new HttpGet(url + "?" + param);     //创建Http请求实例，URL 如：https://cd.lianjia.com/
	    // 模拟浏览器，避免被服务器拒绝，返回返回403 forbidden的错误信息
	    httpget.setHeader("User-Agent", 
	    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");

	    CloseableHttpResponse response = null;
	    CloseableHttpClient httpclient = HttpClients.createDefault();   // 使用默认的HttpClient
	    try {
	        response = httpclient.execute(httpget);
	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     // 返回 200 表示成功
	            html = EntityUtils.toString(response.getEntity(), "utf-8");     // 获取服务器响应实体的内容
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (response != null) {
	            try {
	                response.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return html;
	}
	
	/**
	 * 爬取中国商标网
	 */
	public void getSaic(){
		HttpClientUtils.sendGet("http://wsjs.saic.gov.cn/txnS02.do", "y7bRbp=qmMEweVsmDl9_cN6OQYJvmxp_gbEk8.KgI.mouS16BABs9C4.A7NcK_F_zIxwkWtQHERRNxbxt8j469VFNXeZR4eMLZxKZkJiJ8sqy1gT5PRFQDP9hyeWue5nEOD1VxhiQeIt2mWemsaaD8_zKMPL_6BMVSiMeOe0vBRPh2SNDLS6GqX&c1K5tw0w6_=2d2XcxYZ7RcRNijUy8YPC6ahXg2of22SgU7RkwuP4m_o9um3vxueRoIrMYSXxpGJe3fnageWWOEiGpFVPs6sCfeCXX51Nrrf91urOx1zA9zOV7E.VZIjC5KgP6I4ov0Ga");
	}
	
	/**
	 * html转xml
	 * @param html_data
	 * @return
	 * @throws Exception
	 */
	public static String html2xmlstr(String html_data) throws Exception {
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties properties = cleaner.getProperties();
		// 忽略注释
		properties.setOmitComments(true);
		// 忽略script，style标签
		properties.setPruneTags("script,style");
		// 忽略命名空间
		properties.setNamespacesAware(false);
		// 忽略DOCTYPE声明
		properties.setOmitDoctypeDeclaration(true);
		// properties.setUseEmptyElementTags(false);

		// CleanerTransformations transformations =
		// new CleanerTransformations();
		// TagTransformation tt = new TagTransformation("br");
		// transformations.addTransformation(tt);
		// properties.setCleanerTransformations(transformations);

		TagNode node = cleaner.clean(html_data);
		// 此版本会最大性兼容不规范的html代码
		// utf-8表示生成的xml文件<?xml version="1.0" encoding="utf-8"?>
		String xmlData = new CompactXmlSerializer(properties).getAsString(node,"utf-8");
		return xmlData;
	}
	
	/**
	 * 获取cookie
	 * @return cookieStore
	 */
	public static CookieStore getCookie(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get=new HttpGet("http://wsjs.saic.gov.cn/txnS02.do?locale=zh_CN&y7bRbP=KGllkAkMCeBMCeBMCuBePKpXwi13g6LNC87VouCucyOM");
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = null;
        try {
            CloseableHttpResponse response = httpClient.execute(get, context);
            try{
                System.out.println(">>>>>>headers:");
                Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
                System.out.println(">>>>>>cookies:");
                context.getCookieStore().getCookies().forEach(System.out::println);
                cookieStore = (context.getCookieStore());
            }
            finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cookieStore;
    }
	
	/**
	 * 爬取全国法院被执行人信息查询网站
	 */
	public void getCourt(){
		getCourtVerifyCode();
		File imageFile = new File("verifyCode.jpeg");//图片位置
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath("C:\\Users\\Administrator\\Desktop\\tessdata");//设置tessdata位置
        instance.setLanguage("osd");//选择字库文件（只需要文件名，不需要后缀名）
        String result = null;
		try {
			result = instance.doOCR(imageFile);
			System.out.println("图片验证码:"+result);
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//开始识别
		String searchCourtName = "全国法院（包含地方各级法院）";
		String selectCourtId = "0";
		String selectCourtArrange = "1";
		String pname = "海尔集团";
		String cardNum = "";
		String j_captcha = "an72";
		String captchaId = "e1032fa9141e42288685fc15acbd4e49";
		HttpClientUtils.sendGet("http://zhixing.court.gov.cn/search/newsearch",
				"searchCourtName="+searchCourtName+"&selectCourtId="+selectCourtId+
				"&selectCourtArrange="+selectCourtArrange+"&pname="+pname+"&cardNum="+cardNum+
				"&j_captcha="+j_captcha+"&captchaId="+captchaId);
	}
	
	/**
	 * 获取验证码（全国法院被执行人信息查询网站)
	 */
	public static void getCourtVerifyCode(){
		HttpClient client = HttpClients.createDefault();//实例化httpclient
		HttpGet getVerifyCode = new HttpGet("http://zhixing.court.gov.cn/search/captcha.do");//验证码get
		FileOutputStream fileOutputStream = null;
		HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("courtVerifyCode.jpg"));
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
	
	/**
	 * 获取商务部业务系统统一平台验证码
	 */
	public static void getMofcomVerifyCode(){
		HttpClient client = HttpClients.createDefault();//实例化httpclient
		HttpGet getVerifyCode = new HttpGet("http://iecms.mofcom.gov.cn/IdentifyingCode");//验证码get
		FileOutputStream fileOutputStream = null;
		HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("mofcomVerifyCode.jpg"));
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
