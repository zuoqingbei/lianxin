package com.ulab.controller;

import java.util.List;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.UsageAnalysisModel;
@ControllerBind(controllerKey="/usage",viewPath="/phm")
public class UsageAnalysisController extends BaseController {
		
	public void UsageAnalysisInfo(){
		Integer id=Integer.parseInt(getPara("mId","1"));
		List<Record> uList=UsageAnalysisModel.dao.showUsageAnalysis(id);
		renderJson(uList);
	}
}
