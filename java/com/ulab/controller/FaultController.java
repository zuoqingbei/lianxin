package com.ulab.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.job.TestQuartzJobOne;
import com.ulab.model.FaultModel;
@ControllerBind(controllerKey="/fault",viewPath="/phm")
public class FaultController extends BaseController{
	//转发到service页面
	public void service(){
		render("service.html");
	}

	/*
	 * @author chen xin
	 * 根据传入的page pageSize 查询设备信息
	 */
	public void findPageFaultInfo(){
		int page=Integer.parseInt(getPara("page"));
		int pageSize=Integer.parseInt(getPara("pageSize"));
		String fault_name=getPara("fault_name");
		List<Record> list=FaultModel.dao.findPageFaultInfo(page, pageSize, fault_name);
		renderJson(list);
	}
	/*
	 * @ahthor chen xin
	 * 根据传入的 fault_name 进行 筛选查询 
	 * 注意 此时的fault_name是故障现象 
	 */
	public void findDeviceInfoByDate() {
		String fault_name=getPara("fault_name");
		int[] months= {1,3,6,12,12*2,12*3};//3年以上未添加
		List<Integer> data=new ArrayList<>();
		for(int i=0;i<months.length;i++) {
			Date nowDate=new Date();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(nowDate);
			calendar.add(Calendar.MONTH, -months[i]);//将月份相减到指定月份
			Date start=calendar.getTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime=sdf.format(start);
			String endTime=sdf.format(nowDate);
			Integer num=FaultModel.dao.deviceByDate(startTime, endTime, fault_name);
			data.add(num);
		}
		Integer num=FaultModel.dao.deviceByDate("", "", fault_name);//查询所有的 这里指3年以上
		data.add(num);
		renderJson(data);
	}
	public void quart(){
		TestQuartzJobOne.Device();
		renderNull();
	}
}
