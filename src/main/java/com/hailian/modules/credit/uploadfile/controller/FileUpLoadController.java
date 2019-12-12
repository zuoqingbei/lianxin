package com.hailian.modules.credit.uploadfile.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.NewBeanInstanceStrategy;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.system.file.util.FileUploadUtils;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * 文件上传维护
* @author doushuihai  
* @date 2018年8月27日上午11:36:12  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/file")
public class FileUpLoadController extends BaseProjectController {
	private static final String path = "/pages/credit/uploadfile/file_";
	//public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public static final String searver_port = Config.getStr("searver_port");//域用户密码
	public static final String ftp_store = Config.getStr("ftp_store");//域用户密码
	/**
	 * 单文件上传
	* @author doushuihai  
	* @date 2018年9月3日上午9:24:46  
	* @TODO
	 */
	public void uploadFile(){
		List<File> ftpfileList=new ArrayList<File>();
		Integer pid = getParaToInt();
		CreditUploadFileModel model = null;
		String business_id = null;
		Integer business_type = null;//检索条件-报告类型
		String markFile="";
		int failnumber=0;
		int size=0;
		String originalFileName=null;
		String ext="";
		// 文件附件
		try {
			UploadFile uploadFile = getFile("file_url");//从前台获取文件
			if(uploadFile != null){
				size=1;
				ext=FileTypeUtils.getFileType(uploadFile.getOriginalFileName());
				model = getModel(CreditUploadFileModel.class);
				business_id = model.get("business_id");
				business_type = model.getInt("business_type");//检索条件-报告类型
				if (uploadFile != null /*&& uploadFile.getFile().length()<=maxPostSize*/ && FileTypeUtils.checkType(ext)) {
					String storePath = ftp_store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
//					String now=DateUtils.getNow(DateUtils.YMDHMS);
					String now=UUID.randomUUID().toString().replaceAll("-", "");
					originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
					String FTPfileName=now+"."+ext;
					String fileName=originalFileName+now;
					String pdf_FTPfileName="";
					ftpfileList.add(uploadFile.getFile());
					File pdf=null;
					if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext) && !ext.equalsIgnoreCase("html")){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
						pdf = Office2PDF.toPdf(uploadFile);
						pdf_FTPfileName=now+"."+"pdf";
						ftpfileList.add(pdf);
					}else if(ext.equals("pdf") ||FileTypeUtils.isImg(ext) || ext.equalsIgnoreCase("html")){
						pdf_FTPfileName=FTPfileName;
					}
					boolean storeFile = FtpUploadFileUtils.storeFtpFile(now,ftpfileList,storePath,ip,port,userName,password);//上传
					if(storeFile){
						if(pdf!=null){
							pdf.delete();
						}
						String factpath=storePath+"/"+FTPfileName;
						String pdfFactpath=storePath+"/"+pdf_FTPfileName;
						String url=storePath+"/"+FTPfileName;
						String pdfUrl=storePath+"/"+pdf_FTPfileName;
						Integer userid = getSessionUser().getUserid();
						UploadFileService.service.save(pid,uploadFile, factpath,url,pdfFactpath,pdfUrl,model,fileName,userid);//记录上传信息
					}else{
						failnumber+=1;
						markFile+=uploadFile.getOriginalFileName()+"上传失败!";
					}
				}else{
					failnumber+=1;
					markFile+=uploadFile.getOriginalFileName()+"上传失败，文件不符合要求!";
				}
			}else{
				markFile+="上传文件不能为空";
				renderMessage(markFile);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failnumber+=1;
			markFile+="出现未知异常，上传失败！";
		}finally{
			int successNum=size-failnumber;
			markFile+=successNum+"个文件上传成功";
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("mark", markFile);
			List<CreditUploadFileModel> fileList = UploadFileService.service.getByBusIdAndBusType(business_id, business_type+"",this);
			map.put("fileList", fileList);
			renderJson(map);
			renderMessage(markFile);
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
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日上午9:23:16  
	* @TODO
	 */
	public void list() {
		CreditUploadFileModel attr = getModelByAttr(CreditUploadFileModel.class);
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		Page<CreditUploadFileModel> page = UploadFileService.service.getPage(getPaginator(),attr,orderBy,this);
		setAttr("attr", attr);
		setAttr("page", page);
		render(path + "list.html");
	}
	public void view() {
		CreditUploadFileModel model = CreditUploadFileModel.dao.getById(getParaToInt());
		String view_url=model.get("view_url");
		String url=model.get("url");
		model.set("view_url", "http://"+ip+":"+searver_port+"/"+view_url);
		model.set("url", "http://"+ip+":"+searver_port+"/"+url);
		setAttr("model", model);
		render(path + "view.html");
	}
	/**
	 * 新增
	* @author doushuihai  
	* @date 2018年9月3日上午9:22:55  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		CreditUploadFileModel attr = getModel(CreditUploadFileModel.class);
		setAttr("model", attr);
		// 查询下拉框
		render(path + "add.html");
	}
	/**
	 * 编辑
	* @author doushuihai  
	* @date 2018年9月3日上午9:22:18  
	* @TODO
	 */
	public void edit() {
		Integer paraToInt = getParaToInt();
		CreditUploadFileModel model = CreditUploadFileModel.dao.findById(paraToInt);
		String view_url=model.get("view_url");
		String url=model.get("url");
		model.set("view_url", "http://"+ip+":"+searver_port+"/"+view_url);
		model.set("url", "http://"+ip+":"+searver_port+"/"+url);
		setAttr("model", model);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除
	* @author doushuihai  
	* @date 2018年9月3日上午9:22:36  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		UploadFileService.service.delete(id,userid);//记录上传信息
		list();
	}
	
	/**
	 * 
	* @Description: 上传文件方法，有参数
	* @date 2018年12月14日 上午10:32:39
	* @author: lxy
	* @version V1.0
	 * @return 
	* @return
	 */
	public String upload(Integer pid,UploadFile uploadFile,String reportName,Integer userId){

		List<File> ftpfileList=new ArrayList<File>();
		CreditUploadFileModel model = new CreditUploadFileModel();
		String business_id = null;
		String markFile="";
		int failnumber=0;
		int size=0;
		String originalFileName=null;
		String ext="";
		String pathurl="";
		// 文件附件
		try {
			if(uploadFile != null){
				size=1;
				ext=FileTypeUtils.getFileType(uploadFile.getOriginalFileName());
				if (uploadFile != null /*&& uploadFile.getFile().length()<=maxPostSize */&& FileTypeUtils.checkType(ext)) {
					String storePath ="report_type/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
					originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
					   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					   String date= formatter.format(new Date());
					 String FTPfileName=reportName+"."+ext;//生成指定的名称（报告类型加时间）
					String fileName=originalFileName;//上传的文件名称
					String pdf_FTPfileName="";
					ftpfileList.add(uploadFile.getFile());
					File pdf=null;
					boolean storeFile = FtpUploadFileUtils.storeFtpFile(date,ftpfileList,storePath,ip,port,userName,password);//上传
					 pathurl=storePath+"/"+date+"."+ext;
					if(storeFile){
						if(pdf!=null){
							pdf.delete();
						}
						String factpath=storePath+"/"+FTPfileName;
						String pdfFactpath=storePath+"/"+pdf_FTPfileName;
						String url=storePath+"/"+FTPfileName;
						String pdfUrl=storePath+"/"+pdf_FTPfileName;
						UploadFileService.service.save(null,uploadFile, factpath,url,pdfFactpath,pdfUrl,model,fileName,userId);//记录上传信息
			          
					}else{
						failnumber+=1;
						markFile+=uploadFile.getOriginalFileName()+"上传失败!";
					}
					 
				}else{
					failnumber+=1;
					markFile+=uploadFile.getOriginalFileName()+"上传失败，文件不符合要求!";
				}
			}else{
				markFile+="上传文件不能为空";
				renderMessage(markFile);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failnumber+=1;
			markFile+="出现未知异常，上传失败！";
		}
		return pathurl;
	
	}
}
