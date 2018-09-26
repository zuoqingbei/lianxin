package com.hailian.modules.credit.usercenter.controller;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.jfinal.json.JFinalJson;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

import net.sf.json.JSONArray;
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
	//页面公共路径
	private static final String PATH = "/pages/credit/usercenter/order_manage/";
	//每种搜索类型需要对应的关键词字段名
	public static final Map<String,List<Object>> TYPE_KEY_COLUMN = new HashMap<>();
	//每种搜索类型需要对应的前端属性名
	public static final Map<String,List<Object>> WEB_PARAM_NAMES = new HashMap<>();
	/**
	 * 订单分配的搜索类型
	 */
	public static String orderAllocation = "-1";
	public static LinkedList<Object> orderAllocationColumns = new LinkedList<>();
	public static LinkedList<Object> orderAllocationParamNames = new LinkedList<>();
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
	}
	static{
		WEB_PARAM_NAMES.put(orderAllocation, orderAllocationParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfReport, orderVerifyOfReportParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfOrder, orderVerifyOfOrderParamNames);
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
		//存入回显数据
		for (CreditOrderInfo page : pager.getList()) {
			for (int i = 0; i <  WEB_PARAM_NAMES.get(searchType).size(); i++) {
				page.put((String)WEB_PARAM_NAMES.get(searchType).get(i)+"Key",keywords.get(i));
			}
			page.put("pageNumber",pageNumber);
			page.put("pageSize",pageSize);
			page.put("sortName",sortName);
			page.put("sortOrder",sortOrder);
		}
		return pager;
	}
	
	//修改或者删除功能公共雏形
	private CreditOrderInfo PublicUpdateMod(Map<String,Object> map){
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		String b = model.get("verify_name");
		String a = getPara("verify_name");
		String c = getAttr("verify_name");
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		for (String key : map.keySet()) {
			model.set(key, map.get(key));
		}
		model.update();
		return model;
	}
	/**
	 * @todo   展示订单分配页
	 * @time   2018年9月14日 下午 14:38
	 * @author lzg
	 * @return_type   void
	 */
	public void showReallocation(){
		render(PATH+"order_allocation.html");
	}
	/**
	 * @todo   展示订单管理下的订单核实页
	 * @time   2018年9月12日 下午 13:30
	 * @author lzg
	 * @return_type   void
	 */
	public void showOrderVerifyOfOrders(){
		render(PATH+"order_verify.html");
	}
	/**
	 * @todo   展示订单查档页
	 * @time   2018年9月26日 下午 13:30
	 * @author lzg
	 * @return_type   void
	 */
	public void showOrderFiling(){
		render(PATH+"order_filing.html");
	}
	/**
	 *获取订单数据
	 */
	public void reallocationJson() {
		//获取查询类型
		String searchType = (String) getRequest().getParameter("searchType");
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(searchType);
		List<CreditOrderInfo> rows = pager.getList();
		TemplateSysUserService templete = new TemplateSysUserService();
		for (CreditOrderInfo creditOrderInfo : rows) {
			//参数2代表角色id为2
			String seleteStr= templete.getSysUser(2, creditOrderInfo.get("report_user"));
			creditOrderInfo.put("seleteStr",seleteStr);
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
	public String statusSave() {
		String code = (String) getRequest().getParameter("statusCode");
		Map<String,Object> map = new HashMap<>();
		map.put("status", code);
		CreditOrderInfo model = PublicUpdateMod(map);
		reallocationJson();
		return model.get("num");
	}
	
	/**
	 * @todo   订单管理下的订单核实的保存
	 * @time   2018年9月21日 上午9:21:00
	 * @author lzg
	 * @return_type   void
	 */
	public void verifyOfOrderMangerSave(){
		String orderNum = getModel(CreditOrderInfo.class).get("id")+"";
		uploadFile(orderNum);
		statusSave();
	}
	
	/**
	 * 获取前台文件上传到文件服务器并将文件信息记录到文件实体表
	 */
	private void uploadFile(String orderNum){
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		CreditUploadFileModel fileModel = new CreditUploadFileModel();
		fileModel.set("business_type", "0");
		fileModel.set("business_id",orderNum);
		int size = upFileList.size();
		if(size>0){
			try {
				for(UploadFile uploadFile:upFileList){
					String originalFile = uploadFile.getOriginalFileName();
					String ext = "";
					//获取真实文件名
					String originalFileName = FileTypeUtils.getName(originalFile);
					//获取文件类型
					ext = FileTypeUtils.getFileType(originalFile);
					//错误返回json
					String errorJson = "{uploadMessage:'"+uploadFile.getOriginalFileName()+"上传失败!"+"'},{uploadStatus:0}";
					if (uploadFile!=null&&uploadFile.getFile().length()<=maxPostSize&&FileTypeUtils.checkType(ext)) {
						//上传的文件在ftp服务器按日期分目录
						String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);
						String now=DateUtils.getNow(DateUtils.YMDHMS);
						//上传到服务器时的文件名
						String FTPfileName = originalFileName + now + "." + ext;
						String fileName = originalFileName + now;
						boolean storeFile = FtpUploadFileUtils.storeFile(FTPfileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
						if(storeFile){
							String factpath = storePath + "/" + FTPfileName;
							String url = "http://" + ip+"/" + storePath + "/" + FTPfileName;
							Integer userid = getSessionUser().getUserid();
							fileModel.set("business_id",orderNum);
							//将上传信息维护进实体表
							UploadFileService.service.save(0,uploadFile, factpath,url,fileModel,fileName,userid);
						}else{
							renderJson(errorJson);
						}
					}else{
						renderJson(errorJson);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				renderJson("{uploadMessage:'"+"发生未知错误!"+"'},{uploadStatus:0}");
			}
		}
	}
	
	
	
	
}
