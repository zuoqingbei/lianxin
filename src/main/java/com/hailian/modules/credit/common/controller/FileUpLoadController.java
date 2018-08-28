package com.hailian.modules.credit.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.system.file.util.FileUploadUtils;
import com.hailian.util.DateUtils;
import com.hailian.util.FTP_UploadFileUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

/**
 * 文件上传
* @author doushuihai  
* @date 2018年8月27日上午11:36:12  
* @TODO
 */
@ControllerBind(controllerKey = "/admin/file")
public class FileUpLoadController extends BaseProjectController {
	public static final String ip = "60.205.229.238";//ftp文件服务器 ip
	public static final int port = 21;//ftp端口 默认21
	public static final String userName = "test";//域用户名
	public static final String password = "test";//域用户密码
	public void uploadFile(){
		UploadFile uploadFile = getFile("model.file_url");//从前台获取文件
		uploadFile.getContentType();
		String business_type = getPara("business_type");
		String business_id = getPara("business_id");
		// 文件附件
		try {
			if (uploadFile != null) {
				String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
				String fileName=DateUtils.getNow(DateUtils.YMDHMS);
				boolean storeFile = FTP_UploadFileUtils.storeFile(fileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
				if(storeFile){
					int dot = uploadFile.getOriginalFileName().lastIndexOf(".");
					String ext="";
					if (dot != -1) {
						ext = uploadFile.getOriginalFileName().substring(dot + 1);
					} else {
						ext = "";
					}
					String factpath=storePath+"/"+fileName+"."+ext;
					String url="http://"+ip+"/" + storePath+"/"+fileName+"."+ext;
					Integer userid = getSessionUser().getUserid();
					UploadFileService.service.save(uploadFile, factpath,url,business_type,business_id,fileName,userid);//记录上传信息
					List<CreditUploadFileModel> fileList = UploadFileService.service.getByBusIdAndBusType(business_id, business_type,this);
					renderJson(fileList);
				}else{
					renderMessage("上传失败！");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderMessage("项目出现异常，上传失败！");
		}
	}
	public void uploadMultipleFile(){
		UploadFile uploadFile = getFile("model.file_url");//从前台获取文件
		uploadFile.getContentType();
		String business_type = getPara("business_type");
		String business_id = getPara("business_id");
		// 文件附件
		try {
			if (uploadFile != null) {
				String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
				String fileName=DateUtils.getNow(DateUtils.YMDHMS);
				boolean storeFile = FTP_UploadFileUtils.storeFile(fileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
				if(storeFile){
					int dot = uploadFile.getOriginalFileName().lastIndexOf(".");
					String ext="";
					if (dot != -1) {
						ext = uploadFile.getOriginalFileName().substring(dot + 1);
					} else {
						ext = "";
					}
					String factpath=storePath+"/"+fileName+"."+ext;
					String url="http://"+ip+"/" + storePath+"/"+fileName+"."+ext;
					Integer userid = getSessionUser().getUserid();
					UploadFileService.service.save(uploadFile, factpath,url,business_type,business_id,fileName,userid);//记录上传信息
					List<CreditUploadFileModel> fileList = UploadFileService.service.getByBusIdAndBusType(business_id, business_type,this);
					renderJson(fileList);
				}else{
					renderMessage("上传失败！");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderMessage("项目出现异常，上传失败！");
		}
	}
	//文件预览
	public void fileView() {
		try {
			HttpServletResponse response = getResponse();
			boolean boo = true;
			String fileName = getPara("fileName");
			String filePath = PropKit.get("uploadPath") + "/" + fileName;
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
