package com.ulab.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.math.BigDecimal;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.FaultModel;
@ControllerBind(controllerKey="/fault",viewPath="/phm")
public class FaultController extends BaseController{
	/**
	 * 故障页面，左侧表格中的数据
	 */
	public void faultInfo(){
//		Integer pageNumber =Integer.parseInt(getPara("pageNumber"));
//		Integer pageSize=Integer.parseInt(getPara("pageSize"));
		Page<Record> tList=FaultModel.dao.ShowFault( 1, 10);
		//setAttr("tList", tList);
		renderJson(tList);
		//render("fault.html");
	}
	/**
	 * 显示工贸相关信息
	 */
	public void gongMaoInfo(){
		String fQuyu = getPara("fQuyu");
		String fObject=getPara("fObject");
		List<Record> r=FaultModel.dao.showFaultGongmao(fObject, fQuyu);
		setAttr("r",r);
		//renderJson(r);
		render("page2.html");
	}
	/**
	 * 显示大区域压缩机需求信息
	 */
	public void quYuInfo(){
		render("page2.html");
	}
	public void quYuInfoDataAjax(){
		String fObject=getPara("fObject");
		List<Record> qList=FaultModel.dao.showFaultQuyu(fObject);
		renderJson(qList);
	}
	/**
	 * 显示不同时间段压缩机的需求信息
	 * @param month
	 */
	/*public void DemandInfo(){
		
	}*/
	public void DemandInfoDataAjax(){
		Integer [] months={1,3,6,12,12*2,12*3};
		String fObject=getPara("fObject","主控板");
		List<Record> dList=FaultModel.dao.findFaultDemand(fObject);
		List<Record> finalList=new ArrayList<Record>();
		for(Integer month:months){
			
			Date dNow = new Date();   //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历
			calendar.add(calendar.MONTH, -month);  //设置为前3月
			dBefore = calendar.getTime();   //得到前3月的时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
			String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
			String defaultEndDate = sdf.format(dNow); //格式化当前时间
			//System.out.println("前3个月的时间是：" + defaultStartDate);
			//System.out.println("生成的时间是：" + defaultEndDate);
			Record cRecord=null;
			for (Record demand:dList) {
				if(defaultStartDate.equals(demand.getStr("name"))){
					cRecord=demand;
					break;
				}
			}
			if(cRecord==null){
				//取最后一个  比较当前时间差
				Record r=dList.get(dList.size()-1);
				try {
					if(sdf.parse(r.getStr("name")).getTime()-sdf.parse(defaultStartDate).getTime()>=0){
						cRecord=new Record();
						if(month==1){
							defaultStartDate="一个月";
						}else if(month==3){
							defaultStartDate="三个月";
						}else if(month==6){
							defaultStartDate="半年";
						}else if(month==12){
							defaultStartDate="一年";
						}else if(month==12*2){
							defaultStartDate="两年";
						}else {
							defaultStartDate="三年以上";
						}
						cRecord.set("name", defaultStartDate);
						
						cRecord.set("count", r.get("count"));
					}else{
						if(month==1){
							defaultStartDate="一个月";
						}else if(month==3){
							defaultStartDate="三个月";
						}else if(month==6){
							defaultStartDate="半年";
						}else if(month==12){
							defaultStartDate="一年";
						}else if(month==12*2){
							defaultStartDate="两年";
						}else {
							defaultStartDate="三年以上";
						}
						cRecord=new Record();
						cRecord.set("name", defaultStartDate);
						cRecord.set("count", 0);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			finalList.add(cRecord);
		}
		renderJson(finalList);
	}
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
}
