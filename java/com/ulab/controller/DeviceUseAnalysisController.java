package com.ulab.controller;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.Random;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.DeviceUseAnalysisModel;
//import com.ulab.model.LabModel;
//import com.ulab.util.JsonUtils;

/**
 * @author chen xin
 */
@ControllerBind(controllerKey = "/deviceUseAnalysis", viewPath = "/phm")
public class DeviceUseAnalysisController extends BaseController {
	//跟据商品使用分析表所关联的phm_device_info sn查询该商品使用信息
	public void getDeviceUseAnalysisByDeviceSncode(){
		String device_info_sncode=getPara("device_info_sncode");
		List<Record> record=DeviceUseAnalysisModel.dao.getDeviceUseAnalysisByDeviceSncode(device_info_sncode);
		renderJson(record);
	}

}
