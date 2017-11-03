package com.ulab.controller;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.Random;


import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.DeviceInfoModel;
import com.ulab.model.DicModel;
import com.ulab.model.LabMapModel;
//import com.ulab.model.LabModel;
import com.ulab.model.ProviderDicModel;
//import com.ulab.util.JsonUtils;
import com.ulab.util.SqlUtil;

/**
 * @author chen xin
 */
@ControllerBind(controllerKey = "/deviceInfo", viewPath = "/phm")
public class DeviceInfoController extends BaseController {
	//查询商品各个名字数量
	public void findDeviceKindNumber(){
		List<Record>list=DeviceInfoModel.dao.findDeviceKindNumber();
		renderJson(list);
	}
	//根据商品sn查询信息
	public void findDeviceInfoById(){
		String sn=getPara("sn");
		Record record=DeviceInfoModel.dao.findDeviceInfoById(sn);
		renderJson(record);
	}

}
