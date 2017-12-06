package com.ulab.controller;

import java.util.List;
import java.util.Map;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.DeviceInfoModel;

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
	//转发到strategy.html 系统首页
	public void strategy(){
		render("strategy.html");
	}
	/**
	 * @author chenxin
	 * @return List<List<Record>>
	 * 根据status分别查询处四种状态的集合
	 */
	public void findDeviceInfoStatusGroup(){
		Map<String,List<Record>>mp=DeviceInfoModel.dao.findDeviceInfoStatusGroup();
		renderJson(mp);
	}
}
