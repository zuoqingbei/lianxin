package com.hailian.modules.credit.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.jfinal.kit.PropKit;
@ControllerBind(controllerKey = "/credit/commonFile")
public class FileViewAndUpController extends BaseProjectController{
	public void previewFileOnline() {
		HttpServletResponse response = getResponse();
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			String path = getPara("url");//网络图片地址
			path=new String(getPara("url").getBytes("iso8859-1"),"utf-8");
			String type = getPara("type");//网络图片地址
			response.reset(); // 非常重要
			response.setContentType("text/html; charset=UTF-8");
	        	if(!FileTypeUtils.isImg(type)){
	        		response.setContentType("application/pdf");
	        	}else{
	        		response.setContentType("image/jpeg");
	        	}
	        	URL url =new URL(path);
	       	 	bis = new BufferedInputStream(url.openStream());
	        	os = response.getOutputStream();
	        	int count = 0;
	        	byte[] buffer = new byte[1024 * 1024];
	        	while ((count =bis.read(buffer)) != -1){
	        		os.write(buffer, 0,count);
	        	}
	        	os.flush();
		}catch (Exception e) {
			e.printStackTrace();
			if (os !=null){
            	try {
			os.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
            }
            if (bis !=null){
            	try {
			bis.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
            }
		} finally {
	            if (os !=null){
	            	try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	            }
	            if (bis !=null){
	            	try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	            }
	        }
	}
}
