package com.hailian.modules.credit.usercenter.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.model.ModuleJsonData;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.jfinal.plugin.activerecord.Record;
@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/getmodule")
public class ModuleController extends BaseProjectController{
	
	@ApiOperation(url = "/credit/front/getmodule/list",httpMethod="post", 
			description = "获取模板")
	@Params(value = { 
		@Param(name = "id", description = "报告id", required = false, dataType = "String"),
		})
	/**
	 * 
	 * @time   2018年10月26日 上午10:33:16
	 * @author yangdong
	 * @todo   TODO返回公司信息,订单信息,和模板信息,其中模板信息是由外层集合内层集合组成,外层集合modules是由多个ModuleJsonData对象组成,
	 * ModuleJsonData对象是由一个父模板名称title和一个包含多个子模板的集合组成获取父模板名称data.modules[i].title获取子模板集合data.modules[i].contents
	 * 获取某个子模板名称data.modules[i].contents[i].name
	 * @param  
	 * @return_type   void
	 */
	public void list() {
		//com.hailian.modules.credit.usercenter.controller.finance.FinanceService.getFinancialConfig();
		//订单id
		String orederid = getPara("id");
		//获取报告类型
		String reportType = getPara("reportType");
		//根据订单id获取订单信息
		CreditOrderInfo coi = CreditOrderInfo.dao.findById(orederid);
		if(coi==null) {
			renderJson(new ResultType(0, "无此订单信息!"));
			return;
		}
		//根据订单信息获取公司信息
		CreditCompanyInfo cci = CreditCompanyInfo.dao.findById(Arrays.asList(new String[]{coi.get("company_id")}));
		//找到当前报告类型下的父节点
		List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
		List<ModuleJsonData> list = new ArrayList<ModuleJsonData>();
		//获取默认模板
		List<CreditReportModuleConf> defaultModule = CreditReportModuleConf.dao.getDefaultModule(reportType);
		//获取带锚点模板
		List<CreditReportModuleConf> tabFixed = CreditReportModuleConf.dao.getTabFixed(reportType);
		double start = new Date().getTime();
		//defaultModule.forEach((CreditReportModuleConf model)->{model.removeNullValueAttrs().remove("del_flag");});
		for(CreditReportModuleConf crmc:crmcs) {
			//找到当前父节点下的子节点
			List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(crmc.get("id").toString(),reportType);
			list.add(new ModuleJsonData(crmc,child,crmc.getStr("small_module_type")));
		}
		System.out.println("运行时间===================================="+(double)(new Date().getTime()-start));
		
		Record record = new Record();
		record.set("defaultModule",defaultModule);
		record.set("modules",list);
		record.set("tabFixed",tabFixed);
		renderJson(record);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
