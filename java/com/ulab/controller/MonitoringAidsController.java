package com.ulab.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.MonitoringAidsModel;
@ControllerBind(controllerKey="/MonitoringAids",viewPath="/MonitoringAids")
@Before({GlobalInterceptor.class})
public class MonitoringAidsController extends BaseController {
	//public static final MonitoringAidsModel dao=new  MonitoringAidsModel();
	public void MonitoringAidsInfo(){
		//List<Record> mList=MonitoringAidsModel
	}
}
