package com.hailian.modules.credit.usercenter.controller;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInvestment;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholderDetail;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentCategoryModel;
import com.hailian.modules.credit.agentmanager.service.TemplateAgentService;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.modules.front.template.TemplateSysUserService;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
* @time   2018年9月14日 上午11:00:00
* @author lzg
* todo 订单再分配
*/
@Api(tag = "订单流程", description = "订单流程")
@ControllerBind(controllerKey = "/credit/front/orderProcess")
public class OrderProcessController extends BaseProjectController{
	//文件服务器配置
	public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	//订单管理菜单下公共路径
	private static final String ORDER_MANAGE_PATH = "/pages/credit/usercenter/order_manage/";
	//报告管理下菜单公共路径
	private static final String REPORT_MANAGE_PATH = "/pages/credit/usercenter/report_pages/";
	//正在开发页面路径
	private static final String DEVELOPING_PATH = "/pages/credit/usercenter/developing.html";
	//每种搜索类型需要对应的sql关键词字段名
	public static final Map<String,List<Object>> TYPE_KEY_COLUMN = new HashMap<>();
	//每种搜索类型需要对应的前端属性名
	public static final Map<String,List<Object>> WEB_PARAM_NAMES = new HashMap<>();
	/**
	 * 订单分配的搜索类型
	 */
	public static final String orderAllocation = "-1";
	public static LinkedList<Object> orderAllocationColumns = new LinkedList<>();//存储模糊搜索时后台sql对应字段
	public static LinkedList<Object> orderAllocationParamNames = new LinkedList<>();//存储模糊搜索时前台对应参数名
	/**
	 * 订单管理中的订单核实
	 */
	public static final String orderVerifyOfOrder = "-3";
	public static LinkedList<Object> orderVerifyOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderVerifyOfOrderParamNames = new LinkedList<>();
	/**
	 * 订单管理中的订单查档(国外)
	 */
	public static final String orderFilingOfOrder = "-4";
	public static LinkedList<Object> orderFilingOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderFilingOfOrderParamNames = new LinkedList<>();
	/**
	 * 订单管理中的递交订单
	 */
	public static final String orderSubmitOfOrder = "-5";
	public static LinkedList<Object> orderSubmitOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderSubmitOfOrderParamNames = new LinkedList<>();
	/**
	 * 报告管理中的订单核实
	 */
	public static final String orderVerifyOfReport = "-2";
	public static LinkedList<Object> orderVerifyOfReportColumns = new LinkedList<>(); 
	public static LinkedList<Object> orderVerifyOfReportParamNames = new LinkedList<>(); 
	/**
	 * 报告管理中的信息录入
	 */
	public static final String infoOfReport = "-6";
	public static LinkedList<Object> infoOfReportColumns = new LinkedList<>();
	public static LinkedList<Object>infoOfReportParamNames = new LinkedList<>();
	/**
	 * 报告管理中的订单查档(国内)
	 */
	public static final String orderFilingOfReport = "-7";
	public static LinkedList<Object> orderFilingOfReportColumns = new LinkedList<>();
	public static LinkedList<Object> orderFilingOfReportParamNames = new LinkedList<>();
	static{
		orderAllocationColumns.add("u1.realname");
	}
	static{
		orderAllocationParamNames.add("report_user");
	}
	static{
		TYPE_KEY_COLUMN.put(orderAllocation,orderAllocationColumns);
		TYPE_KEY_COLUMN.put(orderVerifyOfReport,orderVerifyOfReportColumns);
		TYPE_KEY_COLUMN.put(orderVerifyOfOrder,orderVerifyOfOrderColumns);
		TYPE_KEY_COLUMN.put(orderFilingOfOrder,orderFilingOfOrderColumns);
		TYPE_KEY_COLUMN.put(orderSubmitOfOrder,orderSubmitOfOrderColumns);
		TYPE_KEY_COLUMN.put(infoOfReport,infoOfReportColumns);
		TYPE_KEY_COLUMN.put(orderFilingOfReport,orderFilingOfReportColumns);
	}
	static{
		WEB_PARAM_NAMES.put(orderAllocation, orderAllocationParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfReport, orderVerifyOfReportParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfOrder, orderVerifyOfOrderParamNames);
		WEB_PARAM_NAMES.put(orderFilingOfOrder, orderFilingOfOrderParamNames);
		WEB_PARAM_NAMES.put(orderSubmitOfOrder, orderSubmitOfOrderParamNames);
		WEB_PARAM_NAMES.put(infoOfReport, infoOfReportParamNames);
		WEB_PARAM_NAMES.put(orderFilingOfReport, orderFilingOfReportParamNames);
	}
	/**
	 * @todo   展示订单分配页
	 * @time   2018年9月14日 下午 14:38
	 * @author lzg
	 * @return_type   void
	 */
	public void showReallocation(){
		render(ORDER_MANAGE_PATH+"order_allocation.html");
	}
	/**
	 * @todo   展示订单管理下的订单核实页
	 * @time   2018年9月12日 下午 13:30
	 * @author lzg
	 * @return_type   void
	 */
	public void showOrderVerifyOfOrders(){
		render(ORDER_MANAGE_PATH+"order_verify.html");
	}
	/**
	 * @todo   展示订单查档页
	 * @time   2018年9月26日 下午 13:30
	 * @author lzg
	 * @return_type   void
	 */
	public void showOrderFiling(){
		render(ORDER_MANAGE_PATH+"order_filing.html");
	}
	/**
	 * @todo   展示未开发的页数
	 * @time   2018年10月8日 下午05:02
	 * @author lzg
	 * @return_type   void
	 */
	public void showUnderdevelopment(){
		render(DEVELOPING_PATH);
	}
	/**
	 * @todo   展示递交报告
	 * @time   2018年10月9日 上午11:24
	 * @author lzg
	 * @return_type   void
	 */
	public void showSubmitReport(){
		render(ORDER_MANAGE_PATH+"order_submit.html");
	}
	/**
	 * @todo   展示报告管理下的信息录入
	 * @time   2018年10月11日 上午 11:02
	 * @author lzg
	 * @return_type   void
	 */
	public void showReportInfoImport(){
		render(REPORT_MANAGE_PATH+"report_info_import.html");
	}
	/**
	 * 
	 * @time   2018年10月14日 下午3:12:08
	 * @author yangdong
	 * @todo   展示报告管理下的报告质检
	 * @param  
	 * @return_type   void
	 */
	public void showReportBusiness(){
		render(REPORT_MANAGE_PATH+"report_business_info.html");
	}
	/**
	 * 
	 * @time   2018年10月14日 下午3:12:17
	 * @author yangdong
	 * @todo   展示报告管理下的订单核实
	 * @param  
	 * @return_type   void
	 */
	public void showOrderVerify(){
		render(REPORT_MANAGE_PATH+"report_order_verify.html");
	}
	/**
	 * 
	 * @time   2018年10月14日 下午3:12:25
	 * @author yangdong
	 * @todo   展示报告管理下的订单查档
	 * @param  
	 * @return_type   void
	 */
	public void showshowOrderFiling(){
		render(REPORT_MANAGE_PATH+"report_order_filing.html");
	}
	/**
	 * 2018/10/15 15:20
	 * lzg
	 * 获取公司信息
	 */
	public void getCompanyInfo(){
		String companyId = getPara("company_id");
		CompanyModel model = getModel(CompanyModel.class);
		model  = model.findById(companyId);
		renderJson(model);
	}
	
	/**
	 * @todo   展示报告管理下的信息录入的填报详情页
	 * @time   2018年9月29日 上午 11:02
	 * @author lzg
	 * @return_type   void
	 */
	public void showReportedBasicInfo(){
		render(REPORT_MANAGE_PATH+"reported_basic_info.html");
	}
	/**
	 * @todo   展示报告管理下的可配置的填报页面
	 * @time   2018年10月26日 上午 11:02
	 * @author zc
	 * @return_type   void
	 */
	public void showReportedConfig(){
		render(REPORT_MANAGE_PATH+"report_config.html");
	}
	
	//展示列表功能公共雏形
	private Page<CreditOrderInfo> PublicListMod(String searchType){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		//从表单获取排序语句
		String sortName = getPara("sortName");
		String sortOrder = getPara("sortOrder");
		String orderBy = "";
		if(!StringUtil.isEmpty(sortName)){
			if(sortOrder!=null){
				orderBy = sortName+" "+sortOrder;
			}else{
				sortOrder = "";
				orderBy = sortName+" desc ";
			}
		}else{
			sortName = "";
		}
		//获取前台关键词
		List<Object> keywords = new LinkedList<>();
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		for (Object  columnName: WEB_PARAM_NAMES.get(searchType)) {
			if(StringUtil.isEmpty((String) getPara((String) columnName))){
				keywords.add("");
			}else{
				keywords.add((String) getPara((String) columnName).trim());
			}
		}
		//分页查询
		Page<CreditOrderInfo> pager = CreditOrderInfo.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType, this);
		//插入回显数据
		for (CreditOrderInfo page : pager.getList()) {
			for (int i = 0; i <  WEB_PARAM_NAMES.get(searchType).size(); i++) {
				page.put((String)WEB_PARAM_NAMES.get(searchType).get(i)+"Key",keywords.get(i));
			}
			page.put("pageNumber",pageNumber);
			page.put("pageSize",pageSize);
			page.put("sortName",sortName);
			page.put("sortOrder",sortOrder);
			//插入文件信息
			Integer orderId = page.get("id");
			String status = page.get("status");
			List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusType(orderId+"", status, this);
			for (CreditUploadFileModel creditUploadFileModel : files) {
				creditUploadFileModel.set("view_url","http://"+ ip+"/"+creditUploadFileModel.get("view_url"));
				creditUploadFileModel.set("url","http://"+ ip+"/"+creditUploadFileModel.get("url"));
			}
			page.put("files",files);
		}
		return pager;
	}
	
	//修改或者删除功能公共雏形
	/**
	 * @param map
	 * @return 包含旧状态码和新传入参数的实体
	 */
	private void PublicUpdateMod(Map<String,Object> map){
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		model.removeNullValueAttrs();
		model = getModel(CreditOrderInfo.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		if(map!=null){
			for (String key : map.keySet()) {
				model.set(key, map.get(key));
			}
		}
		model.update();
	}
	/**
	 * 代理分配
	* @author doushuihai  
	 * @throws Exception 
	* @date 2018年10月13日下午7:29:46  
	* @TODO
	 */
	private CreditOrderInfo PublicUpdateAgentMod(Map<String,Object> map,String ismail) throws Exception{
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		model = getModel(CreditOrderInfo.class);
		String orderId = model.get("id")+"";
		String oldStatus = model.findById(orderId).get("status");
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		if(map!=null){
			for (String key : map.keySet()) {
				model.set(key, map.get(key));
			}
		}
		model.set("status", "295");
		model.update();
		if("1".equals(ismail)){
			CreditOrderInfo order = OrderManagerService.service.getOrder(orderId, this);
			String mailaddr=order.get("mail_receiver");
			String mailaddrRe=order.get("mail_associate_recipient");
			if(StringUtils.isNotBlank(mailaddr) || StringUtils.isNotBlank(mailaddrRe)){
				String title="New Order";
				String content="Dear Sir/Madam,Good day!"
								+"We would like to place an order for a complete credit report on the following company:"
								+"Speed:" 
								+"Ref No.:" 
								+"Company name:" 
								+"Address:" 
								+"Country:" 
								+"Register Number: "
								+"Tel:"
								+"Fax:"
								+"E-mail:"
								+"Contact person:"
								+"Web-site:"
								+"Bank:"
								+"Special Note:"
								+"Please confirm receiving this order."
								+"Thank you.";
				new SendMailUtil(mailaddr, mailaddrRe, title, content).sendMail();
			}
		}
		
		return model.set("status", oldStatus);
	}
	/**
	 *获取订单数据
	 */
	/**
	 *  update加入获取代理下拉框
	* @author doushuihai  
	* @date 2018年10月13日下午2:11:44  
	* @TODO
	 */
	public void listJson() {
		//获取查询类型
		String searchType = (String) getRequest().getParameter("searchType");
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(searchType);
		List<CreditOrderInfo> rows = pager.getList();
		TemplateSysUserService templete = new TemplateSysUserService();
		//若是搜索类型是订单分配做特殊处理
		if(searchType.equals(orderAllocation)){
			for (CreditOrderInfo creditOrderInfo : rows) {
				//参数2代表角色id为2
				String seleteStr = templete.getSysUser(2, creditOrderInfo.get("report_user"));
				creditOrderInfo.put("seleteStr",seleteStr);
			}
		}
		//若是搜索类型是订单查档做特殊处理
		if(searchType.equals(orderFilingOfOrder)||searchType.equals(orderFilingOfReport)){
			for (CreditOrderInfo creditOrderInfo : rows) {
				//查询代理类型
				String seleteStr = TemplateAgentService.templateagentservice.getAgentIdString(creditOrderInfo.get("agent_id"));
				creditOrderInfo.put("seleteAgentStr",seleteStr);
				Object object = creditOrderInfo.get("agent_id");
				Object object2 = creditOrderInfo.get("agent_category");
				if(object2!=null){
					String seleteAgentCateStr = TemplateAgentService.templateagentservice.getAgentCateString(creditOrderInfo.get("agent_id"),creditOrderInfo.get("agent_category"));
					creditOrderInfo.put("seleteAgentCateStr",seleteAgentCateStr);
				}
			}
		}
		int totalRow = pager.getTotalRow();
		//若时搜索按钮
		ResultType resultType = new ResultType();
		resultType = new ResultType(totalRow,rows);
		
		renderJson(resultType);
	}
	
	public void getAgentCategory(){
		String agentid = (String) getRequest().getParameter("agentid");
		List<AgentCategoryModel> findAll = AgentCategoryModel.dao.findAll(agentid);
		renderJson(findAll);
	}
	/**
	 * @todo   订单状态保存
	 * @time   2018年9月20日 下午4:30:00
	 * @author lzg
	 * @return_type   订单编号
	 */
	public  ResultType  statusSave() {
		try {
		String code = (String) getRequest().getParameter("statusCode");
		Map<String,Object> map = new HashMap<>();
		if(code==null||"".equals(code.trim())){
			map = null;
		}else{
			map.put("status", code);
		}
		PublicUpdateMod(map);
		renderJson(new ResultType());
		return new ResultType();
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"订单状态更新失败!"));
			return new ResultType(0,"订单状态更新失败!");
		}
	}
	/**
	 * 订单代理分配
	* @author doushuihai  
	* @date 2018年10月13日下午7:20:55  
	* @TODO
	 */
	public  CreditOrderInfo  orderAgentSave() {
		try {
		String ismail = (String) getRequest().getParameter("ismail");
		Map<String,Object> map = new HashMap<>();
		CreditOrderInfo  model = PublicUpdateAgentMod(map,ismail);
		renderJson(new ResultType());
		return model;
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"订单代理分配更新失败!"));
			return null;
		}
	}
	/**
	 * 通过订单id获取文件信息
	 */
	public void getFilesByOrderId(){
		String orderId = getPara("orderId");
		List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusType(orderId+"" , this);
		for (CreditUploadFileModel creditUploadFileModel : files) {
			creditUploadFileModel.set("view_url","http://"+ ip+"/"+creditUploadFileModel.get("view_url"));
			creditUploadFileModel.set("url","http://"+ ip+"/"+creditUploadFileModel.get("url"));
		}
		ResultType result = new ResultType();
		result.setFiles(files);
		renderJson(result);
	}
	/**
	 * @todo   带有文件上传功能的保存功能
	 * @time   2018年9月21日 上午9:21:00
	 * @author lzg
	 * @return_type   void
	 */
	public void statusSaveWithFileUpLoad(){
		try {
			//从前台获取文件
			List<UploadFile>  upFileList = getFiles("Files");
			//获取订单id和状态
			CreditOrderInfo model = getModel(CreditOrderInfo.class);
			String orderId = model.get("id")+"";
			String oldStatus = model.findById(orderId).get("status")+"";
			if(orderId==null||oldStatus==null||"".equals(orderId)||"".equals(oldStatus)){
				renderJson(new ResultType(0,"订单编号和订单状态不能为空!"));
				return;
			}
			//上传文件
			ResultType result = uploadFile(orderId,oldStatus,upFileList);
			if(result.getStatusCode()==0){
				renderJson(result);
			}else{
				//更新状态
				if(statusSave().getStatusCode()==0){
					renderJson(new ResultType(0,"订单状态更新失败!"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"发生未知错误!"));
		}
	}
	/**
	 * 获取前台文件上传到文件服务器并将文件信息记录到文件实体表
	 * return resultJson
	 * @param orderId status 
	 */
	private ResultType uploadFile(String orderId, String status,List<UploadFile> upFileList){
		List<File> commonFiles = new ArrayList<File>();
		List<File> pdfFiles = new ArrayList<File>();
		CreditUploadFileModel fileModel = new CreditUploadFileModel();
		fileModel.set("business_type", status);
		fileModel.set("business_id",orderId);
		int size = upFileList.size();
		if(size>0){
			//long now = new Date().getTime();
			String now = UUID.randomUUID().toString().replaceAll("-", "");
			//上传的文件在ftp服务器按日期分目录
			String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);
			try {
				List<String> pdfNameList = new LinkedList<>();
				for(UploadFile uploadFile:upFileList){
					//获取真实文件名
					String originalFile = uploadFile.getOriginalFileName();
					//根据文件后缀名判断文件类型
					String ext = FileTypeUtils.getFileType(originalFile);
					//检查格式
					if(!FileTypeUtils.checkType(ext)){
						return new ResultType(0, "请检查 "+originalFile+" 的格式!");
					}
					//检查大小
					if(uploadFile.getFile().length()>maxPostSize){
						return new ResultType(0, originalFile+" 必须小于5兆!");
					}
					File pdf = null;
					//如果上传文档不是pdf或者图片则转化为pdf，以作预览
					if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)){
						pdf = Office2PDF.toPdf(uploadFile);
					}
					commonFiles.add(uploadFile.getFile());
					if(pdf!=null)
					pdfFiles.add(pdf);
				}
				//将文件上传到服务器
				boolean storePdfFile = FtpUploadFileUtils.storeMoreFtpFile(now+"",pdfFiles,storePath,ip,port,userName,password);
				boolean storeCommonFile = FtpUploadFileUtils.storeMoreFtpFile(now+"",commonFiles,storePath,ip,port,userName,password);
				if(!storePdfFile){
					return new ResultType(0, "预览文件生成异常!");
				}
				if(!storeCommonFile){
					return new ResultType(0, "文件上传异常!");
				}
				//String ftpName=name+now+"."+type;
				
				//将文件信息保存到实体类
				for (int i=0;i<upFileList.size();i++) {
					//获取真实文件名
					String originalFile = upFileList.get(i).getOriginalFileName();
					//不带后缀的文件名
					String originalFileName = FileTypeUtils.getName(originalFile);
					//根据文件后缀名判断文件类型
					String ext = FileTypeUtils.getFileType(originalFile);
					//上传到服务器时的文件名
					String FTPfileName = originalFileName + now + "." + ext;
					String PDFfileName = originalFileName + now + "." + "pdf";
					//String pdfFactpath=storePath+"/"+pdf_FTPfileName;
						/*if(pdf!=null){
							pdf.delete();
						}*/
						String factpath = storePath + "/" + FTPfileName;
						String ftpFactpath = storePath + "/" + PDFfileName;
						//String url = "http://" + ip+"/" + storePath + "/" + FTPfileName;
						Integer userid = getSessionUser().getUserid();
						//将上传信息维护进实体表
						UploadFileService.service.save(0,upFileList.get(i), factpath,factpath,ftpFactpath,ftpFactpath,fileModel,originalFileName+now,userid);
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				return new ResultType(0, "发生未知错误!");
			}finally {
				pdfFiles.forEach((File pdf)->{pdf.delete();});
			}
			return new ResultType(1, "文件上传成功!");
		}else{
			return new ResultType(1, "操作成功!");
		}
	}
	
	/**
	 * 根据文件id删除文件
	 */
	public void deleteFile(){
		try {
			CreditUploadFileModel model = getModel(CreditUploadFileModel.class);
			model.set("del_flag", "1");
			model.update();
			renderJson(new ResultType(1, "操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "操作失败!"));
		}
	}
	/**
	 * 2018/10/15 16:20  2018/10/24 16:20复更
	 * lzg
	 * 报告管理下的信息录入的填报页面的信息(除了企业注册信息信息)
	 */
	public void reportedJson(){
		String companyId = getPara("company_id");
		String flagStr = getPara("flagStr","");
		List<Object> params = new ArrayList<>();
		params.add(companyId);
		List rows = null;
		switch (flagStr) {
		case  "tableRecord": 
			CreditCompanyHis companyHis = getModel(CreditCompanyHis.class); 
			rows = companyHis.find("select * from credit_company_his where company_id=? and del_flag=0",params.toArray());
			break;
		case  "tableShareholdersInfo":
			CreditCompanyShareholder companyShareholder = getModel(CreditCompanyShareholder.class); 
			rows = companyShareholder.find("select * from credit_company_shareholder where company_id=? and del_flag=0",params.toArray());
			break;
		case "tableShareholdersDetail":
			CreditCompanyShareholderDetail companyShareholderDetail  =  getModel(CreditCompanyShareholderDetail.class);
			rows = companyShareholderDetail.find("select * from credit_company_shareholder_detail where company_id=? and del_flag=0",params.toArray());
			break;
		case "tableInvestment":
			CreditCompanyInvestment companyInvestment = getModel(CreditCompanyInvestment.class); 
			rows = companyInvestment.find("select * from credit_company_investment where company_id=? and del_flag=0",params.toArray());
			break;
		case "tableManagement":
			CreditCompanyManagement companyManagement = getModel(CreditCompanyManagement.class); 
			rows = companyManagement.find("select * from credit_company_management where company_id=? and del_flag=0",params.toArray());
			break;
		default:
			rows = new ArrayList<>();
			break;
		}
		ResultType result = new ResultType();
		result.setRows(rows);
		renderJson(result);
	}
	
	
	/**
	 * 2018/10/12
	 * lzg
	 * 报告管理下的信息录入的填报页面的保存
	 */
	public void reportedSave(){
		try {
		//从前台获取数据
		String companyHistoryJson = getPara("companyHistory");
		String tableShareholdersInfoJson = getPara("tableShareholdersInfo");
		String tableShareholdersDetailJson = getPara("tableShareholdersDetail");
		String tableInvestmentJson = getPara("tableInvestment");
		String tableManagementJson = getPara("tableManagement");
		String companyZhuCeJson = getPara("companyZhuCe");
		String companyId = getPara("companyId");
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		//保存前获取已有数据
		CreditCompanyHis companyHis = getModel(CreditCompanyHis.class); 
		CreditCompanyInvestment companyInvestment = getModel(CreditCompanyInvestment.class); 
		CreditCompanyManagement companyManagement = getModel(CreditCompanyManagement.class); 
		CreditCompanyShareholder companyShareholder = getModel(CreditCompanyShareholder.class); 
		CreditCompanyShareholderDetail companyShareholderDetail  =  getModel(CreditCompanyShareholderDetail.class);
		
		final List<CreditCompanyHis> companyHisList = companyHis.find("select * from credit_company_his where company_id = "+companyId);
		final List<CreditCompanyInvestment> companyInvestmentList = companyInvestment.find("select * from credit_company_investment where company_id = "+companyId);
		final List<CreditCompanyManagement> companyManagementList = companyManagement.find("select * from credit_company_management where company_id = "+companyId);
		final List<CreditCompanyShareholder> companyShareholderList = companyShareholder.find("select * from credit_company_shareholder where company_id = "+companyId);
		final List<CreditCompanyShareholderDetail> companyShareholderDetailList = companyShareholderDetail.find("select * from credit_company_shareholder_detail where company_id = "+companyId);
		//转化为model集合
		final List<BaseProjectModel>  companyHistoryModelList = infoEntry(companyHistoryJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyHis");
		final List<BaseProjectModel>  CreditCompanyInvestmentList = infoEntry(tableInvestmentJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyInvestment");
		final List<BaseProjectModel>  CreditCompanyManagementList = infoEntry(tableManagementJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement");
		final List<BaseProjectModel>  CreditCompanyShareholderList = infoEntry(tableShareholdersInfoJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder");
		final List<BaseProjectModel>  CreditCompanyShareholderDetailList = infoEntry(tableShareholdersDetailJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholderDetail");
		List<BaseProjectModel> companyZhuCeJsonModelList = infoEntry(companyZhuCeJson.trim(),"com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo");
		//数据库事务
		Db.tx(new IAtom(){
			@Override
			//在这里写要执行的操作，在执行的过程中如果有异常将回滚，如果return false 就也回滚
			public boolean run() throws SQLException {
				//保存前删除已有的记录
				companyHisList.forEach( (CreditCompanyHis baseProjectModel)->{baseProjectModel.set("del_flag", "1"); baseProjectModel.update();});
				companyInvestmentList.forEach( (CreditCompanyInvestment baseProjectModel)->{baseProjectModel.set("del_flag", "1"); baseProjectModel.update();});
				companyManagementList.forEach( (CreditCompanyManagement baseProjectModel)->{baseProjectModel.set("del_flag", "1"); baseProjectModel.update();});
				companyShareholderList.forEach( (CreditCompanyShareholder baseProjectModel)->{baseProjectModel.set("del_flag", "1"); baseProjectModel.update();});
				companyShareholderDetailList.forEach( (CreditCompanyShareholderDetail baseProjectModel)->{baseProjectModel.set("del_flag", "1"); baseProjectModel.update();});
				//保存操作
				companyHistoryModelList.forEach( (BaseProjectModel  baseProjectModel) ->{baseProjectModel.remove("id") .set("create_by",userId) .set("create_date", now) .set("company_id", companyId) .save();});
				CreditCompanyInvestmentList.forEach( (BaseProjectModel  baseProjectModel) ->{baseProjectModel.remove("id") .set("create_by",userId) .set("create_date", now) .set("company_id", companyId) .save();});
				CreditCompanyManagementList.forEach( (BaseProjectModel  baseProjectModel) ->{baseProjectModel.remove("id") .set("create_by",userId) .set("create_date", now) .set("company_id", companyId) .save();});
				CreditCompanyShareholderList.forEach( (BaseProjectModel  baseProjectModel) ->{baseProjectModel.remove("id") .set("create_by",userId) .set("create_date", now) .set("company_id", companyId) .save();});
				CreditCompanyShareholderDetailList.forEach( (BaseProjectModel  baseProjectModel) ->{baseProjectModel.remove("id") .set("create_by",userId) .set("create_date", now) .set("company_id", companyId) .save();});
				return true;
			}
		});
	
		//公司基本信息更新
		for (BaseProjectModel<? extends CreditCompanyInfo> baseProjectModel : companyZhuCeJsonModelList) {
			baseProjectModel.update();
		}
		//状态更新
		if(statusSave().getStatusCode()==0){
			throw new RuntimeException();
		}
		renderJson(new ResultType(1,"操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"请输入合乎规范的值!"));
		}
	}
	
	/**
	 * 把 形如[{"a":b,"a1":b1},{"c":d,"a1":b1},{"e":f,"a1":b1}]的json数组分解放进model里
	 * @param <T>
	 * @param jsonStr
	 * @param model
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private   <T> List<BaseProjectModel> infoEntry(String jsonStr,String entryTypeParam) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(jsonStr==null||"".equals(jsonStr.trim())||!jsonStr.contains("{")||!jsonStr.contains(":"))
			return new ArrayList<BaseProjectModel>();
		List<BaseProjectModel> list = new ArrayList<BaseProjectModel>();
		Map<String,String> map = new HashMap<>();
		String jsonStr2 = jsonStr.replace("\"", "");
		String[] jsonStr3 = jsonStr2.split("}");
		if(jsonStr3!=null||"".equals(jsonStr3)){
			for (String string : jsonStr3) {
				if(string==null||"".equals(string.trim())||"]".equals(string.trim())){
					continue;
				}
				String[] string4 = string.split(",");
				for (String string2 : string4) {
					if(string2==null||"".equals(string2)){
						continue;
					}
					String string5 = string2.substring(string2.indexOf("{")+1,string2.indexOf(":"));
					String string6 =  string2.substring(string2.indexOf(":")+1);
					if("null".equals(string6)){
						continue;
					}
					map.put(string5, string6);
				}
				//反射获取Class对象
				@SuppressWarnings("rawtypes")
				Class entryType = null;
				BaseProjectModel model = null;
					entryType = Class.forName(entryTypeParam);
						//根据Class对象创建实例
						model = (BaseProjectModel) entryType.newInstance();
				for (String key : map.keySet()) {
					System.out.println(key+":"+map.get(key));
					model.set(key.trim(), map.get(key).trim());
				}
				model.set("update_by", getSessionUser().getUserid());
				model.set("update_date", getNow());
				list.add(model);
				map.clear();
			}
			
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
}

