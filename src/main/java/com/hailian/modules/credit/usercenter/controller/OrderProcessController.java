package com.hailian.modules.credit.usercenter.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.front.template.TemplateSysUserService;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
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
	//每种搜索类型需要对应的关键词字段名
	public static final Map<String,List<Object>> TYPE_KEY_COLUMN = new HashMap<>();
	//每种搜索类型需要对应的前端属性名
	public static final Map<String,List<Object>> WEB_PARAM_NAMES = new HashMap<>();
	/**
	 * 订单分配的搜索类型
	 */
	public static String orderAllocation = "-1";
	public static LinkedList<Object> orderAllocationColumns = new LinkedList<>();//存储模糊搜索时后台sql对应字段
	public static LinkedList<Object> orderAllocationParamNames = new LinkedList<>();//存储模糊搜索时前台对应参数名
	/**
	 * 报告管理中的订单核实
	 */
	public static String orderVerifyOfReport = "-2";
	public static LinkedList<Object> orderVerifyOfReportColumns = new LinkedList<>(); 
	public static LinkedList<Object> orderVerifyOfReportParamNames = new LinkedList<>(); 
	/**
	 * 订单管理中的订单核实
	 */
	public static String orderVerifyOfOrder = "-3";
	public static LinkedList<Object> orderVerifyOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderVerifyOfOrderParamNames = new LinkedList<>();
	/**
	 * 订单管理中的订单查档
	 */
	public static String orderFilingOfOrder = "-4";
	public static LinkedList<Object> orderFilingOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderFilingOfOrderParamNames = new LinkedList<>();
	/**
	 * 订单管理中的递交订单
	 */
	public static String orderSubmitOfOrder = "-5";
	public static LinkedList<Object> orderSubmitOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderSubmitOfOrderParamNames = new LinkedList<>();
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
	}
	static{
		WEB_PARAM_NAMES.put(orderAllocation, orderAllocationParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfReport, orderVerifyOfReportParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfOrder, orderVerifyOfOrderParamNames);
		WEB_PARAM_NAMES.put(orderFilingOfOrder, orderFilingOfOrderParamNames);
		WEB_PARAM_NAMES.put(orderSubmitOfOrder, orderSubmitOfOrderParamNames);
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
	 * @todo   展示报告管理下的信息录入的填报详情页
	 * @time   2018年9月29日 上午 11:02
	 * @author lzg
	 * @return_type   void
	 */
	public void showReportedBasicInfo(){
		render(REPORT_MANAGE_PATH+"reported_basic_info.html");
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
			if(StringUtil.isEmpty((String) model.get((String) columnName))){
				keywords.add("");
			}else{
				keywords.add((String) model.get((String) columnName));
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
			page.put("files",files);
		}
		return pager;
	}
	
	//修改或者删除功能公共雏形
	/**
	 * @param map
	 * @return 包含旧状态码和新传入参数的实体
	 */
	private CreditOrderInfo PublicUpdateMod(Map<String,Object> map){
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
		model.update();
		return model.set("status", oldStatus);
	}
	
	/**
	 *获取订单数据
	 */
	public void listJson() {
		//获取查询类型
		String searchType = (String) getRequest().getParameter("searchType");
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(searchType);
		List<CreditOrderInfo> rows = pager.getList();
		TemplateSysUserService templete = new TemplateSysUserService();
		if(searchType.equals(orderAllocation)){//若是搜索类型是订单分配
			for (CreditOrderInfo creditOrderInfo : rows) {
				//参数2代表角色id为2
				String seleteStr = templete.getSysUser(2, creditOrderInfo.get("report_user"));
				creditOrderInfo.put("seleteStr",seleteStr);
			}
		}
		int totalRow = pager.getTotalRow();
		ResultType resultType = new ResultType(totalRow,rows);
		renderJson(resultType);
	}
	
	/**
	 * @todo   订单状态保存
	 * @time   2018年9月20日 下午4:30:00
	 * @author lzg
	 * @return_type   订单编号
	 */
	public  CreditOrderInfo  statusSave() {
		try {
		String code = (String) getRequest().getParameter("statusCode");
		Map<String,Object> map = new HashMap<>();
		map.put("status", code);
		CreditOrderInfo  model = PublicUpdateMod(map);
		renderJson(new ResultType());
		return model;
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"订单状态更新失败!"));
			return null;
		}
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
			String orderId =model.get("id")+"";
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
				if(statusSave()==null){
					renderJson(new ResultType(0,"订单状态更新失败!"));
				}
			}
		} catch (Exception e) {
			renderJson(new ResultType(0,"发生未知错误!"));
		}
	}
	/**
	 * 获取前台文件上传到文件服务器并将文件信息记录到文件实体表
	 * return resultJson
	 * @param orderId status 
	 */
	private ResultType uploadFile(String orderId, String status,List<UploadFile> upFileList){
		List<File> files = new ArrayList<File>();
		CreditUploadFileModel fileModel = new CreditUploadFileModel();
		fileModel.set("business_type", status);
		fileModel.set("business_id",orderId);
		int size = upFileList.size();
		if(size>0){
			long now = new Date().getTime();
			//上传的文件在ftp服务器按日期分目录
			String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);
			try {
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
					files.add(uploadFile.getFile());
				}
				boolean storeFile = FtpUploadFileUtils.storeMoreFtpFile(now+"",files,storePath,ip,port,userName,password);
				if(!storeFile){
					return new ResultType(0, "文件上传异常!");
				}
				//String ftpName=name+now+"."+type;
				
				//将文件信息保存到实体类
				for (UploadFile uploadFile:upFileList) {
					//获取真实文件名
					String originalFile = uploadFile.getOriginalFileName();
					//不带后缀的文件名
					String originalFileName = FileTypeUtils.getName(originalFile);
					//根据文件后缀名判断文件类型
					String ext = FileTypeUtils.getFileType(originalFile);
					//上传到服务器时的文件名
					String FTPfileName = originalFileName + now + "." + ext;
					if(storeFile){
						String factpath = storePath + "/" + FTPfileName;
						String url = "http://" + ip+"/" + storePath + "/" + FTPfileName;
						Integer userid = getSessionUser().getUserid();
						//将上传信息维护进实体表
						UploadFileService.service.save(0,uploadFile, factpath,url,fileModel,originalFileName+now,userid);
					}else{
						return new ResultType(0, "实体保存异常!");
					}
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				return new ResultType(0, "发生未知错误!");
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
		CreditUploadFileModel model = getModel(CreditUploadFileModel.class);
		model.set("del_flag", "1");
		model.update();
		renderJson(new ResultType(1, "操作成功!"));
	}
	
	
	
}

