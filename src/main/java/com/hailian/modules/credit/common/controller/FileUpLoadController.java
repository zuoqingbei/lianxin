package com.hailian.modules.credit.common.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.ImageModel;
import com.hailian.component.util.ImageUtils;
import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.image.model.TbImage;
import com.hailian.modules.admin.image.model.TbImageTags;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.system.file.util.FileUploadUtils;
import com.hailian.util.StrUtils;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;


/*
* 文件上传
* @author ljm  
* @date 2018年4月16日
* 
*/
@ControllerBind(controllerKey = "/admin/file")
public class FileUpLoadController extends BaseProjectController {
	public void index(){
		render("xd/pages/06_02yuebaoshangchuan.html");
	}
	//文件上传
	public void upload() {
		TbSite site = getBackSite();
		System.out.println("路径===================================："+FileUploadUtils.getUploadTmpPath(site));
		UploadFile uploadImage = getFile("model.file_url", FileUploadUtils.getUploadTmpPath(site), FileUploadUtils.UPLOAD_MAX);
		
		
		


	
	
		
		renderMessage("保存成功");
	} 
	
	//文件预览
	public void fileView() {
		try {
			HttpServletResponse response = getResponse();
			boolean boo = true;
			String fileName = getPara("fileName");
			String filePath = PropKit.get("uploadPath")+"/"+fileName;
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			if (boo) { // 在线打开（预览）
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; fileName=" + f.getName());
			} 
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
			renderNull();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
	
	

