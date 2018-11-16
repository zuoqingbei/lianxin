package com.hailian.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
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
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSONObject;


/**
 * 爬虫
 * @author Thinkpad
 *
 */
public class HttpCrawler {
	
	//商标网随机数
	public static Integer random = 11;
	
	public static void main(String[] args) throws Exception {
		HttpCrawler crawler = new HttpCrawler();
		//1. 中华人民共和国海关总署网站
		/*Map<String,String> postValue = crawler.getCustomersVerifyCode("2");
		String verifyCode = null;//定义验证码变量
 		Scanner in = new Scanner(System.in);
 		verifyCode = in.nextLine();
        in.close();
        crawler.getCustomsUrl("海尔集团",postValue,verifyCode);*/
		
		//2. 商务部- 解析完成
		/*CookieStore cookieStore = crawler.getMofcomVerifyCode("mofcom_1");
		String verifyCode = null;//定义验证码变量
 		Scanner in = new Scanner(System.in);
 		verifyCode = in.nextLine();
        in.close();
        crawler.getMofcomUrl("海尔集团", cookieStore, verifyCode);*/
		
		// 3. 香港查册网 - 完成解析
		//crawler.getIcrisUrl("FNETLINK CO., LIMITED");
		
		//4.全国法院被执行人信息
        HashMap<String,Object> map = crawler.getCourtVerifyCode();
        Scanner in = new Scanner(System.in);
        String verifyCode = in.nextLine();
        in.close();
		crawler.getCourtUrl("华正",verifyCode,map);
		
		//crawler.getSource();
		
		//这一步必不可少  
		/*System.setProperty("webdriver.chrome.driver","H:/hailian3/chromedriver.exe");

	    ChromeOptions options = new ChromeOptions(); 
	    options.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"); 
	    
	    Proxy proxy = new Proxy();
        proxy.setHttpProxy("http://127.0.0.1:8080")
             .setFtpProxy("http://127.0.0.1:8080")
             .setSslProxy("http://127.0.0.1:8080");
        
	    options.setProxy(proxy);
	    
	    ChromeDriver driver = new ChromeDriver(options); 
	    

	    driver.get("http://wsjs.saic.gov.cn");  */
	    //driver.findElement(By.id("kw")).sendKeys(new  String[] {"hello"});//找到kw元素的id，然后输入hello
        //driver.findElement(By.id("su")).click(); //点击按扭

	    //driver.quit();
	
		//crawler.test();
		//crawler.bulidRadom();
    }

	public CloseableHttpClient client = null;

	public HttpCrawler() {
		if(client==null) {
			//实例化httpclient
			client = HttpClients.createDefault();
		}
	}

	/*-----------------------1.海关总署-----------------------------------------------*/
	/**
	 * 1.1 中华人民共和国海关总署网站
	 * 二维码获取
	 * @param identify
	 * @return
	 */
	public Map<String,String> getCustomersVerifyCode(String identify) {
		Map<String,String> postValue = new HashMap<String,String>();
		String _verifyCode = "http://query.customs.gov.cn/HYW2007DataQuery/CompanyQuery.aspx";
		HttpClientContext context = HttpClientContext.create();
		//验证码get
		HttpGet getVerifyCode = new HttpGet(_verifyCode);
		FileOutputStream fileOutputStream = null;
		CloseableHttpResponse response = null;
		CookieStore cookieStore = new BasicCookieStore();
		try {
            response = client.execute(getVerifyCode,context);
            cookieStore = (context.getCookieStore());
            String html = EntityUtils.toString(response.getEntity(), "utf-8");
            //将返回的结果转为对象
	        Document doc = Jsoup.parse(html);
	        Elements elements = doc.select("#verifyIdentityImage");
	        if(elements!=null && elements.size()>0) {
	        	//获取img图片的src地址并进行拼接
		        String imgSrc = "http://query.customs.gov.cn" + doc.select("#verifyIdentityImage").get(0).getElementsByTag("img").attr("src");
		        //获取viewState参数值
		        String viewState = doc.select("#__VIEWSTATE").val();
		        //获取eventValidation参数值
		        String eventValidation = doc.select("#__EVENTVALIDATION").val();
		        postValue.put("viewState", viewState);
		        postValue.put("eventValidation", eventValidation);
		        //下载验证码
		        verifyCodeDownload(imgSrc,cookieStore,identify);
	        }else {
	        	System.out.println("海关总署网：未获取到验证码图片,请检查网络或确认目标网址是否有更新！");
	        }
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
        	if(fileOutputStream!=null) {
        		try {
        			fileOutputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
        	}
        }
		return postValue;
	}
	
	
	/**
	 * 1.2 爬取中华人民共和国海关总署网站
	 * @param key
	 * @param postValue 请求参数
	 * @param verifyCode 验证码
	 * @return
	 */
	public Map<String,String> getCustomsUrl(String key,Map<String,String> postValue,String verifyCode){
		Map<String,String> result = new HashMap<String,String>();
		CloseableHttpResponse response = null;
        try {
            //post请求获取返回信息
            HttpPost post = new HttpPost("http://query.customs.gov.cn/HYW2007DataQuery/CompanyQuery.aspx");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
    	    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
    	    postData.add(new BasicNameValuePair("__EVENTTARGET", ""));
            postData.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
            postData.add(new BasicNameValuePair("__VIEWSTATE", postValue.get("viewState")));
            postData.add(new BasicNameValuePair("__EVENTVALIDATION", postValue.get("eventValidation")));
            postData.add(new BasicNameValuePair("txtCompanyName", key));
            postData.add(new BasicNameValuePair("txtVerifyNumber", verifyCode));
            postData.add(new BasicNameValuePair("btnSubmit", "查询"));
            post.setEntity(new UrlEncodedFormEntity(postData,"utf-8"));//捆绑参数
            /*Header[] h1 = post.getAllHeaders();
            System.out.println("请求头:");
            for(int i=0;i<h1.length;i++) {
	        	System.out.println(h1[i].getName() +"=="+ h1[i].getValue());
	        }
            System.out.println("响应头:");
            */
	        response = client.execute(post);
	        /*Header[] headers = response.getAllHeaders();
	        for(int i=0;i<headers.length;i++) {
	        	System.out.println(headers[i].getName() +"=="+ headers[i].getValue());
	        }*/
	        String html = EntityUtils.toString(response.getEntity(), "utf-8");
	        //System.out.println(html);
	        //解析结果
	        result = parseCustomers(html);
	        System.out.println(JSONObject.toJSONString(result));
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
        return result;
	}
	
	/**
	 * 1.3 中华人民共和国海关总署网站
	 * 结果解析
	 * @param html
	 * @return
	 */
	public Map<String, String> parseCustomers(String html) {
		Map<String, String> result = new HashMap<String, String>();
		Document doc = Jsoup.parse(html);
		
		Elements scripts = doc.getElementsByTag("script");
		for(Element script:scripts) {
			String content = script.data();
			if(content!=null && content.contains("验证码错误，请重新输入")) {
				System.out.println("海关总署网：验证码错误，请重新输入！");
				return result;
			}
		}
		
		Element table = doc.getElementById("gvCmpany");
		if (table != null) {
			Elements trs = table.select("tr");
			if (trs != null && trs.size() > 1) {
				// 遍历该表格内的所有的<tr> <tr/>
				for (int i = 0; i < trs.size(); ++i) {
					// i=0表头 1=1表示数据
					if (i == 1) {
						// 获取tr
						Element tr = trs.get(i);
						// 获取该行的所有td节点
						Elements tds = tr.select("td");
						for (int j = 0; j < tds.size(); ++j) {
							switch (j) {
								case 0:
									result.put("company", tds.get(j).text());
									break;
								case 1:
									result.put("company_id", tds.get(j).text());
									break;
								case 4:
									result.put("establishment_date", tds.get(j).text());
									break;
							}	
						}
						break;
					}
				}
			} else {
				System.out.println("海关总署网：未查询到结果！");
			}
		}
		return result;
	}
	
	/*-----------------------2.商务部-----------------------------------------------*/
	/**
	 * 2.1 爬取商务部业务系统网站接口
	 */
	public CookieStore getMofcomVerifyCode(String identify){
		CookieStore cookieStore = getCookie("http://iecms.mofcom.gov.cn/corpLogin.html");
		if(null != cookieStore){
			verifyCodeDownload("http://iecms.mofcom.gov.cn/IdentifyingCode",cookieStore,identify);
		}
		return cookieStore;
	}


    /**
     * 2.2 爬取商务部业务系统网站
     * @param key
     * @param cookieStore
     * @param verifyCode
     * @return
     */
	public Map<String,String> getMofcomUrl(String key, CookieStore cookieStore, String verifyCode) {
        Map<String, String> result = new HashMap<String,String>();
		String html = "";
		if (cookieStore != null) {
			// 创建Http请求实例
			HttpPost httppost = new HttpPost("http://iecms.mofcom.gov.cn/corpLogin_listDate.html");
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
			postData.add(new BasicNameValuePair("corpNaCn", key));
			postData.add(new BasicNameValuePair("searcc", "查  询"));
			postData.add(new BasicNameValuePair("scCode", ""));
			postData.add(new BasicNameValuePair("identifyingCode", verifyCode));
			CloseableHttpResponse response = null;

			HttpClientBuilder builder = HttpClients.custom()
//	            .disableAutomaticRetries() //关闭自动处理重定向
					.setRedirectStrategy(new LaxRedirectStrategy()).setDefaultCookieStore(cookieStore);// 利用LaxRedirectStrategy处理POST重定向问题
			CloseableHttpClient httpclient = builder.build();
			try {
				httppost.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));// 捆绑参数
				response = httpclient.execute(httppost);
				Header[] headers = response.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					System.out.println(headers[i].getName() + "==" + headers[i].getValue());
				}
				html = EntityUtils.toString(response.getEntity(), "utf-8");
				Document doc = Jsoup.parse(html);
				Elements attr = doc.select("table[class=m-table]").get(0).getElementsByTag("a");
				for (Element element : attr) {
					String href = "http://iecms.mofcom.gov.cn" + element.attr("href");
					//System.out.println("href====" + href);
					HttpGet get = new HttpGet(href);
					response = httpclient.execute(get);
					html = EntityUtils.toString(response.getEntity(), "utf-8");
                    result = parseMofcom(html);
                    System.out.println(JSONObject.toJSONString(result));
                    break;
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
		return result;
	}

    /**
     * 2.3
     * @param html
     * @return
     */
	public Map<String, String> parseMofcom(String html) {
        Map<String, String> result = new HashMap<String,String>();
        Document doc = Jsoup.parse(html);
        Elements tables = doc.select("table[class=m-table1]");
        if(tables!=null&&tables.size()>0){
            Element table = tables.get(0);
            Elements trs = table.select("tr");
            if(trs!=null){
                for(int i=0;i<trs.size();i++){
                    Element tr = trs.get(i);
                    Elements tds = tr.select("td");
                    switch (i){
                        case 0:
                            if(tds!=null&&tds.size()>=2){
                                String code = tds.get(0).text();
                                result.put("code",code);
                            }
                            break;
                        case 1:
                            if(tds!=null&&tds.size()>=2){
                                String nameEn = tds.get(1).text();
                                result.put("nameEn",nameEn);
                            }
                            break;
                    }
                }
            }
        }
		return result;
	}

	
	
	/*-----------------------------3.香港查册网-------------------------------------------------*/

    /**
     * 爬取香港查册网站
     * @param company
     */
	public static void getIcrisUrl(String company) {
        //第一级页面
        HttpGet get = new HttpGet("https://www.icris.cr.gov.hk/csci/clearsession.jsp?user_type=iguest");
        CloseableHttpClient httpclient = sslClient(null);
        CloseableHttpResponse response = null;
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        try {
            response = httpclient.execute(get, context);
            //第二级页面
            get = new HttpGet("https://www.icris.cr.gov.hk/csci/login_i.do?loginType=iguest&CHKBOX_01=false&OPT_01=1&OPT_02=0&OPT_03=0&OPT_04=0&OPT_05=0&OPT_06=0&OPT_07=0&OPT_08=0&OPT_09=0&username=iguest");
            response = httpclient.execute(get, context);
            cookieStore = (context.getCookieStore());
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
        postData.add(new BasicNameValuePair("query", company));
        postData.add(new BasicNameValuePair("page", "1"));
        postData.add(new BasicNameValuePair("companyName", ""));
        postData.add(new BasicNameValuePair("moduleType", "search_company_name"));
        postData.add(new BasicNameValuePair("saveActvy", "true"));
        httpclient = sslClient(cookieStore);
        String html = "";
        try {
            post.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));//捆绑参数
            response = httpclient.execute(post);
            Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i].getName() + "==" + headers[i].getValue());
            }
            html = EntityUtils.toString(response.getEntity(), "utf-8");
            Document doc = Jsoup.parse(html);
            Elements tables = doc.select("table[class=data]");
            if (tables != null && tables.size() > 0) {
                Elements attr = tables.get(0).getElementsByTag("a");
                for (Element element : attr) {
                    String href = element.attr("href");
                    String scrNo = href.substring(href.indexOf("'") + 1, href.lastIndexOf(",") - 1);
                    System.out.println("href====" + scrNo);
                    post = new HttpPost("https://www.icris.cr.gov.hk/csci/cns_basic_comp.do");
                    postData = new ArrayList<NameValuePair>();
                    postData.add(new BasicNameValuePair("certcontent", ""));
                    postData.add(new BasicNameValuePair("desc:", ""));
                    postData.add(new BasicNameValuePair("screenPrintInd", ""));
                    postData.add(new BasicNameValuePair("searchMode", "BYNAME"));
                    postData.add(new BasicNameValuePair("searchPage", "True"));
                    postData.add(new BasicNameValuePair("searchKey", "module name is:search_company_name,company name search,search mode is:LEFT PARTIAL,search value is:" + company));
                    postData.add(new BasicNameValuePair("itemDesc", "search value is:" + company));
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
                    postData.add(new BasicNameValuePair("query", company));
                    postData.add(new BasicNameValuePair("unpaid", "true"));
                    postData.add(new BasicNameValuePair("sCRNo", scrNo));
                    postData.add(new BasicNameValuePair("showMedium", "true"));
                    postData.add(new BasicNameValuePair("showBack", "true"));
                    postData.add(new BasicNameValuePair("searchPage", "True"));
                    postData.add(new BasicNameValuePair("DPDSInd", "查阅公司名称"));
                    post.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));//捆绑参数
                    response = httpclient.execute(post);
                    html = EntityUtils.toString(response.getEntity(), "utf-8");
                    parseIcris(html);
                }
            } else {
                System.out.println("香港查册网：未查询到'" + company + "'");
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
	 * 香港查册网解析
	 * @param html
	 * @return
	 */
	public static Map<String, String> parseIcris(String html) {
		Map<String, Object> result = new HashMap<String, Object>();
		String splitChar = "_";
		Document doc = Jsoup.parse(html);
		Elements tables = doc.select("table");
		//分析结果页面，第四个表格是公司基本信息，第五个表格是变更记录
		if(tables!=null) {
			for(int z=0;z<tables.size();z++) {
				switch(z) {
					case 3: //公司基本信息
						Element table = tables.get(z);
						Elements trs = table.select("tr");
						for(int i=0;i<trs.size();i++) {
							Element tr = trs.get(i);
							Elements tds = tr.select("td");
							switch (i) {
								case 0:  //第一行 公司编号
									result.put("companyNo", icrisPaseTable(tds));
									break;
								case 1:  //第二行 公司名称
									result.put("companyName", icrisPaseTable(tds));
									break;
								case 2:  //第三行 公司类别
									result.put("companyType", icrisPaseTable(tds));
									break;
								case 3:  //第四行 成立日期
									result.put("IncorDate", icrisPaseTable(tds));
									break;
								case 4:  //第五行 公司现况
									result.put("status", icrisPaseTable(tds));
									break;
							}		
						}
						break;
					case 4:
						SortedMap<String,String> changeMap = new TreeMap<String,String>();
						//公司变更记录
						Element table2 = tables.get(z);
						Elements trs2 = table2.select("tr");
						for(int i=0;i<trs2.size();i++) {
							Element tr = trs2.get(i);
							Elements tds = tr.select("td");
							//解决单元格合并的情况
							if(tds!=null) {
								//有合并
								if(tds.size()==1) {
									String lk = changeMap.lastKey();
									String value = changeMap.get(lk);
									String first = value.split(splitChar)[0];
									changeMap.put(i+"", first+splitChar+tds.get(0).text());
								}
								//未合并
								if(tds.size()==2) {
									changeMap.put(i+"", tds.get(0).text()+splitChar+tds.get(1).text());
								}
							}
						}
						List<HashMap<String,Object>> changeList = new ArrayList<HashMap<String,Object>>();
						for(String key: changeMap.keySet()) {
							HashMap<String,Object> map = new HashMap<String,Object>();
							String v = changeMap.get(key);
							String[] vs = v.split(splitChar);
							map.put("data",vs[0]);
							map.put("name",vs[1]);
							changeList.add(map);
						}
						result.put("changeList", changeList);
						break;
				}
			}	
		}
		System.out.println(JSONObject.toJSONString(result));
		return null;
	}
	
	//香港查册-解析表格
	public static String icrisPaseTable(Elements tds) {
		String value = "";
		for(int j=0;j<tds.size();j++) {
			Element td = tds.get(j);
			if(j==0) {
				//第一列名称
				System.out.println(td.text());
			}else if(j==1) {
				//第二列取数据
				Elements spans = td.select("span");
				String txt = spans!=null&&spans.size()>0?spans.get(0).html():"";
				String[] txts = txt.split("<br>");
				if(txts.length>1) {
					System.out.println(txts[1]);
					value = txts[1];
				}else {
					System.out.println(td.text());
					value = td.text();
				}
			}
		}
		return value;
	}
	
	
	/*-----------------------------4.全国法院被执行人信息查询网------------------------------------------------*/

    /**
     * 4.1 获取验证码
     * @return
     */
    public static HashMap<String,Object> getCourtVerifyCode() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://zhixing.court.gov.cn/search/index_form.do");
        get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        HttpClientContext context = HttpClientContext.create();
        String captchaId = "";
        FileOutputStream fileOutputStream = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get, context);
            CookieStore cookieStore = context.getCookieStore();
            String html = EntityUtils.toString(response.getEntity(), "utf-8");
            Document doc = Jsoup.parse(html);
            Elements ele = doc.select("#captchaImg");
            String src = ele.attr("src");
            System.out.println(src);
            captchaId = src.substring(src.indexOf("="), src.indexOf("&"));
            String VerifyCodeUrl = "http://zhixing.court.gov.cn/search/captcha.do?captchaId=" + captchaId + "&random=" + Math.random();
            get = new HttpGet(VerifyCodeUrl);
            get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            response = httpClient.execute(get);
            //验证码写入文件,当前工程的根目录,保存为verifyCode.jpg
            String verifyCodePath = creatVerifyCodeDir("cour");
            fileOutputStream = new FileOutputStream(new File(verifyCodePath));
            response.getEntity().writeTo(fileOutputStream);
            //response.close();
            map.put("captchaId",captchaId);
            map.put("httpClient",httpClient);
            map.put("response",response);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
        return map;
    }

    /**
     * 4.2爬取全国法院被执行人信息查询网站
     * @param company
     * @param verifyCode
     * @param map
     */
	public static void getCourtUrl(String company,String verifyCode,HashMap<String,Object> map) {
        CloseableHttpClient httpClient = (CloseableHttpClient)map.get("httpClient");
        CloseableHttpResponse response = (CloseableHttpResponse)map.get("response");
        String captchaId = (String)map.get("captchaId");
        String html = "";
        try {
            try {
                HttpPost post = new HttpPost("http://zhixing.court.gov.cn/search/newsearch");
                post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                post.addHeader("Referer", "http://zhixing.court.gov.cn/search/index_form.do");
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                postData.add(new BasicNameValuePair("searchCourtName", "全国法院（包含地方各级法院）"));
                postData.add(new BasicNameValuePair("selectCourtId", "0"));
                postData.add(new BasicNameValuePair("selectCourtArrange", "1"));
                postData.add(new BasicNameValuePair("pname", company));
                postData.add(new BasicNameValuePair("cardNum", ""));
                postData.add(new BasicNameValuePair("j_captcha", verifyCode));
                postData.add(new BasicNameValuePair("captchaId", (String)map.get("captchaId")));
                post.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));//捆绑参数
                response = httpClient.execute(post);
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(html);
                Document document = Jsoup.parse(html);
                String id = document.getElementsByTag("a").attr("id");
                if (id != null && !"".equals(id)) {
                    HttpGet get = new HttpGet("http://zhixing.court.gov.cn/search/newdetail?id=" + id + "&j_captcha=" + verifyCode + "&captchaId=" + captchaId);
                    response = httpClient.execute(get);
                    html = EntityUtils.toString(response.getEntity(), "utf-8");
                    System.out.println(html);
                } else {
                    System.out.println("全国法院被执行人信息: 未查询到结果！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	//----------------------------------------------------------------------------------------------------------------------
	/**
	 * 获取cookie
	 * @param url 需要获取cookie的网址
	 * @return
	 */
	public static CookieStore getCookie(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get=new HttpGet(url);
        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        try {
            CloseableHttpResponse response = httpClient.execute(get, context);
            try{
                //System.out.println(">>>>>>headers:");
                //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
                //System.out.println(">>>>>>cookies:");
               // context.getCookieStore().getCookies().forEach(System.out::println);
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
	 * 图形验证码下载
	 * @param codeUrl  验证码地址
	 * @param cookieStore 附带的cookie信息
	 * @param identify 自定义标签
	 */
	public static void verifyCodeDownload(String codeUrl,CookieStore cookieStore,String identify) {
        //实例化httpclient
        HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //验证码get
        HttpGet getVerifyCode = new HttpGet(codeUrl);
        FileOutputStream fileOutputStream = null;
        HttpResponse response = null;
        try {
            // 获取验证码
            response = client.execute(getVerifyCode);
            //验证码写入文件,当前工程的根目录,保存为verifyCode.jpg
            String verifyCodePath = creatVerifyCodeDir(identify);
            fileOutputStream = new FileOutputStream(new File(verifyCodePath));
            response.getEntity().writeTo(fileOutputStream);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成图形验证码目录
     * @param identify
     * @return
     */
    public static String creatVerifyCodeDir(String identify){
        //验证码写入文件,当前工程的根目录,保存为verifyCode.jpg
        File _verifycode = new File("verifycode");
        if (!_verifycode.exists()) {
            _verifycode.mkdir();
        }
        return "verifycode/VerifyCode_" + identify + ".jpg";
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
	
	
	
	public void test() {
		//address = system.args[1];//获得命令行第二个参数 接下来会用到
		Integer ramdom = bulidRadom();
		String name = "海尔集团";
		Runtime rt = Runtime.getRuntime();
		String exec = "H:\\hailian3\\doc\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe H:\\hailian3\\doc\\111.js " + ramdom + " "+name;
		Process p = null;
		try {
			p = rt.exec(exec);
			InputStream is = p.getInputStream();
			
			int index;
			byte[] bytes = new byte[1024];
			FileOutputStream downloadFile = new FileOutputStream("H:\\3.txt");
			while ((index = is.read(bytes)) != -1) {
				downloadFile.write(bytes, 0, index);
				downloadFile.flush();
			}
			downloadFile.close();
			is.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public Integer bulidRadom() {
		if(random<77){
			random = random + 1;
		}else if(random==0) {
			random = 1;
		}
		System.out.println(random);
		return random;
	}

	
	
}
