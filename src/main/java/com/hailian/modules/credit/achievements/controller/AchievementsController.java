package com.hailian.modules.credit.achievements.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.user.SysUser;
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
		SysUser user= (SysUser) getSessionUser();
		boolean isadmin=isAdmin(user);
		Integer userid = getSessionUser().getUserid();
		if(isadmin){
			userid=null;
		}
		List<SysUser> listDetail = SysUser.dao.getSysUser(userid);
		renderJson(listDetail);
	}
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
	/**
	 * @Description: 绩效导出
	* @author: dsh 
	* @date:  2018年11月23日
	 */
	public void AchievementsExport() {
		String fileName="订单绩效-"+sdf.format(new Date())+".xlsx";
		String time = getPara("time");
		String reportername=getPara("reportername");
		SysUser user= (SysUser) getSessionUser();
		boolean isadmin=isAdmin(user);
		String userid = getSessionUser().getUserid()+"";
		if(isadmin){
			userid="";
		}
	    List<CreditOrderInfo> infos  = OrderManagerService.service.exportAchievements(reportername,time,userid,this);
	    com.hailian.util.AchievementsExport AchievementsExport=new com.hailian.util.AchievementsExport(infos);
		 try {
			 fileName=new String(fileName.getBytes("GBK"), "ISO-8859-1");
			 AchievementsExport.doExport(getResponse(), fileName);
		renderJson("导出成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson("导出失败");
		}
	}
	
}
