package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpTest {
	
	public static void main(String[] args) throws Exception {
		
		//getCustomsUrl();//爬取企业信息基本情况
		//getSourceUrl();//爬取商务部业务系统网站
		
		//CookieStore cookieStore = getIcrisCookie();//获取香港查册网站cookie信息
		//getIcrisUrl(cookieStore);//爬取香港查册网站
		
		getCourtUrl();
		
    }
	
	public static void getCourtUrl(CookieStore cookieStore){
		
	}
	
	
	
	/**
	 * 获取香港查册网站cookie信息
	 * @return
	 */
	public static CookieStore getIcrisCookie(){
		HttpGet get = new HttpGet("https://www.icris.cr.gov.hk/csci/clearsession.jsp?user_type=iguest");
		CloseableHttpClient httpclient = sslClient(null);
		CloseableHttpResponse response = null;
		//String html = "";
		HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
		try {
	        response = httpclient.execute(get,context);
	        get = new HttpGet("https://www.icris.cr.gov.hk/csci/login_i.do?loginType=iguest&CHKBOX_01=false&OPT_01=1&OPT_02=0&OPT_03=0&OPT_04=0&OPT_05=0&OPT_06=0&OPT_07=0&OPT_08=0&OPT_09=0&username=iguest");
	        response = httpclient.execute(get,context);
//            System.out.println(">>>>>>headers:");
//            Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
//            System.out.println(">>>>>>cookies:");
//            context.getCookieStore().getCookies().forEach(System.out::println);
            cookieStore = (context.getCookieStore());
//	        Header[] headers = response.getAllHeaders();
//	        for(int i=0;i<headers.length;i++) {
//	        	System.out.println(headers[i].getName() +"=="+ headers[i].getValue());
//	        }
	        //html = EntityUtils.toString(response.getEntity(), "utf-8");
	        //System.out.println(html);
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
		return cookieStore;
	}
	/**
	 * 爬取香港查册网站
	 */
	public static void getIcrisUrl(CookieStore cookieStore){
		HttpPost post = new HttpPost("https://www.icris.cr.gov.hk/csci/search_company_name.do");
		post.setHeader("User-Agent", 
				    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
	    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
	    postData.add(new BasicNameValuePair("nextAction", "search_company_name"));
        postData.add(new BasicNameValuePair("searchPage:", "True"));
        postData.add(new BasicNameValuePair("searchMode", "BYNAME"));
        postData.add(new BasicNameValuePair("firstSearchPage", "True"));
        postData.add(new BasicNameValuePair("mode", "LEFT PARTIAL"));
        postData.add(new BasicNameValuePair("language", "en"));
        postData.add(new BasicNameValuePair("query", "FNETLINK CO,.LIMITED"));
        postData.add(new BasicNameValuePair("page", "1"));
        postData.add(new BasicNameValuePair("companyName", "FNETLINK CO,.LIMITED"));
        postData.add(new BasicNameValuePair("moduleType", "search_company_name"));
        postData.add(new BasicNameValuePair("saveActvy", "true"));
	    CloseableHttpResponse response = null;
	    CloseableHttpClient httpclient = sslClient(cookieStore);
	    String html = "";
	    try {
	    	post.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
	        response = httpclient.execute(post);
	        Header[] headers = response.getAllHeaders();
	        for(int i=0;i<headers.length;i++) {
	        	System.out.println(headers[i].getName() +"=="+ headers[i].getValue());
	        }
	        html = EntityUtils.toString(response.getEntity(), "utf-8");
	        //System.out.println(html);
	        Document doc = Jsoup.parse(html);
	        Elements attr = doc.select("table[class=data]").get(0).getElementsByTag("a");
	        for(Element element : attr){
	        	String href = element.attr("href");
	        	String scrNo = href.substring(href.indexOf("'")+1,href.lastIndexOf(",")-1);
	        	System.out.println("href===="+scrNo);
	        	post = new HttpPost("https://www.icris.cr.gov.hk/csci/cns_basic_comp.do");
	        	postData = new ArrayList<NameValuePair>();
	    	    postData.add(new BasicNameValuePair("certcontent", ""));
	            postData.add(new BasicNameValuePair("desc:", ""));
	            postData.add(new BasicNameValuePair("screenPrintInd", ""));
	            postData.add(new BasicNameValuePair("searchMode", "BYNAME"));
	            postData.add(new BasicNameValuePair("searchPage", "True"));
	            postData.add(new BasicNameValuePair("searchKey", "module name is:search_company_name,company name search,search mode is:LEFT PARTIAL,search value is:FNETLINK CO,.LIMITED"));
	            postData.add(new BasicNameValuePair("itemDesc", "search value is:FNETLINK CO,.LIMITED"));
	            postData.add(new BasicNameValuePair("productCode", "SCRPRT"));
	            postData.add(new BasicNameValuePair("moduleName", "search_company_name"));
	            postData.add(new BasicNameValuePair("certificate", "search_company_name"));
	            postData.add(new BasicNameValuePair("cnsDesc", "查阅公司名称"));
	            postData.add(new BasicNameValuePair("fromCnsOfc", "N"));
	            postData.add(new BasicNameValuePair("isScreenSearch", ""));
	            postData.add(new BasicNameValuePair("page", "1"));
	            postData.add(new BasicNameValuePair("lastPage", "1"));
	            postData.add(new BasicNameValuePair("radioButton", "BYCRNO"));
	            postData.add(new BasicNameValuePair("nextAction", "search_company_name"));
	            postData.add(new BasicNameValuePair("selectPage", "1"));
	            postData.add(new BasicNameValuePair("mode", "LEFT PARTIAL"));
	            postData.add(new BasicNameValuePair("language", "en"));
	            postData.add(new BasicNameValuePair("query", "FNETLINK CO,.LIMITED"));
	            postData.add(new BasicNameValuePair("unpaid", "true"));
	            postData.add(new BasicNameValuePair("sCRNo", scrNo));
	            postData.add(new BasicNameValuePair("showMedium", "true"));
	            postData.add(new BasicNameValuePair("showBack", "true"));
	            postData.add(new BasicNameValuePair("searchPage", "True"));
	            postData.add(new BasicNameValuePair("DPDSInd", "查阅公司名称"));
	            post.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
	        	response = httpclient.execute(post);
	        	html = EntityUtils.toString(response.getEntity(), "utf-8");
	        	System.out.println(html);
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
	}
	
	/**
	 * 创建ssl链接
	 * @return
	 */
	private static CloseableHttpClient sslClient(CookieStore cookieStore) {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override public void checkClientTrusted(X509Certificate[] xcs, String str) {}
                @Override public void checkServerTrusted(X509Certificate[] xcs, String str) {}
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { trustManager }, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https",socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
	
	/**
	 * 爬取商务部业务系统网站接口
	 */
	public static void getSourceUrl(){
		CookieStore cookieStore = getCookie();
		if(null != cookieStore){
			getMofcomVerifyCode(cookieStore);
			String verifyCode = null;
			Scanner in = new Scanner(System.in);
			verifyCode = in.nextLine();
	        in.close();
	        getSource("", cookieStore, verifyCode);
		}
		//图片识别
//		File imageFile = new File("mofcomVerifyCode.jpg");//图片位置
//        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//        instance.setDatapath("C:\\Users\\Administrator\\Desktop\\tessdata");//设置tessdata位置
//        instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
//        String verifyCode = null;
//		try {
//			//开始识别
//			verifyCode = instance.doOCR(imageFile);
//			System.out.println("图片验证码:"+verifyCode);
//		} catch (TesseractException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 爬取商务部业务系统网站
	 * @param url
	 * @param cookieStore
	 * @param verifyCode
	 * @return
	 */
	public static String getSource(String url,CookieStore cookieStore,String verifyCode) {
	    String html = new String();
	    HttpPost httppost = new HttpPost("http://iecms.mofcom.gov.cn/corpLogin_listDate.html");//创建Http请求实例
	    // 模拟浏览器，避免被服务器拒绝，返回返回403 forbidden的错误信息
	    httppost.setHeader("User-Agent", 
	    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
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
        postData.add(new BasicNameValuePair("identifyingCode", verifyCode));
	    CloseableHttpResponse response = null;
	    
	    HttpClientBuilder builder = HttpClients.custom()
//	            .disableAutomaticRetries() //关闭自动处理重定向
	            .setRedirectStrategy(new LaxRedirectStrategy()).setDefaultCookieStore(cookieStore);//利用LaxRedirectStrategy处理POST重定向问题
	    CloseableHttpClient httpclient = builder.build();
	    try {
	    	httppost.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
	        response = httpclient.execute(httppost);
	        Header[] headers = response.getAllHeaders();
	        for(int i=0;i<headers.length;i++) {
	        	System.out.println(headers[i].getName() +"=="+ headers[i].getValue());
	        }
	        html = EntityUtils.toString(response.getEntity(), "utf-8");
	        //System.out.println(html);
	        Document doc = Jsoup.parse(html);
	        Elements attr = doc.select("table[class=m-table]").get(0).getElementsByTag("a");
	        //Elements attr = doc.getElementsByTag("a");
	        for(Element element : attr){
	        	String href = "http://iecms.mofcom.gov.cn"+element.attr("href");
	        	System.out.println("href===="+href);
	        	HttpGet get = new HttpGet(href);
	        	response = httpclient.execute(get);
	        	html = EntityUtils.toString(response.getEntity(), "utf-8");
	        	System.out.println(html);
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
		//HttpClientUtils.sendGet("http://wsjs.saic.gov.cn/txnS02.do", "y7bRbp=qmMEweVsmDl9_cN6OQYJvmxp_gbEk8.KgI.mouS16BABs9C4.A7NcK_F_zIxwkWtQHERRNxbxt8j469VFNXeZR4eMLZxKZkJiJ8sqy1gT5PRFQDP9hyeWue5nEOD1VxhiQeIt2mWemsaaD8_zKMPL_6BMVSiMeOe0vBRPh2SNDLS6GqX&c1K5tw0w6_=2d2XcxYZ7RcRNijUy8YPC6ahXg2of22SgU7RkwuP4m_o9um3vxueRoIrMYSXxpGJe3fnageWWOEiGpFVPs6sCfeCXX51Nrrf91urOx1zA9zOV7E.VZIjC5KgP6I4ov0Ga");
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
        HttpGet get=new HttpGet("http://iecms.mofcom.gov.cn/corpLogin.html");
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
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
	public static void getCourtUrl(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get=new HttpGet("http://zhixing.court.gov.cn/search/index_form.do");
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        FileOutputStream fileOutputStream = null;
        String html = "";
        try {
            CloseableHttpResponse response = httpClient.execute(get, context);
            try{
                System.out.println(">>>>>>headers:");
                Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
                System.out.println(">>>>>>cookies:");
                context.getCookieStore().getCookies().forEach(System.out::println);
                cookieStore = (context.getCookieStore());
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                //System.out.println(html);
                Document doc = Jsoup.parse(html);
                Elements ele = doc.select("#captchaImg");
                String src = ele.attr("src");
                String captchaId = src.substring(src.indexOf("="),src.indexOf("&"));
                System.out.println("captchaId===="+captchaId);
                System.out.println(Math.random());
                get = new HttpGet("http://zhixing.court.gov.cn/search/captcha.do?captchaId="+captchaId+"&random="+Math.random());
                httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
                response = httpClient.execute(get);//获取验证码
                /*验证码写入文件,当前工程的根目录,保存为verifyCode.jpg*/
                fileOutputStream = new FileOutputStream(new File("courtVerifyCode.jpg"));
                response.getEntity().writeTo(fileOutputStream);
                Scanner input = new Scanner(System.in);
                String verifyCode = input.nextLine();
                HttpPost post = new HttpPost("http://zhixing.court.gov.cn/search/newsearch");
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
        	    postData.add(new BasicNameValuePair("searchCourtName", "全国法院（包含地方各级法院）"));
                postData.add(new BasicNameValuePair("selectCourtId", "0"));
                postData.add(new BasicNameValuePair("selectCourtArrange", "1"));
                postData.add(new BasicNameValuePair("pname", "海尔集团"));
                postData.add(new BasicNameValuePair("cardNum", ""));
                postData.add(new BasicNameValuePair("j_captcha", verifyCode));
                postData.add(new BasicNameValuePair("captchaId", captchaId));
                post.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
                response = httpClient.execute(post);
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(html);
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
    }
	
	/**
	 * 获取验证码（全国法院被执行人信息查询网站)
	 * @param cookieStore
	 */
	public static void getCourtVerifyCode(CookieStore cookieStore){
		
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();//实例化httpclient
		HttpGet getVerifyCode = new HttpGet("http://zhixing.court.gov.cn/search/captcha.do");//验证码get
		FileOutputStream fileOutputStream = null;
		HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jpg*/
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
	 * @param cookieStore
	 */
	public static void getMofcomVerifyCode(CookieStore cookieStore){
		HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();//实例化httpclient
		HttpGet getVerifyCode = new HttpGet("http://iecms.mofcom.gov.cn/IdentifyingCode");//验证码get
		FileOutputStream fileOutputStream = null;
		HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jpg*/
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
	
	/**
	 * 爬取中华人民共和国海关总署网站
	 */
	public static void getCustomsUrl(){
		CloseableHttpClient client = HttpClients.createDefault();//实例化httpclient
		HttpClientContext context = HttpClientContext.create();
		HttpGet getVerifyCode = new HttpGet("http://query.customs.gov.cn/HYW2007DataQuery/CompanyQuery.aspx");//验证码get
		FileOutputStream fileOutputStream = null;
		CloseableHttpResponse response = null;
		CookieStore cookieStore = new BasicCookieStore();
        try {
            response = client.execute(getVerifyCode,context);
            cookieStore = (context.getCookieStore());
            String html = EntityUtils.toString(response.getEntity(), "utf-8");
	        Document doc = Jsoup.parse(html);
	        //获取img图片的src地址并进行拼接
	        String imgSrc = "http://query.customs.gov.cn" + doc.select("#verifyIdentityImage").get(0).getElementsByTag("img").attr("src");
	        //获取viewState参数值
	        String viewState = doc.select("#__VIEWSTATE").val();
	        //获取eventValidation参数值
	        String eventValidation = doc.select("#__EVENTVALIDATION").val();
	        //获取验证码
	        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	        HttpGet get = new HttpGet(imgSrc);
	        response = client.execute(get);
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jpg*/
            fileOutputStream = new FileOutputStream(new File("customsVerifyCode.jpg"));
            response.getEntity().writeTo(fileOutputStream);
            String verifyCode = null;//定义验证码变量
    		Scanner in = new Scanner(System.in);
    		verifyCode = in.nextLine();
            in.close();
            //post请求获取返回信息
            HttpPost post = new HttpPost("http://query.customs.gov.cn/HYW2007DataQuery/CompanyQuery.aspx");
            post.setHeader("User-Agent", 
            	    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
    	    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
    	    postData.add(new BasicNameValuePair("__EVENTTARGET", ""));
            postData.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
            System.out.println("viewState============"+viewState);
            postData.add(new BasicNameValuePair("__VIEWSTATE", viewState));
            System.out.println("eventValidation================"+eventValidation);
            postData.add(new BasicNameValuePair("__EVENTVALIDATION", eventValidation));
            postData.add(new BasicNameValuePair("txtCompanyName", "海尔集团"));
            postData.add(new BasicNameValuePair("txtVerifyNumber", verifyCode));
            postData.add(new BasicNameValuePair("btnSubmit", "查询"));
            post.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
            Header[] h1 = post.getAllHeaders();
            System.out.println("请求头:");
            for(int i=0;i<h1.length;i++) {
	        	System.out.println(h1[i].getName() +"=="+ h1[i].getValue());
	        }
            System.out.println("响应头:");
	        response = client.execute(post);
	        Header[] headers = response.getAllHeaders();
	        for(int i=0;i<headers.length;i++) {
	        	System.out.println(headers[i].getName() +"=="+ headers[i].getValue());
	        }
	        html = EntityUtils.toString(response.getEntity(), "utf-8");
	        System.out.println(html);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
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
	}
}
