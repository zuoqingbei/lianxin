package com.hailian.modules.credit.usercenter.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.front.template.TemplateDictService;
import com.jfinal.plugin.activerecord.Record;
@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController extends BaseProjectController implements ReportInfoGetDataInterface {
	TemplateDictService template = new TemplateDictService();
	@Override
	public void getBootStrapTable() {
		// TODO Auto-generated method stub

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
