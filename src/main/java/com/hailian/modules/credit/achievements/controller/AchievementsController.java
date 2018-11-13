package com.hailian.modules.credit.achievements.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.JFlyFoxUtils;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.system.user.SysUser;
import com.hailian.util.ehcache.EhCacheUtil;
import com.jfinal.plugin.activerecord.Page;
/**
 * 前台绩效
* @author doushuihai  
* @date 2018年11月10日上午11:03:00  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/achievements")
public class AchievementsController extends BaseProjectController{
	private static Logger logger=Logger.getLogger(AchievementsController.class);
	private static final String path = "/pages/credit/usercenter/total_manage/";
	public void index() {
		render(path+"report_credit_analysis.html");
	}
	public void list() throws ParseException{
		CreditOrderInfo model = new CreditOrderInfo();
		
		String time = getPara("end_date");
		String sortname=getPara("sortName");
		String reportername=getPara("reportername");
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
		boolean isadmin=isAdmin(user);
		
		Paginator pageinator=getPaginator();
		Page<CreditOrderInfo> page=OrderManagerService.service.getAchievementsOrders(pageinator,model,reportername,time,user,isadmin,sortname,sortorder);
		int total= page.getTotalRow();
		List<CreditOrderInfo> rows=page.getList();
		ResultType resultType=new ResultType(total,rows);
		renderJson(resultType);
	}
	public void getUserSelect(){
		/*
		 * 获取系统用户
		 */
		List<SysUser> listDetail = SysUser.dao.getSysUser(null);
		renderJson(listDetail);
	}
	
}
