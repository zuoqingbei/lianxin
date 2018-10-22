package com.hailian.modules.credit.reportmanager.controller;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportTempConf;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className ReportTempConfController.java
 * @time   2018年10月22日 上午11:36:56
 * @author yangdong
 * @todo   TODO 报告模板管理
 */
@Api(tag = "报告模板", description = "操作报告模板")
@ControllerBind(controllerKey = "/credit/reporttempmanager")
public class ReportTempConfController extends BaseProjectController{
	private static final String path = "/pages/credit/reportmanager/reporttemp_";
	
	public void index() {
		List();
	}
	/**
	 * 
	 * @time   2018年10月22日 上午11:37:38
	 * @author yangdong
	 * @todo   TODO报告模板列表展示
	 * @param  
	 * @return_type   void
	 */
	public void List() {
		//接收查询条件
		CreditReportTempConf temp=getModelByAttr(CreditReportTempConf.class);
		//获取分页对象
		Paginator pageinator=getPaginator();
		//查询数据集合
		Page<CreditReportTempConf> page=CreditReportTempConf.dao.findTemps(temp,pageinator,this);
		setAttr("page",page);
		render(path+"list.html");
	}
}
