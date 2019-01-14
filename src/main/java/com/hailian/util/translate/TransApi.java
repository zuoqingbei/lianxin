package com.hailian.util.translate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.hailian.util.Config;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class TransApi {	 
	public static final String appId = Config.getStr("appid");//百度翻译appid
	public static final String secretkey = Config.getStr("secretkey");//百度翻译秘钥
	public static String Trans(String q,String targetlanguage) {//targetlanguage目标语言  en英语  cht中文繁体
         String appid=appId;
//         String appid="20181213000247765";
         String salt=String.valueOf(new Random().nextInt(100));
         String sign=appid+q+salt+secretkey;
//         String sign=appid+q+salt+"HN0OWJ2NVBDvAAbjE0Z5";
         MessageDigest md5;
 		try {
 			md5 = MessageDigest.getInstance("MD5");
 			 md5.update((sign).getBytes("UTF-8"));
 	         byte b[] = md5.digest();
 	          
 	         int i;
 	         StringBuffer buf = new StringBuffer("");
 	          
 	         for(int offset=0; offset<b.length; offset++){
 	         	i = b[offset];
 	         	if(i<0){
 	         		i+=256;
 	         	}
 	         	if(i<16){
 	         		buf.append("0");
 	         	}
 	         	buf.append(Integer.toHexString(i));
 	         }
 	 	   
 	         sign = buf.toString();
 		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();

 		} 
 		 String result=getResult(q,sign,appid,salt,targetlanguage);
         String content="{"+result;
         String json=getDate(content);
         return json;
	}
	//百度平台（翻译接口）相关数据
	public static String getResult(String q,String sign,String appid,String salt,String targetlanguage){
		String result="";
		//拼接相关参数
		String params="http://api.fanyi.baidu.com/api/trans/vip/translate?q="+q+"&from=auto&to="+targetlanguage+"&appid="+appid+"&salt="+salt+"&sign="+sign;		
		 try {
			URL url = new URL(params);
			URLConnection connection = url.openConnection();  
			//设置连接时间(10*1000)
			connection.setConnectTimeout(10*1000);
		    //设置输出
			connection.setDoOutput(true);
			//设置输出
			connection.setDoInput(true);
            //设置缓存
			connection.setUseCaches(false);			
			//outputstream-----输出流
			InputStream inputstream=connection.getInputStream();
			//缓存字符流
			BufferedReader buffer = new BufferedReader(new InputStreamReader(inputstream)); 
			//返回相关结果
			StringBuilder builder=new StringBuilder();
			while(buffer.read()!=-1){
				builder.append(buffer.readLine());				
			}
			//返回相关结果
			result=builder.toString();
			//缓存字符流关闭操作
			buffer.close();
 
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	//解析百度服务器平台返回的相关数据信息
	public static String getDate(String result){
        System.out.println(result);
		String date="";
		
		JSONObject object=JSONObject.fromObject(result);
		JSONArray array=object.getJSONArray("trans_result");
		int length=array.size();
		for(int i=0;i<length;i++){
			JSONObject params=JSONObject.fromObject(array.get(i));
			String str=params.getString("dst");
			try {
				str=URLDecoder.decode(str,"utf-8");
				date=str;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}	
		return date;
		
	}
 
	public static void main(String[] args) {
		String trans = Trans("信息安全及保密承诺书","cht");
		String transTOEn = Trans("信息安全及保密承诺书","en");
		System.out.println(trans);
		System.out.println(transTOEn);
	}
}


