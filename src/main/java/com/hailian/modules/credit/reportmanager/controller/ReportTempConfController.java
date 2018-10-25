package com.hailian.modules.credit.reportmanager.controller;

import java.util.Date;
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
	 * @todo   TODO 跳转到新增模板页面
	 * @param  
	 * @return_type   void
	 */
	public void add() {
		String id=getPara("id").toString();
		if(StringUtils.isNotBlank(id)) {
			CreditReportTempConf model=CreditReportTempConf.dao.findById(id);
			CreditReportTempConf model1=new CreditReportTempConf();
			//区分一级菜单二级菜单三级菜单
			String nodeType=model.getStr("node_type");
			//新建一级目录
			if("0".equals(nodeType)) {

				model1.set("node_type", "0");
				model1.set("parent_temp", "999");
				model1.set("create", getSessionUser().getUserid());
				setAttr("model1",model1);
				render(path+"add.html");
				}		
			if("1".equals(nodeType)) {

				model1.set("node_type", "1");
				model1.set("parent_temp", model.getStr("parent_temp"));
				model1.set("create", getSessionUser().getUserid());
				model1.set("report", model.getStr("report"));
				setAttr("model1",model1);
				render(path+"add.html");			
				}
			//三级目录
			if("2".equals(nodeType)) {
			
				model1.set("node_type", "2");
				model1.set("parent_temp", model.getStr("parent_temp"));
				model1.set("create", getSessionUser().getUserid());
				model1.set("report", model.getStr("report"));
				setAttr("model1",model1);
				render(path+"add.html");
				}
		
		}
	}
	/**
	 * 
	 * @time   2018年10月25日 上午9:24:16
	 * @author yangdong
	 * @todo   TODO 跳转到修改模板页面
	 * @param  
	 * @return_type   void
	 */
	public void edit() {
		String id=getPara("id").toString();
		if(StringUtils.isNotBlank(id)) {
			CreditReportTempConf model=CreditReportTempConf.dao.findById(id);
				setAttr("model",model);
				render(path+"edit.html");
		}
	}
	/**
	 * 
	 * @time   2018年10月25日 上午9:28:17
	 * @author yangdong
	 * @todo   TODO 修改保存
	 * @param  
	 * @return_type   void
	 */
	public void save2() {
		CreditReportTempConf model=getModelByAttr(CreditReportTempConf.class);
		model.set("update_date", new Date());
		model.set("update", getSessionUser().getUserid());
		/*if(model.get("report")) {
			
		}*/
		try {
		model.update();
		renderMessage("修改成功");
		}catch(Exception e){
		e.printStackTrace();
		renderMessageByFailed("修改失败请重新修改");
		}
		
		}
	/**
	 * 
	 * @time   2018年10月23日 下午5:31:08
	 * @author yangdong
	 * @todo   TODO 新建模板
	 * @param  
	 * @return_type   void
	 */
	public void save() {
		CreditReportTempConf model=getModelByAttr(CreditReportTempConf.class);
		model.set("create_date", new Date());
		/*if(model.get("report")) {
			
		}*/
		try {
		model.save();
		renderMessage("创建成功");
		}catch(Exception e){
		e.printStackTrace();
		renderMessageByFailed("创建失败请重新创建");
		}
		
		}
	public void delete() {
		String id=getPara("id").toString();
		CreditReportTempConf model=CreditReportTempConf.dao.findById(id);
		model.set("del_flag", "1");
		try {
			model.update();
			renderMessageByFailed("删除成功");
		}catch(Exception e) {
			e.printStackTrace();
			renderMessageByFailed("删除失败");
		}
	}
}