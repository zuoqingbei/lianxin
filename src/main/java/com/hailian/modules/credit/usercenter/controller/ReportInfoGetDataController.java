package com.hailian.modules.credit.usercenter.controller;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.front.template.TemplateDictService;
import com.jfinal.plugin.activerecord.Record;
@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController extends BaseProjectController implements ReportInfoGetDataInterface {
	private TemplateDictService template = new TemplateDictService();
	
	/**
	 * 获取bootstraptable类型的数据
	 * 链接形如: http://localhost:8080/credit/front/ReportGetData/getBootStrapTable?conf_id=18
	 * company_id=24&report_type=1&tableName=credit_company_his&className=CreditCompanyHis
	 */
	public void getBootStrapTable() {
		Record record = new Record();
		String tableName = getPara("tableName","");
		String className = getPara("className");
		String confId = getPara("conf_id","");
		//解析实体获取required参数
		CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
		String getSource = confModel.getStr("get_source");
		String[] requireds = getSource.split("\\*");
		String[] required = requireds[1].split("\\$");
		StringBuffer sqlSuf = new StringBuffer();
		for (String str : required) {
			sqlSuf.append(str.trim()+"="+getPara(str).trim()+" and ");
		}
		if(sqlSuf.length()<1){
			renderJson(record.set("rows", null));
			return;
		}
		List rows = null;
		try {
			Class<?> table = Class.forName("com.hailian.modules.admin.ordermanager.model."+className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find("select * from "+tableName+" where del_flag=0 and "+sqlSuf+" 1=1");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		renderJson(record.set("rows", rows));
		renderJson(record.set("total", rows.size()));
	}

	@Override
	public void getForm() {
		// TODO Auto-generated method stub

	}

	public void getSelete() {
	 String selectStr = template.getSysDictDetailString3(getPara("type"), getPara("selectedId"), getPara("disPalyCol"));
	 renderJson(new Record().set("selectStr", selectStr));
	}
///credit/front/ReportGetData?type=registration_status&selectedId=596&disPalyCol=registration_status
}
