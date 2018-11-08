package com.hailian.modules.credit.usercenter.controller;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.front.template.TemplateDictService;
import com.jfinal.plugin.activerecord.Record;
@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController extends BaseProjectController implements ReportInfoGetDataInterface {
	TemplateDictService template = new TemplateDictService();
	public void getBootStrapTable() {
		String companyId = getPara("companyId");
		String tableName = getPara("tableName","");
		List<Object> params = new ArrayList<>();
		List rows = null;
		params.add(companyId);
		//http://localhost:8080/credit/front/ReportGetData/getBootStrapTable/?companyId=24&tableName=credit_company_his&className=CreditCompanyHis
		try {
			Class<?> table = Class.forName("com.hailian.modules.admin.ordermanager.model."+getPara("className"));
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
		 rows = model.find("select * from "+tableName+" where company_id=? and del_flag=0",params.toArray());
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Record record = new Record();
		renderJson(record.set("rows", rows));
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
