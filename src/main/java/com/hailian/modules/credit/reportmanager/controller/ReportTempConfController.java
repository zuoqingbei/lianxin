package com.hailian.modules.credit.reportmanager.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
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
		list();
	}
	/**
	 * 
	 * @time   2018年10月22日 上午11:37:38
	 * @author yangdong
	 * @todo   TODO报告模板列表展示
	 * @param  
	 * @return_type   void
	 */
	public void list() {
		//接收查询条件
		CreditReportTempConf temp=getModelByAttr(CreditReportTempConf.class);
		//获取分页对象
		Paginator pageinator=getPaginator();
		//查询数据集合
		Page<CreditReportTempConf> page=CreditReportTempConf.dao.findTemps(temp,pageinator,this);
		//获取全部磨板类型
		List<CreditReportTempConf> crtf=CreditReportTempConf.dao.getAllTemp();
		setAttr("page",page);
		setAttr("crtf",crtf);
		setAttr("model",temp);
		render(path+"list.html");
	}
	/**
	 * 
	 * @time   2018年10月22日 下午4:13:22
	 * @author yangdong
	 * @todo   TODO 跳转到新增或者修改页面
	 * @param  
	 * @return_type   void
	 */
	public void add() {
		String id=getPara("id").toString();
		if(StringUtils.isNotBlank(id)) {
			CreditReportTempConf model=CreditReportTempConf.dao.findById(id);
			setAttr("model",model);
			render(path+"edit.html");
		}else {
			render(path+"add.html");
		}
	}
}
