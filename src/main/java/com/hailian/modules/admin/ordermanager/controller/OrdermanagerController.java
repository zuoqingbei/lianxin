package com.hailian.modules.admin.ordermanager.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportLanguage;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.file.util.FileUploadUtils;
import com.hailian.system.user.SysUser;
import com.hailian.util.Config;
import com.hailian.util.DateAddUtil;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
/**
 * 
 * @className OrdermanagerController.java
 * @time   2018年8月31日 下午3:41:17
 * @author yangdong
 * @todo   TODO
 * 订单管理
 */
@Api(tag = "订单菜单路由", description = "订单菜单")
@ControllerBind(controllerKey = "/admin/ordermanager")
public class OrdermanagerController extends BaseProjectController{
	private static final String path = "/pages/admin/ordermanager/order_";
	public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:22
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager",httpMethod="post", 
			description = "获取订单列表")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单信息", required = true, dataType = "Model"),
		})
	public void index() {
		list();
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:36
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/list",httpMethod="post", 
			description = "获取订单列表")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单信息", required = true, dataType = "Model"),
		})
	public void list() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		SysUser user = (SysUser) getSessionUser();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy, this);	
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:02
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/delete",httpMethod="post", 
			description = "删除订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void delete() {
	
		try {
			int id=getParaToInt("id");
			CreditOrderInfo coi=CreditOrderInfo.dao;
			coi.set("id",id);
			coi.set("del_flag", "1");
			OrderManagerService.service.deleteOrder(coi, this);
			renderMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			renderMessageByFailed("删除失败");
		}
		
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:54
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 转到订单修改页面
	 */
	@ApiOperation(url = "/admin/ordermanager/edit",httpMethod="get", 
			description = "定位修改订单页面")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void edit() {
		int id=getParaToInt("id");
		CreditOrderInfo coi=OrderManagerService.service.editOrder(id,this);
		String continent=coi.get("continent").toString();
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CountryModel> country=OrderManagerService.service.getCountrys(continent);
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("model", coi);
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("country",country);
		setAttr("company",company);
		render(path + "edit.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:44
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 修改订单
	 */
	@ApiOperation(url = "/admin/ordermanager/save",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单", required = false, dataType = "Model"),
		})
	@Before(Tx.class)
	public void save() {
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		List<File> ftpfileList=new ArrayList<File>();
		CreditUploadFileModel model1= new CreditUploadFileModel();
		String num=CreditOrderInfo.dao.getNumber();
		model1.set("business_type", "0");
		model1.set("business_id", num);
		int num1=0;
		String message="";
		int size=upFileList.size();
		int id=getParaToInt("id");
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		SysUser user = (SysUser) getSessionUser();
		model.set("num", num);
		if(size >0){
			try {
				for(UploadFile uploadFile:upFileList){
					String originalFile=uploadFile.getOriginalFileName();
					int dot = originalFile.lastIndexOf(".");
					String ext="";
					String originalFileName=FileTypeUtils.getName(originalFile);
					ext=FileTypeUtils.getFileType(originalFile);
					
					if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
						String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
						String now=UUID.randomUUID().toString().replaceAll("-", "");
						originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
						String FTPfileName=now+"."+ext;
						String fileName=originalFileName+now;
						String pdf_FTPfileName="";
						ftpfileList.add(uploadFile.getFile());
						if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
							File pdf = toPdf(uploadFile);
							pdf_FTPfileName=now+"."+"pdf";
							ftpfileList.add(pdf);
						}else if(ext.equals("pdf") ||FileTypeUtils.isImg(ext)){
							pdf_FTPfileName=FTPfileName;
						}
						//String now,List<File> filelist,String storePath,String url,int port,String userName,String password
						boolean storeFile = FtpUploadFileUtils.storeFtpFile(FTPfileName, ftpfileList,storePath,ip,port,userName,password);//上传
						if(storeFile){
							String factpath=storePath+"/"+FTPfileName;
							String pdfFactpath=storePath+"/"+pdf_FTPfileName;
							String url="http://"+ip+"/" + storePath+"/"+FTPfileName;
							Integer userid = getSessionUser().getUserid();
							model1.set("business_id", num);
							String pdfUrl="http://"+ip+"/" + storePath+"/"+pdf_FTPfileName;
							UploadFileService.service.save(0,uploadFile, factpath,url,pdfFactpath,pdfUrl,model1,fileName,userid);//记录上传信息
						}else{
							num1+=1;
							message+=uploadFile.getOriginalFileName()+"上传失败!";
							renderMessageByFailed(message);
//							redirect("/credit/front/home/menu");
							
							return;
						}
					}else{
						num1+=1;
						message+=uploadFile.getOriginalFileName()+"上传失败!";
						renderMessageByFailed(message);
//						redirect("/credit/front/home/menu");						
						return;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				renderMessageByFailed("文件上传失败");
//				redirect("/credit/front/home/menu");
				return;
			}
		}
		try {
			OrderManagerService.service.modifyOrder(id,model,user,this);
			OrderManagerService.service.addOrderHistory(id, user);
			renderMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			renderMessageByFailed(message);
//			redirect("/credit/front/home/menu");
			
		}	
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:48
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 根据传入id的不同来区分修改添加
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
			description = "查看订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void view() {
		int id=getParaToInt("id");
		CreditOrderInfo model=OrderManagerService.service.editOrder(id,this);
		setAttr("model", model);
		render(path + "view.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:56
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 转到添加页面
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
			description = "增加订单")
	public void add() {
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		//默认国内
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("company",company);
		setAttr("model",new CreditOrderInfo());
		render(path + "add.html");
	}
	/**
	 * 
	 * @time   2018年8月31日 下午3:28:55
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 获取速度
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
	description = "查看订单")
	@Params(value = { 
	@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
	})
	public void getSpeed() {
		String type=getPara("countrytype").toString();
		List<SysDictDetail> speed=null;
		speed=OrderManagerService.service.getDictByType("orderspeed");
		renderJson(speed);
	}
	/**
	 * 
	 * @time   2018年9月3日 下午4:27:56
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @throws ParseException 
	 * @return_type   void
	 * 获取订单时间
	 */
	@ApiOperation(url = "/admin/ordermanager/getTime",httpMethod="get", 
			description = "返回所用时间")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "speed", description = "速度", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getTime() throws ParseException {
		String countryType=getPara("countrytype", "");
		String speed=getPara("speed", "");
		String reporttype=getPara("reporttype", "");
		String orderType=getPara("ordertype", "");
		String receivedate=getPara("receivedate","");
		CreditReportUsetime usetime=OrderManagerService.service.getTime(countryType,speed,reporttype,orderType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		if(StringUtils.isNotBlank(receivedate)) {
		ca.setTime(sdf.parse(receivedate));//设置接单时间
		}
		int time;
		if(usetime==null) {
			 usetime=new CreditReportUsetime();
			 time=0;
		}else {
			time=usetime.get("use_time");
		}
		Calendar c = 
				new DateAddUtil().addDateByWorkDay(ca,//当前时间
						//需要用多少天
						(int)Math.ceil(time/24.0));
		String enddate=sdf.format(c.getTime());
		if(time==0) {
			enddate="";
		}
		Record record=new Record();
		record.set("usetime", usetime);
		record.set("enddate", enddate);
		renderJson(record);

	}
	/**
	 * 
	 * @time   2018年9月3日 下午7:26:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回价格")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "speed", description = "速度", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getPrice() {
		String countryType=getPara("countrytype", "");
		String speed=getPara("speed", "");
		String reporttype=getPara("reporttype", "");
		String orderType=getPara("orderType", "");
		CreditReportPrice price=OrderManagerService.service.getPrice(countryType,speed,reporttype,orderType);
		renderJson(price);

	}
	/**
	 * 
	 * @time   2018年9月4日 下午2:13:00
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回语言")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getLanguage() {
		String countryType=getPara("countrytype", "");
		String reporttype=getPara("reporttype", "");
		List<CreditReportLanguage> language=OrderManagerService.service.getLanguage(countryType,reporttype);
		renderJson(language);
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:08:44
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回公司")
	public void getCompany() {
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		renderJson(company);
	}
	
	public File toPdf(UploadFile uploadFile) throws Exception{
		TbSite site = getBackSite();
		String projectStorePath = FileUploadUtils.getUploadPath(site, "view");
		String now=DateUtils.getNow(DateUtils.YMDHMS);
		String type = FileTypeUtils.getFileType(uploadFile.getFileName());
		String name = FileTypeUtils.getName(uploadFile.getFileName());
		File convertFileToPdf = Office2PDF.convertFileToPdf(uploadFile.getFile(),name,type, projectStorePath);
		return convertFileToPdf;
	}
	
	public String test(){
		 String reportIdtoOrder = OrderManagerService.service.getReportIdtoOrder();
		 return reportIdtoOrder;
	}
}
