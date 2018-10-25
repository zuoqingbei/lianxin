package com.hailian.modules.credit.usercenter.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/getmodule")
public class ModuleController extends BaseProjectController{
	
	@ApiOperation(url = "/credit/front/getmodule/list",httpMethod="post", 
			description = "获取模板")
	@Params(value = { 
		@Param(name = "id", description = "报告id", required = false, dataType = "String"),
		})
	public void list() {
		String report=getPara("id");
		List<CreditReportModuleConf> crmc=CreditReportModuleConf.dao.findReportNodes(report);
		renderJson(crmc);
	}
	

}
