
package com.hailian.modules.credit.usercenter.controller;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditOrderHistory;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.orderflowconf.model.CreditOrderFlowConf;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.usercenter.service.HomeService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.system.menu.SysMenu;
import com.hailian.system.user.SysUser;
import com.hailian.system.user.UserSvc;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * 
 * @className HomeController.java
 * @time   2018年9月5日 下午2:00:19
 * @author yangdong
 * @todo   用户控制台
 */


@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/home")
public class HomeController extends BaseProjectController {
	private static final String path = "/pages/credit/usercenter/";
	public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public void index() {
		render(path+"index.html");
		
	}
	public void allOrder() {
		SysUser user= (SysUser) getSessionUser();
		if(user==null||"".equals(user)) {
			redirect("/credit/front/usercenter/login");
		}else {
			render(path+"all_orders.html");
		}
	}
	public void menu() {
		SysUser user= (SysUser) getSessionUser();
		Map<Integer, List<SysMenu>> map = new UserSvc().getQTMap(user);
		if(user==null||"".equals(user) ) {
			redirect("/credit/front/usercenter/login");
		}
		else if(map==null){
			renderMessageByFailed("请分配角色");
		}else {
		setAttr("user",user);
		setAttr("menu", map);
		render("/pages/credit/common/menu.html");
		}
	}
	/**
	 * 
	 * @time   2018年9月17日 上午11:54:31
	 * @author yangdong
	 * @todo   TODO 获取详情页
	 * @param  
	 * @return_type   void
	 */
	public void orderInfo() {
		//获取订单id
		int id=getParaToInt("id");
		//根据订单id获取订单信息
		CreditOrderInfo order=OrderManagerService.service.editOrder(id,this);
		int time=order.get("useTime");
		int useTime=(int)Math.ceil(time/24.0);
		order.put("useTime", useTime);
		//根据id获取num
		String num=order.getStr("num");
		//获取附件
		List<CreditUploadFileModel> files=CreditUploadFileModel.dao.getFile(num);
		for(CreditUploadFileModel file:files) {
			String view_url=file.get("view_url");
			String url=file.get("url");
			file.set("view_url", "http://"+ip+"/"+view_url);
			file.set("url", "http://"+ip+"/"+url);	
		}
		/*if(files.size()==0) {
			files=null;
		}*/
		//根据订单信息获取公司信息
		Integer companyid=order.getInt("company_id");
		if(companyid==null) {
			renderMessage("未获取该公司id");
			return;
		}
		CreditCompanyInfo company=OrderManagerService.service.getCompany(companyid);
		//根据订单id获取历史记录信息
		List<CreditOrderHistory> histroy=HomeService.service.getHistroy(String.valueOf(id));
		//获取客户信息
		CreditCustomInfo custom=OrderManagerService.service.getCreater(order.getStr("custom_id"));
		//获取地区
//		SysDictDetail continent=HomeService.service.getContinent(order.getStr("continent"));
		//获取订单记录表
		List<CreditOrderFlow> cof=CreditOrderFlow.dao.findByNum(order.getStr("num"));
		int size=cof.size();
		//获取国家类型
		CountryModel country=CountryModel.dao.findById(order.getStr("country"));
		String countryType=country.getStr("type");
		//根据国家类型获取流程列表
		List<CreditOrderFlowConf> cofc=CreditOrderFlowConf.dao.findByType(countryType);
		//绑定订单信息和公司信息
		setAttr("order",order);
		setAttr("company",company);
		setAttr("histroy",histroy);
		setAttr("custom",custom);
		setAttr("files",files);
		setAttr("cof",cof);
		setAttr("cofc",cofc);
		setAttr("size",size);
		//转发页面
		render(path+"order_manage/order_detail.html");
	}
	/**
	 * 
	 * @time   2018年9月7日 下午3:54:46
	 * @author yangdong
	 * @todo   TODO7
	 * @param  
	 * @throws ParseException 
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/list",httpMethod="post", 
			description = "查看订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = false, dataType = "String"),
		})
	public void list(){
		CreditOrderInfo model = new CreditOrderInfo();
		if(StringUtils.isNotBlank(getPara("custom_id"))) {
			model.set("custom_id", getPara("custom_id"));
		}
		if(StringUtils.isNotBlank(getPara("country"))) {
			model.set("country", getPara("country"));
		}
		if(StringUtils.isNotBlank(getPara("end_date"))) {
			model.set("end_date", getPara("end_date"));
		}
		if(StringUtils.isNotBlank(getPara("agent_id"))) {
			model.set("agent_id", getPara("agent_id"));
		}
		if(StringUtils.isNotBlank(getPara("company_by_report"))) {
			model.set("company_by_report", getPara("company_by_report"));
		}
		if(StringUtils.isNotBlank(getPara("right_company_name_en"))) {
			model.set("right_company_name_en", getPara("right_company_name_en"));
		}
		String sortname=getPara("sortName");
		if(!StringUtils.isNotBlank(sortname)) {
			sortname="receiver_date";
		}
		String sortorder=getPara("sortOrder");
		if(!StringUtils.isNotBlank(sortorder)) {
			sortorder="desc";
		}
		SysUser user= (SysUser) getSessionUser();
		String status =getPara("status");
		if(StringUtils.isNotBlank(status)) {
			status=status.substring(0, status.length()-1);
		}
		Paginator pageinator=getPaginator();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,status, user,sortname,sortorder);
//		List<CreditOrderInfo> result=OrderManagerService.service.getOrdersService(status,model, user);
		int total= page.getTotalRow();
		List<CreditOrderInfo> rows=page.getList();
		ResultType resultType=new ResultType(total,rows);
		renderJson(resultType);
	}
	/**
	 * 
	 * @time   2018年9月10日 上午9:56:03
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/getMessage",httpMethod="post", 
			description = "获取信息")
	public void getMessage() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String status =getPara("status");
		if(StringUtils.isNotBlank(status)) {
			status=status.substring(0, status.length()-1);
		}
		SysUser user= SysUser.dao.getUser(this);
		List<CountryModel> country=OrderManagerService.service.getCountrys("");
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CreditCompanyInfo> companys=OrderManagerService.service.getCompany();
		//订单核实数量
		int orderhs=OrderManagerService.service.getOrdersService("292",null,user,null);
		//订单查档数量
		int ordercd=OrderManagerService.service.getOrdersService("294",null,user,null);
		//订单信息质检数量
		int orderzj1=OrderManagerService.service.getOrdersService("298",null,user,null);
		//分析质检
		int orderzj2=OrderManagerService.service.getOrdersService("303",null,user,null);
		//翻译质检
		int orderzj3=OrderManagerService.service.getOrdersService("308",null,user,null);
		Record record=new Record();
		record.set("user", user);
		record.set("country", country);
		record.set("customs", customs);
		record.set("companys", companys);
		record.set("orderhs", orderhs);
		record.set("ordercd", ordercd);
		record.set("orderzj", orderzj1+orderzj2+orderzj3);
		renderJson(record);
	}
	@ApiOperation(url = "/credit/front/home/getCountry",httpMethod="post", 
			description = "获取信息")
	@Params(value = { 
			@Param(name = "id", description = "地区id", required = true, dataType = "String"),
			})
	public void getCountry() {
		String contient=getPara("contient");
		List<CountryModel> country=OrderManagerService.service.getCountrys(contient);
		renderJson(country);
	}
	/**
	 * 
	 * @time   2018年9月5日 下午5:25:42
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/view",httpMethod="post", 
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
	 * 创建订单
	 * @time   2018年9月11日 上午10:18:10
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	public void createOrder() {
		CreditOrderInfo model=new CreditOrderInfo();
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("company",company);
		setAttr("model", model);
		render(path + "/order_manage/create_order.html");
	}
	/**
	 * 
	 * @time   2018年9月12日 下午5:33:20
	 * @author yangdong
	 * @todo   TODO 保存订单
	 * @param  
	 * @throws Exception 
	 * @return_type   void
	 */
	public void saveOrder() throws Exception {
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		List<File> ftpfileList=new ArrayList<File>();
		String num =CreditOrderInfo.dao.getNumber();
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    String year=String.valueOf(calendar.get(Calendar.YEAR));
	    String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String countryId = model.get("country");
		if(countryId!=null&&!"".equals(countryId)){
			Db.update("update credit_country set `scale`=`scale`+1 where id="+countryId);
		}
		
		model.set("num", num);
		model.set("receiver_date", date);
		model.set("year", year);
		model.set("month", month);
		//获取订单公司名称
		String right_company_name_en=model.get("right_company_name_en");
		//判断该公司是否存在于公司库中
		CreditCompanyInfo company=CreditCompanyInfo.dao.findByENname(right_company_name_en);
		//如果公司不存在则在数据库中增加该公司的记录
		if(company==null) {
			company=new CreditCompanyInfo();
			company.set("name_en", right_company_name_en);
			company.save();
			//获取新增公司
			company=CreditCompanyInfo.dao.findByENname(right_company_name_en);
		}
		//获取公司id
		model.set("company_id", company.get("id"));
		String reportIdtoOrder = OrderManagerService.service.getReportIdtoOrder();
		model.set("report_user", reportIdtoOrder);
		//获取订单记录
		CreditOrderFlow cof=new CreditOrderFlow();
		//订单号
		cof.set("order_num", num);
		//订单状态
		cof.set("order_state", model.get("status"));
		//操作人
		cof.set("create_oper", model.get("create_by"));
		//操作时间
		cof.set("create_time", model.get("receiver_date"));
		
		SysUser user = (SysUser) getSessionUser();
		CreditUploadFileModel model1= new CreditUploadFileModel();
		model1.set("business_type", "0");
		model1.set("business_id", num);
		int num1=0;
		String message="";
		int size=upFileList.size();
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
							File pdf = Office2PDF.toPdf(uploadFile);
							pdf_FTPfileName=now+"."+"pdf";
							ftpfileList.add(pdf);
						}else if(ext.equals("pdf") ||FileTypeUtils.isImg(ext)){
							pdf_FTPfileName=FTPfileName;
						}
						//String now,List<File> filelist,String storePath,String url,int port,String userName,String password
						boolean storeFile = FtpUploadFileUtils.storeFtpFile(now, ftpfileList,storePath,ip,port,userName,password);//上传
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
							ResultType resultType=new ResultType(0,message);
							renderJson(resultType);
							return;
						}
					}else{
						num1+=1;
						message+=uploadFile.getOriginalFileName()+"上传失败!";
						ResultType resultType=new ResultType(0,message);
						renderJson(resultType);						
						return;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				ResultType resultType=new ResultType(0,"文件上传失败,请重新提交");
				renderJson(resultType);
				return;
			}
		}
		try {
			Date date1=new Date();
			model.set("create_date", date1);
			OrderManagerService.service.modifyOrder(0,model,user,this);
			cof.save();
			ResultType resultType=new ResultType(1,"操作成功");
			renderJson(resultType);
		} catch (Exception e) {
			e.printStackTrace();
			ResultType resultType=new ResultType(0,"订单保存失败,请重新提交");
			renderJson(resultType);
			return;
		}
	}
	/**
	 * 
	 * @time   2018年10月9日 下午4:24:03
	 * @author yangdong
	 * @todo   TODO 订单撤销
	 * @param  
	 * @return_type   void
	 */
	public void cheXiao() {
		String id=getPara("id");
		String revoke_reason=getPara("revoke_reason");
		try {
		//根据id查找订单
		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id);
		//更新订单
		coi.set("status","313");
		coi.set("revoke_reason", revoke_reason);
		//保存订单
		coi.update();
		ResultType resultType=new ResultType(1,"操作成功");
		renderJson(resultType);
		}catch(Exception e) {
			e.printStackTrace();
			ResultType resultType=new ResultType(0,"操作失败,请重新提交");
			renderJson(resultType);
		}
	}
	/**
	 * 
	 * @time   2018年10月10日 上午10:54:29
	 * @author yangdong
	 * @todo   TODO 内容更新
	 * @param  
	 * @return_type   void
	 */
	public void update() {
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		List<File> ftpfileList=new ArrayList<File>();
		String id=getPara("orderid1");
		String update_reason=getPara("update_reason");
		//更新订单信息
		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id);
		coi.set("update_reason", update_reason);
		coi.set("status", "312");
		//获取订单编号
		String num=coi.get("num");
		//保存上传文件
		CreditUploadFileModel model= new CreditUploadFileModel();
		model.set("business_type", "0");
		model.set("business_id", num);
		//记录失败次数
		int num1=0;
		//记录信息
		String message="";
		//上传文件个数
		int size=upFileList.size();
		//循环上传文件
		if(size>0) {
		try {
		for(UploadFile uploadFile:upFileList){
			//获取文件全名
			String originalFile=uploadFile.getOriginalFileName();
			//获取去除后缀名的文件名长度
			int dot = originalFile.lastIndexOf(".");
			//后缀名
			String ext="";
			//获取去除后缀名后的文件名
			String originalFileName=FileTypeUtils.getName(originalFile);
			//后缀名,文件类型
			ext=FileTypeUtils.getFileType(originalFile);
			
			if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
				
				String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
				//为文件取新名字(汉字名字预览出问题)
				String now=UUID.randomUUID().toString().replaceAll("-", "");
				//获取文件名(取出后缀)
				originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
				//文件储存时的名字
				String FTPfileName=now+"."+ext;
				//数据库记录文件名字
				String fileName=originalFileName+now;
				String pdf_FTPfileName="";
				File pdf=null;
				ftpfileList.add(uploadFile.getFile());
				if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
					 pdf = Office2PDF.toPdf(uploadFile);
					pdf_FTPfileName=now+"."+"pdf";
					ftpfileList.add(pdf);
				}else if(ext.equals("pdf") ||FileTypeUtils.isImg(ext)){
					pdf_FTPfileName=FTPfileName;
				}
				//String now,List<File> filelist,String storePath,String url,int port,String userName,String password
				boolean storeFile = FtpUploadFileUtils.storeFtpFile(now, ftpfileList,storePath,ip,port,userName,password);//上传
				
				if(storeFile){
					if(pdf!=null) {
						pdf.delete();
					}
					//如果文件上传成功则添加数据库记录
					String factpath=storePath+"/"+FTPfileName;
					String pdfFactpath=storePath+"/"+pdf_FTPfileName;
					String url= storePath+"/"+FTPfileName;
					Integer userid = getSessionUser().getUserid();
					model.set("business_id", num);
					String pdfUrl=storePath+"/"+pdf_FTPfileName;
					UploadFileService.service.save(0,uploadFile, factpath,url,pdfFactpath,pdfUrl,model,fileName,userid);//记录上传信息
				}else{
					num1+=1;
					message+=uploadFile.getOriginalFileName()+"上传失败!";
					ResultType resultType=new ResultType(0,message);
					renderJson(resultType);
					return;
				}
			}else{
				num1+=1;
				message+=uploadFile.getOriginalFileName()+"上传失败!";
				ResultType resultType=new ResultType(0,message);
				renderJson(resultType);						
				return;
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			ResultType resultType=new ResultType(0,"操作失败,请重新提交");
			renderJson(resultType);
			return;
		}
		
	}
		try {
			coi.update();
			ResultType resultType=new ResultType(1,"操作成功");
			renderJson(resultType);						
			return;
		}catch(Exception e) {
			e.printStackTrace();
			ResultType resultType=new ResultType(0,"操作失败,请重新提交");
			renderJson(resultType);
			return;
		}
		
	}
	
	/**
	 * 
	 * @time   2018年9月17日 上午9:58:23
	 * @author yangdong
	 * @todo   TODO  根据客户id获取客户姓名
	 * @param  
	 * @return_type   void
	 */
	public void getCustomName() {
		String id=getPara("id");
		CreditCustomInfo custom=OrderManagerService.service.getCreater(id);
		renderJson(custom);
	}
	

}

