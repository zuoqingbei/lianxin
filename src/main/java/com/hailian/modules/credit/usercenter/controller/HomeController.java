

package com.hailian.modules.credit.usercenter.controller;
import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hailian.modules.admin.ordermanager.model.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.api.constant.RoleCons;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.agentmanager.service.AgentPriceService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.company.service.CompanyService;
import com.hailian.modules.credit.mail.service.MailService;
import com.hailian.modules.credit.orderflowconf.model.CreditOrderFlowConf;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.usercenter.service.HomeService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.menu.SysMenu;
import com.hailian.system.user.SysUser;
import com.hailian.system.user.UserSvc;
import com.hailian.util.CharacterParser;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.word.BaseBusiCrdt;
import com.hailian.util.word.BusiUtil;
import com.hailian.util.word.Roc102;
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
	//public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public static final String searver_port = Config.getStr("searver_port");//端口号
	public static final String ftp_store = Config.getStr("ftp_store");//存储目录
	public static Logger log = Logger.getLogger(HomeController.class);

	public void index() {
		List<SysDictDetail> country=	SysDictDetail.dao.find("select * from sys_dict_detail where dict_type=?","country");
		setAttr("country", country);
		List<CreditCustomInfo> customerId=	CreditCustomInfo.dao.find("select * from credit_custom_info");
	    setAttr("customer", customerId);
	        //订单核实数量
	  		int orderhs=CreditOrderInfo.dao.find("select * from credit_order_info where status='500' and del_flag='0'").size();
	  		//订单查档数量
	  		int ordercd=CreditOrderInfo.dao.find("select * from credit_order_info where status='295' and del_flag='0' ").size();
	  		//订单信息质检数量
	  		int orderzj1=CreditOrderInfo.dao.find("select * from credit_order_info where status in('294','303','308') and del_flag='0'").size();
	  		setAttr("orderhs", orderhs);
	  		setAttr("ordercd", ordercd);
	  		setAttr("orderzj", orderzj1);	
	  		 List<SysUser> reporter = SysUser.dao.getReporter(RoleCons.REPORTER+"");//报告员
			 List<SysUser> transer = SysUser.dao.getReporter(RoleCons.TRANSER+"");//翻译员
			 List<SysUser> analer = SysUser.dao.getReporter(RoleCons.ANALER+"");//分析员
			 setAttr("reporter", reporter);
			 setAttr("transer", transer);
			 setAttr("analer", analer);
	    render(path+"index.html");
		
	}
	public void allOrder() {
		SysUser user= (SysUser) getSessionUser();
		if(user==null||"".equals(user)) {
			redirect("/credit/front/usercenter/login");
		}else {
			List<SysDictDetail> country=	SysDictDetail.dao.find("select * from sys_dict_detail where dict_type=?","country");
			setAttr("country", country);
			List<CreditCustomInfo> customerId=	CreditCustomInfo.dao.find("select * from credit_custom_info");
		    setAttr("customer", customerId);
			CreditOrderInfo model=new CreditOrderInfo();
			setAttr("model", model);
			
			
			 List<SysUser> reporter = SysUser.dao.getReporter(RoleCons.REPORTER+"");//报告员
			 List<SysUser> transer = SysUser.dao.getReporter(RoleCons.TRANSER+"");//翻译员
			 List<SysUser> analer = SysUser.dao.getReporter(RoleCons.ANALER+"");//分析员
			 setAttr("reporter", reporter);
			 setAttr("transer", transer);
			 setAttr("analer", analer);
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
		List<CreditUploadFileModel> files=CreditUploadFileModel.dao.getFile(id+"");//修改关联关系
		for(CreditUploadFileModel file:files) {
			String view_url=file.get("view_url");
			String url=file.get("url");
			file.set("view_url",  "http://"+ip+":"+searver_port+"/"+view_url);
			file.set("url", "http://"+ip+":"+searver_port+"/"+url);	
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
		int size=1;
		//获取国家类型
		CountryModel country=CountryModel.dao.findById(order.getStr("country"));
		String countryType=country.getStr("type");
		if("207".equals(countryType) || "208".equals(countryType) || "209".equals(countryType)) {
			countryType="148";
		}
		//根据国家类型获取流程列表
		List<CreditOrderFlowConf> cofc=CreditOrderFlowConf.dao.findByType(countryType);		
		List<Integer> length=new ArrayList<Integer>();
		List<Integer> length2=new ArrayList<Integer>();
		if(cof.size()>0) {
			
		for(CreditOrderFlowConf cofc2:cofc) {
			length2.add(cofc2.getInt("id"));
			for(CreditOrderFlow cof2:cof) {
				if(cof2.getStr("order_state").equals(cofc2.getStr("flow_state"))) {
					length.add(cofc2.getInt("id"));
					break;
				}
			}
		}
		Collections.sort(length);
		for(int i=0;i<length2.size();i++) {
            if(length.size()>0) {
                if (length2.get(i) == length.get(length.size() - 1)) {
                    size = i + 1;
                }
            }
		}
		}
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
		if(StringUtils.isNotBlank(getPara("num"))) {
			model.set("num", getPara("num"));
		}
		if(StringUtils.isNotBlank(getPara("reference_num"))) {
			model.set("reference_num", getPara("reference_num"));
		}
		if(StringUtils.isNotBlank(getPara("reporter_id"))) {
			model.set("report_user", getPara("reporter_id"));
		}
		if(StringUtils.isNotBlank(getPara("transer_id"))) {
			model.set("translate_user", getPara("transer_id"));
		}
		
		if(StringUtils.isNotBlank(getPara("analer_id"))) {
			model.set("analyze_user", getPara("analer_id"));
		}
		if(StringUtils.isNotBlank(getPara("report_type"))) {
			model.set("report_type", getPara("report_type"));
		}
		String sortname=getPara("sortName");
		if(!StringUtils.isNotBlank(sortname)) {
			sortname="create_date";
		}
		if("receiver_date".equals(sortname)) {
			sortname="create_date";
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
		for(CreditOrderInfo order:rows){
			//获取附件
			List<CreditUploadFileModel> files=CreditUploadFileModel.dao.getFile(order.get("id")+"");//修改关联关系
			for(CreditUploadFileModel file:files) {
				String view_url=file.get("view_url");
				String url=file.get("url");
				file.set("view_url",  "http://"+ip+":"+searver_port+"/"+view_url);
				file.set("url", "http://"+ip+":"+searver_port+"/"+url);	
			}
			order.put("files", files);
		}
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
		   //订单核实数量
  		int orderhs=CreditOrderInfo.dao.find("select * from credit_order_info where status='500' and del_flag='0'").size();
  		//订单查档数量
  		int ordercd=CreditOrderInfo.dao.find("select * from credit_order_info where status='295' and del_flag='0' ").size();
  		//订单信息质检数量
  		int orderzj1=CreditOrderInfo.dao.find("select * from credit_order_info where status in('294','303','308') and del_flag='0'").size();
        List<CreditCompanyInfo> companys=OrderManagerService.service.getCompany();
		
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
		record.set("orderzj", orderzj1);
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
//		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
//		setAttr("company",company);
		setAttr("model", model);
		render(path + "/order_manage/create_order.html");
	}

	/**
	 * neironggengxin
	 */
	public void updateOrder() {
		CreditOrderInfo model=new CreditOrderInfo();
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
//		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
//		setAttr("company",company);
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
	public void saveOrder()  {
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		if(upFileList.size()>0){
			System.out.println(upFileList.get(0).getFileName());
		}
		Integer userid = getSessionUser().getUserid();
		List<File> ftpfileList=new ArrayList<File>();
		String num =CreditOrderInfo.dao.getNumber();
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    String year=String.valueOf(calendar.get(Calendar.YEAR));
	    String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Object modelid=model.get("id");
		toString(model,380);
		if((String)model.get("continent")==null) {
			renderJson(new ResultType(0,"缺失 "+"地区参数"+" ,订单创建失败!"));
			return;
		}
		if((String)model.get("country")==null) {
			renderJson(new ResultType(0,"缺失 "+"国家ID"+" ,订单创建失败!"));
			return;
		}
		if((String)model.get("report_type")==null) {
			renderJson(new ResultType(0,"缺失 "+"报告类型参数"+" ,订单创建失败!"));
			return;
		}
		if((String)model.get("order_type")==null) {
			renderJson(new ResultType(0,"缺失 "+"订单类型"+" ,订单创建失败!"));
			return;
		}
		if( model.get("report_language")==null) {
			renderJson(new ResultType(0,"缺失 "+"报告语言"+" ,订单创建失败!"));
			return;
		}
		if((String)model.get("speed")==null) {
			renderJson(new ResultType(0,"缺失 "+"报告速度"+" ,订单创建失败!"));
			return;
		}
		if((String)	model.get("custom_id")==null) {
			renderJson(new ResultType(0,"缺失 "+"客户id"+" ,订单创建失败!"));
			return;
		}

		
		toString(model,400);
		String countryId = model.get("country");
		if(countryId!=null&&!"".equals(countryId)){
			Db.update("update credit_country set `scale`=`scale`+1 where id="+countryId);
		}
		toString(model,405);
		if(model.get("id")==null){
			model.set("num", num);
		}
		model.set("receiver_date", date);
		model.set("year", year);
		model.set("month", month);
		//获取订单公司名称
		String right_company_name_en=model.get("right_company_name_en");
		//获取报告价格
		CreditReportPrice pricemodel = OrderManagerService.service.getOrderprice(model.get("continent").toString(), model.get("speed").toString(), model.get("report_type").toString(), model.get("order_type").toString(), model.get("custom_id").toString(), model.get("country").toString());
																	//getOrderprice(String countryType,String speed,String reporttype,String orderType,String customid,String countryid);
		if(pricemodel!=null){
			model.set("price_id", pricemodel.get("id"));
			//orderReal.set("price_id", "99999");
		}else{
			model.set("price_id", "");
			//errormark+=errornum+".第"+(r+1)+"行，此订单没有获取到报告价格，请联系管理员！;";
			String orderNum = model.get("num");
			ReportInfoGetDataController.sendMessageWhenCreate(orderNum,userid,"此订单没有获取到报告价格，请联系管理员!(如创建失败请忽略此条消息!)");
		}
	    String is_fastsubmmit=model.get("is_fastsubmmit");
		CreditOrderInfo theSameOrder=null;
		//查询到有相同公司报告直接提交
		/*if(!is_fastsubmmit.equals("-1")){
			theSameOrder = OrderManagerService.service.getTheSameOrder(right_company_name_en,model.get("report_type")+"",model.get("report_language")+"", this);
			toString(model,418);
			model.set("is_fastsubmmit", theSameOrder.get("id"));
			model.set("company_id", theSameOrder.get("id"));
			model.set("status", "311");
		}*/
		//获取报告员id
		if(model.get("id")==null){
			String reportIdtoOrder = OrderManagerService.service.getUserIdtoOrder(RoleCons.REPORTER);//根据自动分配规则获取该订单指定的报告员
			model.set("report_user", reportIdtoOrder);
		}
		//国外代理自动分配 除韩国新加坡马来西亚
		boolean isNeedAgent=false;//是否需要自动分配
		boolean isagent=false;//自动分配是否成功
		if(!countryId.equals("106") && !countryId.equals("61") && !countryId.equals("62") && !countryId.equals("92")){
			isNeedAgent=true;
			//代理自动分配
			AgentPriceModel agentPrice = AgentPriceService.service.getAgentAbroad(countryId,model.get("speed"));
			if(agentPrice!=null){
				model.set("status", "295");
				model.set("agent_id", agentPrice.get("agent_id"));
				model.set("agent_priceId", agentPrice.get("id"));
				isagent=true;
			}
		}
		boolean isFastsubmmit=false;
		isFastsubmmit=(!is_fastsubmmit.equals("-1"));
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
		Date date1=new Date();
		model.set("create_date", date1);
		model.set("update_by", userid);
		model.set("update_date", date1);
		String id = "";
		try {
			toString(model,458);
			id = OrderManagerService.service.modifyOrder(model,user,this);//创建订单或订单更新
			toString(model,460);
		} catch (Exception e1) {
			e1.printStackTrace();
			renderJson(new ResultType(0,"订单创建失败!"));
			return;
		}//保存订单
		//查询到有相同公司报告直接提交
		if(isFastsubmmit){
			int companInfoId = crateReportByOrder(userid, model, id);//根据新订单创建报告
			theSameOrder = OrderManagerService.service.getTheSameOrder(right_company_name_en,model.get("report_type")+"",model.get("report_language")+"", this);
			CreditOrderInfo order = new CreditOrderInfo();
			order.set("company_id",companInfoId);
			order.set("status", "311");
			if(null!=theSameOrder) {
				order.set("company_by_report",theSameOrder.get("company_by_report"));
				order.set("is_fastsubmmit", theSameOrder.get("id"));
			}
			order.set("id",id);
			order.update();
			isNeedAgent=false;//是否需要自动分配
			isagent=true;
			model=CreditOrderInfo.dao.findById(id);
		}else{
			//非快速递交时创建报告
			//System.out.println(model.get("id"));
			if(modelid==null){
				int companInfoId = crateReportByOrder(userid, model, id);//根据新订单创建报告
				theSameOrder=CreditOrderInfo.dao.isTheSameOrder(model.get("right_company_name_en").toString(), model.get("report_type").toString(), model.get("report_language").toString(), this);
				CreditOrderInfo order = new CreditOrderInfo();
				order.set("company_id",companInfoId);
				if(null!=theSameOrder) {
					order.set("company_by_report",theSameOrder.get("company_by_report"));
				}
				order.set("id",id);
				
				order.update();
			}
		}

		CreditOperationLog.dao.addOneEntry(userid, model, "","/credit/front/home/saveOrder");//操作日志记录
		cof.save();
		CreditUploadFileModel model1= new CreditUploadFileModel();
		model1.set("business_type", "291");
		model1.set("business_id", id);
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
					
					if (uploadFile != null /*&& uploadFile.getFile().length()<=maxPostSize*/ && FileTypeUtils.checkType(ext)) {
						String storePath = ftp_store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
						String now=UUID.randomUUID().toString().replaceAll("-", "");
						originalFileName=FileTypeUtils.getName(uploadFile.getFile().getName());
						//originalFileName=URLEncoder.encode(originalFileName, "UTF-8");
						CharacterParser c=new CharacterParser();
						originalFileName=c.getSpelling(originalFileName);
						String FTPfileName=originalFileName+now+"."+ext;
						String fileName=originalFileName+now;
						String pdf_FTPfileName="";
						ftpfileList.add(uploadFile.getFile());
						if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)&& !ext.equalsIgnoreCase("html")){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
							try {
								File pdf = Office2PDF.excelPdfNew(uploadFile);
								//File pdf = Office2PDF.toPdf(uploadFile);
								pdf_FTPfileName=originalFileName+now+"."+"pdf";
								if(pdf!=null)
								ftpfileList.add(pdf);
							}catch (Exception e){
								e.printStackTrace();
							}

						}else if(ext.equals("pdf") ||FileTypeUtils.isImg(ext)|| ext.equalsIgnoreCase("html")){
							pdf_FTPfileName=FTPfileName;
						}
						//String now,List<File> filelist,String storePath,String url,int port,String userName,String password
						boolean storeFile = FtpUploadFileUtils.storeMoreFtpFile3(now, ftpfileList,storePath,ip,port,userName,password);//上传
						if(storeFile){
							String ftpName=originalFileName+now+"."+ext;
							String factpath=storePath+"/"+ftpName;
							String pdfFactpath=storePath+"/"+pdf_FTPfileName;
							String url= storePath+"/"+FTPfileName;
							userid = getSessionUser().getUserid();
							model1.set("business_id", id);
							String pdfUrl=storePath+"/"+pdf_FTPfileName;
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
			ResultType resultType = new ResultType(1,"操作成功");
			if(!isNeedAgent){
				//快速递交的直接发送报告
				if(isFastsubmmit){
					String result=reSendReport(model.getStr("is_fastsubmmit"), model.getStr("num"),getSessionUser().getUserid());
					if(!"success".equals(result)){
						resultType = new ResultType(0,"递交订单失败！");
					}
				}
				renderJson(resultType); return;
			}
			if(!isagent){
				resultType = new ResultType(3,"提交成功，但该订单没有找到合适的代理，请注意!");
			}else{	//发送邮件
				try {
					MailService.service.toSendMail("1", model.get("id")+"",model.get("agent_id")+"",userid,this);//代理分配发送邮件
				}catch (Exception e){
					resultType = new ResultType(0,"订单创建成功, 但是代理邮件发送失败,请注意!");
				}
				resultType = new ResultType(1,"订单创建成功,代理邮件发送成功");
			}
			renderJson(resultType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  String reSendReport(String oldOrderId,String newOrderNum,Integer userid){
		CreditOrderInfo m = CreditOrderInfo.dao.findFirst("select * from credit_order_info where id=?", oldOrderId);
		if(m==null){
			return "failure";
		}
		String orderNum=m.getStr("num");
		String errorMessage = "";
        Record record = new Record();
        record.set("statusCode", 1);
        record.set("message", "发送成功!");
        CreditOrderInfo newOrder = CreditOrderInfo.dao.findFirst("select * from credit_order_info where num=?", newOrderNum);
        String newOrderId = newOrder.get("id")+"";
        CreditOrderInfo model = CreditOrderInfo.dao.findFirst("select * from credit_order_info where num=?", orderNum);
        String orderId = model.get("id")+"";
        try{
            String speedLanguage = "s1.detail_name";
            String meataSql = "select report_type,report_language from credit_order_info  where num = ?";
            CreditOrderInfo metaOrder = CreditOrderInfo.dao.findFirst(meataSql, orderNum);
            String reportType = metaOrder.getStr("report_type");
            String report_language = metaOrder.getStr("report_language");
            if("EN".equals(ReportTypeCons.whichLanguage(reportType))) {
                speedLanguage += "_en";
            }
            //业务sql
            String sql = "select t.*,"+speedLanguage+" as speedName from credit_order_info t left join sys_dict_detail s1 on t.speed = s1.detail_id  where t.id = ?";
            CreditOrderInfo order = CreditOrderInfo.dao.findFirst(sql, orderId);
            //做新旧转化
            order.set("custom_id", newOrder.get("custom_id"));
            order.set("reference_num", newOrder.get("reference_num"));
            Map<String,Object> extend=new HashMap<String, Object>();
            extend.put("order_code", newOrderNum);
            if (ReportTypeCons.ROC_HY.equals(reportType) || ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
                //中文繁体+英文
                if ("217".equals(report_language)) {
                    if ("12".equals(reportType) || "14".equals(reportType)) {
                        Roc102.reportTable(order, reportType, "613", userid,extend);
                    }
                } else {
                    Roc102.reportTable(order, reportType, "612", userid,extend);
                }
            }else if (ReportTypeCons.BUSI_ZH.equals(reportType) ){
    			if ("213".equals(report_language)) {
    				BaseBusiCrdt.reportTable(order, "8", "612", userid);
    			}else{
    				BusiUtil.reportTableCh(order, userid);
    			}
    		}else if (ReportTypeCons.BUSI_EN.equals(reportType)){
    			if ("215".equals(report_language)) {
    				BaseBusiCrdt.reportTable(order, "9", "613", userid);
    			}else{
    				BusiUtil.reportTableEn(order, userid);
    			}
    		} else {
                if ("213".equals(report_language)) {
                    BaseBusiCrdt.reportTable(order, reportType, "612", userid,extend);
                } else if ("215".equals(report_language)) {
                    //英文
                    BaseBusiCrdt.reportTable(order, reportType, "613", userid,extend);
                } else if ("216".equals(report_language)) {
                    //中文简体+英文
                    if ("1".equals(reportType)) {
                        BaseBusiCrdt.reportTable(order, "1", "612", userid,extend);
                        BaseBusiCrdt.reportTable(order, "7", "613", userid,extend);
                    } else if ("8".equals(reportType)) {
						BaseBusiCrdt.reportTable(order, "8", "612", userid);
						//BaseBusiCrdt.reportTable(order, "9", "613", userid);
					} else if ("9".equals(reportType)) {
						// BaseBusiCrdt.reportTable(order, "8", "612", userid);
						BaseBusiCrdt.reportTable(order, "9", "613", userid);
					} else if ("10".equals(reportType)) {
                        BaseBusiCrdt.reportTable(order, "10", "612", userid,extend);
                        BaseBusiCrdt.reportTable(order, "11", "613", userid,extend);
                    } else {
                        BaseBusiCrdt.reportTable(order, reportType, "", userid,extend);
                    }
                }
            }
            //计算绩效
            try{
                OrderProcessController op = new OrderProcessController();
                op.getKpi(model,userid);
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch(Exception e){
                //日志输出
                e.printStackTrace();
                errorMessage = ReportInfoGetData.outPutErroLog(log, e);
                //报告发送失败,改变状态
                CreditOrderInfo tempModel = new CreditOrderInfo();
                int statusCode = 999;
                tempModel.set("id", orderId).set("status", statusCode).update();
                model.set("status", statusCode);
                record.clear().set("statusCode", 0).set("message", "递交订单发送报告时失败!请联系管理员或者再次发送!");
                CreditOrderFlow.addOneEntry(this, model.set("status",statusCode),errorMessage,true);
                //增加站内信
                ReportInfoGetData.sendErrMsg(model, userid,  "递交订单时失败!请联系管理员或者再次发送!");
                return "failure";
        }
        //发送成功修改状态
        //增加跟踪记录
      //报告发送成功,改变状态
        /*CreditOrderInfo tempModel = new CreditOrderInfo();
        int statusCode = 311;
        tempModel.set("id", orderId).set("status", statusCode).update();
        CreditOrderFlow.addOneEntry(this, model.set("status",statusCode),errorMessage,true);
        CreditOperationLog.dao.addOneEntry(userid, model, "订单递交/", "/credit/front/home/saveOrder");//操作日志记录
*/        return "success";
	}
	public int crateReportByOrder(Integer userid, CreditOrderInfo model,
			String id) {
		String language = model.get("report_language")+"";//报告语言
		String reprotType = model.get("report_type");//报告类型
		String infoLanguage = Db.queryInt("select info_language from credit_report_type where del_flag=0 and id="+reprotType)+"";//填报语言
		
		CreditCompanyInfo company = new CreditCompanyInfo();
		CreditCompanyInfo common = new CreditCompanyInfo();
		common.set("order_id", id);
		common.set("update_date", getNow());
		common.set("create_date", getNow());
		common.set("create_by", userid);
		common.set("update_by",userid);
		//如果之前有此订单，获取之前报告公司的工商信息。以省略企查查录入步骤
		CreditOrderInfo theSameOrder=CreditOrderInfo.dao.isTheSameOrder(model.get("right_company_name_en").toString(), model.get("report_type").toString(), model.get("report_language").toString(), this);
		
		String companyid="";
		if(null!=theSameOrder) {
			company=CreditCompanyInfo.dao.getCompanyById(theSameOrder.getStr("company_id").toString());
			companyid=company.get("id").toString();
			SysDictDetail d=SysDictDetail.dao.findById(company.get("company_type")+"");
			if(d!=null){
				if(ReportTypeCons.BUSI_EN.equals(reprotType)&&"216".equals(language)){
					company.set("type_of_enterprise_remark", d.get("detail_remark"));
				}
			}
		}
		
		company.remove("id");
		company.set("order_id", id);
		company.set("update_date", getNow());
		company.set("create_date", getNow());
		company.set("create_by", userid);
		company.set("update_by",userid);
		//company.set("name_en", model.get("right_company_name_en"));
		int companInfoId = -1;//填报语言对应的公司表id
		/** 报告语言
		 	213		中文简体
		    214	 	中文繁体  
			215	 	英文
			216	 	中文简体+英文
			217	 	中文繁体+英文 */
		/**自然语言
		   612	 	中文简体
		   613	 	英文
		   614	 	中文繁体*/
		
		//if(infoLanguage.equals("614")) { companInfoId = company.get("id");}
		if("214".equals(language)){
			company.set("sys_language", "614");
			company.remove("id").save();
			if(infoLanguage.equals("612")) {
				common.set("sys_language", "612"); //当填报语言为612时候则创建对应实体并保存对应实体id到orderInfo表中
				common.remove("id").save();
				companInfoId = common.get("id");
			}else if(infoLanguage.equals("613")) {//当填报语言为613时候则创建对应实体并保存对应实体id到orderInfo表中
				common.set("sys_language", "613");
				common.remove("id").save();
			    companInfoId = common.get("id");
			}else if(infoLanguage.equals("614")) {
				companInfoId = company.get("id");//当报告语言为214时默认公司id
			}
		}else if("215".equals(language)){
			if(ReportTypeCons.BUSI_EN.equals(reprotType) ||ReportTypeCons.BUSI_ZH.equals(reprotType)){
				company.set("sys_language", "612");
				company.remove("id").save();
				if(infoLanguage.equals("612")) { companInfoId = company.get("id");}
				common.set("sys_language", "613");common.remove("id").save();
				if(infoLanguage.equals("613")) { companInfoId = common.get("id");}
				if(infoLanguage.equals("614")) { common.set("sys_language", "614"); common.remove("id").save();companInfoId = common.get("id");}
			}else{
				company.set("sys_language", "613");
				company.remove("id").save();
				if(infoLanguage.equals("612")) {
					common.set("sys_language", "612"); //当填报语言为612时候则创建对应实体并保存对应实体id到orderInfo表中
					common.remove("id").save();
					companInfoId = common.get("id");
				}else if(infoLanguage.equals("613")) {//当填报语言为613时候则创建对应实体并保存对应实体id到orderInfo表中
					companInfoId = company.get("id");
				}else if(infoLanguage.equals("614")) {
					common.set("sys_language", "614"); //当填报语言为612时候则创建对应实体并保存对应实体id到orderInfo表中
					common.remove("id").save();
					companInfoId = common.get("id");//当报告语言为214时默认公司id
				}
			}
		}else if("216".equals(language)){
			if(ReportTypeCons.BUSI_EN.equals(reprotType) ){
				company.set("sys_language", "613");
				company.remove("id").save();
				companInfoId = company.get("id");
				common.set("sys_language", "612");common.remove("id").save();
				/*if(infoLanguage.equals("613")) { companInfoId = common.get("id");}
				if(infoLanguage.equals("614")) { common.set("sys_language", "614"); common.remove("id").save();companInfoId = common.get("id");}*/
			}else if(ReportTypeCons.BUSI_ZH.equals(reprotType)){
				company.set("sys_language", "612");
				company.remove("id").save();
				if(infoLanguage.equals("612")) { companInfoId = company.get("id");}
				common.set("sys_language", "613");common.remove("id").save();
				if(infoLanguage.equals("613")) { companInfoId = common.get("id");}
				if(infoLanguage.equals("614")) { common.set("sys_language", "614"); common.remove("id").save();companInfoId = common.get("id");}
			}else{
				company.set("sys_language", "612");
				company.remove("id").save();
				if(infoLanguage.equals("612")) { companInfoId = company.get("id");}
				common.set("sys_language", "613");common.remove("id").save();
				if(infoLanguage.equals("613")) { companInfoId = common.get("id");}
				if(infoLanguage.equals("614")) { common.set("sys_language", "614"); common.remove("id").save();companInfoId = common.get("id");}
			}
		}else if("217".equals(language)){
			company.set("sys_language", "614");
			company.remove("id").save();
			if(infoLanguage.equals("614")) { companInfoId = company.get("id");}
			common.set("sys_language", "613");common.remove("id").save();
			if(infoLanguage.equals("613")){companInfoId = common.get("id");}
			if(infoLanguage.equals("612")) { common.set("sys_language", "612"); common.remove("id").save();companInfoId = common.get("id");}
		}else if("213".equals(language)){
			if(ReportTypeCons.BUSI_EN.equals(reprotType) ||ReportTypeCons.BUSI_ZH.equals(reprotType)){
				company.set("sys_language", "612");
				company.remove("id");
				company.save();
				companInfoId = company.get("id");
				common.set("sys_language", "613"); common.remove("id").save();  
			}else{
				company.set("sys_language", "612");
				company.remove("id");
				company.save();
				if(infoLanguage.equals("612")) { companInfoId = company.get("id");}
				if(infoLanguage.equals("613")) { common.set("sys_language", "613"); common.remove("id").save();  companInfoId = common.get("id");}
				if(infoLanguage.equals("614")) { common.set("sys_language", "614"); common.remove("id").save();  companInfoId = common.get("id");}
			}
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(companyid)) {
			//引用之前的报告，就无需企查查接口，降低成本
			if(ReportTypeCons.BUSI_EN.equals(reprotType)&&"216".equals(language)){
				Thread td = new Thread(new threadEnterGrabTheSameCompany(companyid, reprotType, "613"));
				td.start();
			}else{
				Thread td = new Thread(new threadEnterGrabTheSameCompany(companyid, reprotType, "612"));
				td.start();
			}
		
		}

		return companInfoId;
	}
	class threadEnterGrabTheSameCompany implements Runnable{
		String companyId = "";
	    String reporttype = "";
	    String sys_language="";
		public threadEnterGrabTheSameCompany(String companyId, String reporttype, String sys_language) {
			super();
			this.companyId = companyId;
			this.reporttype = reporttype;
			this.sys_language = sys_language;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			new CompanyService().enterGrabTheSameCompany(companyId,sys_language,reporttype);
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
		String  ids = getPara("ids");//批量撤销ids
		String revoke_reason=getPara("revoke_reason");
		try {
			if(StringUtils.isNotBlank(ids)){
	      String id2 []=ids.split(",");
	      for (String id3 : id2) {
	    	//根据id查找订单
	  		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id3);
	  		//更新订单
	  		coi.set("status","313");
	  		coi.set("revoke_reason", revoke_reason);
	  		//保存订单
	  		coi.update();
		   }
	    }else{
		//根据id查找订单
		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id);
		//更新订单
		coi.set("status","313");
		coi.set("revoke_reason", revoke_reason);
		//保存订单
		coi.update();
	    }
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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//更新订单信息
		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id);
		coi.set("update_reason", update_reason);
		coi.set("status", "293");
		//获取订单编号
		String num=coi.get("num");
		//保存上传文件
		CreditUploadFileModel model= new CreditUploadFileModel();
		model.set("business_type", "0");
		model.set("business_id", num);
		
		
		//获取订单记录
		CreditOrderFlow cof=new CreditOrderFlow();
		//订单号
		cof.set("order_num", num);
		//订单状态
		cof.set("order_state", "293");
		//操作人
		cof.set("create_oper", getSessionUser().getUserid());
		//操作时间
		cof.set("create_time", sdf.format(new Date()));
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
			
			if (uploadFile != null /*&& uploadFile.getFile().length()<=maxPostSize */&& FileTypeUtils.checkType(ext)) {
				
				String storePath = ftp_store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
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
					pdf = Office2PDF.excelPdfNew(uploadFile);
					//pdf = Office2PDF.toPdf(uploadFile);
					pdf_FTPfileName=now+"."+"pdf";
					if(pdf!=null)
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
			//内容更新会删除以前除了订单分配外的所有记录节点
			CreditOrderFlow.dao.del(coi.getStr("num"));
			coi.update();
			cof.save();
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

	//打桩机
	static void toString(CreditOrderInfo model,int lineNum) {
		System.out.println("代码第"+lineNum+"行:");
		System.out.println("report_type========="+(String)model.get("report_type"));
		System.out.println("continent========="+(String)model.get("continent"));
		System.out.println("country========="+(String)model.get("country"));
		System.out.println("speed========="+(String)model.get("speed"));
		System.out.println("order_type========="+(String)model.get("order_type"));
		System.out.println("id========="+ model.get("id")+"");
		System.out.println("country========="+(String)model.get("country"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

