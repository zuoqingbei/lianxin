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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class TransApi {	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
         String body="条条道路通罗马";
         String from="zh";
         String to="en";
         
         String result=getResult(body,from,to);
         System.out.println("{"+result);
         String content="{"+result;
         String json=getDate(content);
         System.out.println(json);
	}
	//百度平台（翻译接口）相关数据
	public static String getResult(String body,String from ,String to){
		String result="";
		//拼接相关参数
		String params="http://openapi.baidu.com/public/2.0/bmt/translate?client_id=cxE4PWzno4Zx13LAvHX7ND5j&q="+body+"&from="+from+"&to="+to;		
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
	
	}


