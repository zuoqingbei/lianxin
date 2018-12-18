package com.hailian.modules.admin.ordermanager.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
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
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportLanguage;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.admin.site.TbSite;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
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
	public static final String searver_port = Config.getStr("searver_port");//端口号
	public static final String ftp_store = Config.getStr("ftp_store");//存储目录
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
		String num = CreditOrderInfo.dao.getNumber();
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    String year=String.valueOf(calendar.get(Calendar.YEAR));
	    String month=String.valueOf(calendar.get(Calendar.MONTH));
		CreditUploadFileModel model1= new CreditUploadFileModel();
		model1.set("business_type", "0");
		model1.set("business_id", num);
		int num1=0;
		String message="";
		int size=upFileList.size();
		int id=getParaToInt("id");
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		model.set("num", num);
		model.set("receiver_date", date);
		model.set("year", year);
		model.set("month", month);
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
		
		if(size >0){
			try {
				for(UploadFile uploadFile:upFileList){
					String originalFile=uploadFile.getOriginalFileName();
					int dot = originalFile.lastIndexOf(".");
					String ext="";
					String originalFileName=FileTypeUtils.getName(originalFile);
					ext=FileTypeUtils.getFileType(originalFile);
					
					if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
						String storePath =ftp_store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
						String now=UUID.randomUUID().toString().replaceAll("-", "");
						originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
						String FTPfileName=now+"."+ext;
						String fileName=originalFileName+now;
						String pdf_FTPfileName="";
						ftpfileList.add(uploadFile.getFile());
						if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
							File pdf = Office2PDF.toPdf(uploadFile);;
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
							String url= storePath+"/"+FTPfileName;
							Integer userid = getSessionUser().getUserid();
							model1.set("business_id", num);
							String pdfUrl=storePath+"/"+pdf_FTPfileName;
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
			cof.save();
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
		if("207".equals(countryType) || "208".equals(countryType) || "209".equals(countryType)) {
			countryType="148";
		}
		String speed=getPara("speed", "");
		String reporttype=getPara("reporttype", "");
		/*String orderType=getPara("ordertype", "");*/
		String receivedate=getPara("receivedate","");
		CreditReportUsetime usetime=OrderManagerService.service.getTime(countryType,speed,reporttype/*,orderType*/);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		if(StringUtils.isNotBlank(receivedate)) {
		ca.setTime(sdf.parse(receivedate));//设置接单时间
		}
		int time;
		int days;
		int hour;
		if(usetime==null) {
			 usetime=new CreditReportUsetime();
			 time=0;
		}else {
			time=usetime.get("use_time");
			usetime.set("use_time", (int)Math.ceil(time/24.0));
		}
		ca.setTime(new Date());
		hour = ca.get(Calendar.HOUR_OF_DAY);
		if(hour<12) {
			days=(int)Math.ceil(time/24.0);
		}else {
			days=(int)Math.ceil(time/24.0)+1;
		}
		Calendar c = 
				new DateAddUtil().addDateByWorkDay(ca,//当前时间
						//需要用多少天
						days);
		String enddate=sdf.format(c.getTime());
		if(time==0) {
			enddate="";
		}
		if("148".equals(countryType)&&!("15".equals(reporttype) || "22".equals(reporttype))) {
			ca.add(Calendar.DATE, days);
			enddate=sdf.format(ca.getTime());
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
        CreditReportPrice price = new CreditReportPrice();
        String countryType = getPara("countrytype", "");
        if ("207".equals(countryType) || "208".equals(countryType) || "209".equals(countryType)) {
            countryType = "148";
        }
        String speed = getPara("speed", "");
        String reporttype = getPara("reporttype", "");
        String orderType = getPara("ordertype", "");
        String customid = getPara("customid", "");
        String countryid = getPara("countryid", "");

        //根据客户id判断新老客户新老客户价格区分字段是versions=老系统
        CreditCustomInfo cci = CreditCustomInfo.dao.findById(customid);
        if ("0".equals(cci.getStr("is_old_customer"))) {
            //3种不同类型老客户
            if ("373".equals(customid)) {
                //获取上月的第5天
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, -1);//上月
                c.set(Calendar.DAY_OF_MONTH, 5);//设置为5号,当前日期既为上月第5天
                String first = format.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.add(Calendar.MONTH, 1);//本月
                ca.set(Calendar.DAY_OF_MONTH, 5);//本月5日
                String last = format.format(ca.getTime());
                //此客户依据每月订单量和国家决定订单价格
                //求该客户的该月订单量
                List<CreditOrderInfo> list = CreditOrderInfo.dao.findByCustom(customid, first, last);
                Integer size = list.size();
                price = CreditReportPrice.dao.getoldPrice(countryid, null, null, size);

            } else if ("399".equals(customid)) {
                //此客户依据国家决定订单价格
                price = CreditReportPrice.dao.getoldPrice(countryid, null, null, null);
            } else {
                //此类客户依据国家,报告类型,和速度决定订单价格
                price = CreditReportPrice.dao.getoldPrice(countryid, reporttype, speed, null);
            }
        } else {
            price = OrderManagerService.service.getPrice(countryType, speed, reporttype, orderType);
        }
		/*
		 * 为防止测试时拦截,上线后删掉
		 */
		/*CreditReportPrice test=new CreditReportPrice();
		test.set("id", 99);
		renderJson(test);*/
		/*
		 * 正式
		 */
        renderJson(price);
    }

	/**
	 * 查找以往是否有该订单公司的已完成订单
	* @author doushuihai  
	 * @throws UnsupportedEncodingException 
	* @date 2018年11月18日下午5:55:29  
	* @TODO
	 */
	public void isTheSameCompany() throws UnsupportedEncodingException{
		String companyname=getPara("companyname");
		String report_type=getPara("report_type");
		String report_language=getPara("report_language");
		companyname = URLDecoder.decode(companyname,"UTF-8");
		//判断该公司是否存在于公司库中
		CreditCompanyInfo company=CreditCompanyInfo.dao.findByENname(companyname);
		CreditOrderInfo theSameOrder=null;
		if(company!=null){
			theSameOrder = OrderManagerService.service.isTheSameOrder(companyname,report_type,report_language, this);
		}
		if(theSameOrder!=null){
			Map<String,Object> map=new HashMap<String, Object>();
			ResultType resultType = new ResultType(1,"检测到相同公司名称");
			map.put("flag", resultType);
			map.put("result", theSameOrder);
			renderJson(map);
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			ResultType resultType = new ResultType(2,"未检测到相同公司名称");
			map.put("flag", resultType);
			map.put("result", theSameOrder);
			renderJson(map);
		}
		
		
	}
	/**
	 * 
	 * @time   2018年9月4日 下午2:13:00
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getLanguage",httpMethod="get", 
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
	
	public void getCustom() {
		String id=getPara("id");
		CreditCustomInfo cci=CreditCustomInfo.dao.findById(id);
		renderJson(cci);
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
