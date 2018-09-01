package com.hailian.modules.credit.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.StrUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * 文件上传
* @author doushuihai  
* @date 2018年8月27日上午11:36:12  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/file")
public class FileUpLoadController extends BaseProjectController {
	private static final String path = "/pages/credit/uploadfile/file_";
	public static final int maxPostSize=Config.getToInt("FTP_maxPostSize");
	public static final String ip = Config.getStr("FTP_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("FTP_port");//ftp端口 默认21
	public static final String userName = Config.getStr("FTP_userName");//域用户名
	public static final String password = Config.getStr("FTP_password");//域用户密码
	public void uploadFile(){
		Integer pid = getParaToInt();
		String business_type = getPara("business_type");
		String business_id = getPara("business_id");
		String markFile="";
		int failnumber=0;
		int size=0;
		// 文件附件
		try {
			UploadFile uploadFile = getFile("file_url");//从前台获取文件
			if(uploadFile != null){
				size=1;
			}
			int dot = uploadFile.getOriginalFileName().lastIndexOf(".");
			String ext="";
			String originalFileName=uploadFile.getOriginalFileName();
			if (dot != -1) {
				originalFileName=originalFileName.substring(0, dot);
				ext = uploadFile.getOriginalFileName().substring(dot + 1);
			} else {
				ext = "";
			}
			if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
				String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
				String now=DateUtils.getNow(DateUtils.YMDHMS);
				String FTPfileName=originalFileName+now+"."+ext;
				String fileName=originalFileName+now;
				boolean storeFile = FtpUploadFileUtils.storeFile(FTPfileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
				if(storeFile){
					String factpath=storePath+"/"+FTPfileName;
					String url="http://"+ip+"/" + storePath+"/"+FTPfileName;
					Integer userid = getSessionUser().getUserid();
					UploadFileService.service.save(uploadFile, factpath,url,business_type,business_id,fileName,userid);//记录上传信息
				}else{
					failnumber+=1;
					markFile+=uploadFile.getOriginalFileName()+"上传失败!";
				}
			}else{
				failnumber+=1;
				markFile+=uploadFile.getOriginalFileName()+"上传失败，文件不符合要求!";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failnumber+=1;
			markFile+="项目出现异常，上传失败！";
		}finally{
			int successNum=size-failnumber;
			markFile+=successNum+"个文件上传成功";
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("mark", markFile);
			List<CreditUploadFileModel> fileList = UploadFileService.service.getByBusIdAndBusType(business_id, business_type,this);
			map.put("fileList", fileList);
			renderJson(map);
			renderMessage(markFile);
		}
	}
	public void uploadMultipleFile(){
		String markFile="";
		int failnumber=0;
		int size=0;
		String business_type = getPara("business_type");
		String business_id = getPara("business_id");
		// 文件附件
		try {
			List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
			size=upFileList.size();
			for(UploadFile uploadFile:upFileList){
				String originalFileName=uploadFile.getOriginalFileName();
				int dot = originalFileName.lastIndexOf(".");
				String ext="";
				if (dot != -1) {
					originalFileName=originalFileName.substring(0, dot);
					ext = originalFileName.substring(dot + 1);
				} else {
					ext = "";
				}
				if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
					String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
					String now=DateUtils.getNow(DateUtils.YMDHMS);
					String FTPfileName=originalFileName+now+"."+ext;
					String fileName=originalFileName+now;
					boolean storeFile = FtpUploadFileUtils.storeFile(FTPfileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
					if(storeFile){
						String factpath=storePath+"/"+FTPfileName;
						String url="http://"+ip+"/" + storePath+"/"+FTPfileName;
						Integer userid = getSessionUser().getUserid();
						UploadFileService.service.save(uploadFile, factpath,url,business_type,business_id,fileName,userid);//记录上传信息
						
					}else{
						failnumber+=1;
						markFile+=uploadFile.getOriginalFileName()+"上传失败!";
					}
				}else{
					failnumber+=1;
					markFile+=uploadFile.getOriginalFileName()+"上传失败!";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			markFile+="项目出现异常，上传失败！";
		}finally{
			int successNum=size-failnumber;
			markFile+=successNum+"个文件上传成功";
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("mark", markFile);
			List<CreditUploadFileModel> fileList = UploadFileService.service.getByBusIdAndBusType(business_id, business_type,this);
			map.put("fileList", fileList);
			renderJson(map);
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
	
	public void index() {
		list();
	}
	public void list() {
		CreditUploadFileModel attr = getModelByAttr(CreditUploadFileModel.class);
		StringBuffer sql = new StringBuffer(" from credit_upload_file where del_flag=0");
		String type = attr.getStr("ext");//检索条件-文件类型
//		String business_type = attr.getStr("business_type");//检索条件-报告类型
		Integer business_type = attr.getInt("business_type");//检索条件-报告类型
//		String business_type="";
		String originalname = attr.getStr("originalname");//检索条件-上传文件名
		if (StrUtils.isNotEmpty(type)) {
			sql.append(" and ext = '").append(type).append("'");
		}
		if (business_type !=null && business_type>=0) {
			sql.append(" and business_type = ").append(business_type);
		}
		if (StrUtils.isNotEmpty(originalname)) {
			sql.append(" and originalname = '").append(originalname).append("'");
		}
		Page<CreditUploadFileModel> page = CreditUploadFileModel.dao
				.paginate(getPaginator(), "select *  ", sql.toString());
		
		// 下拉框
//		setAttr("selectAlbum", svc.selectDictType(attr.getStr("dict_type")));
		setAttr("attr", attr);
		setAttr("page", page);
		render(path + "list.html");
	}
	public void view() {
		CreditUploadFileModel model = CreditUploadFileModel.dao.findById(getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}
	public void add() {
		// 获取页面信息,设置目录传入
		CreditUploadFileModel attr = getModel(CreditUploadFileModel.class);
		setAttr("model", attr);
		// 查询下拉框
		render(path + "add.html");
	}
	public void edit() {
		CreditUploadFileModel model = CreditUploadFileModel.dao.findById(getParaToInt());
		setAttr("model", model);
		// 查询下拉框
		render(path + "edit.html");
	}
	

}
