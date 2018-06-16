package com.ulab.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.DeviceInfoModel;
import com.ulab.model.DeviceUseAnalysisModel;
import com.ulab.model.FaultModel;
/**
 * 
 * @time   2017年12月6日 下午2:17:03
 * @author zuoqb
 * @todo   PHM数据请求
 */
@ControllerBind(controllerKey="/data",viewPath="/phm")
public class PhmAjaxController extends BaseController{
	/**
	 * 
	 * @time   2017年12月6日 下午2:52:31
	 * @author zuoqb
	 * @todo    售后页面-查询设备信息
	 * @param  
	 * @return_type   void
	 */
	public void findPageFaultInfo(){
		int page=Integer.parseInt(getPara("page"));
		int pageSize=Integer.parseInt(getPara("pageSize"));
		String fault_name=getPara("fault_name");
		List<Record> list=FaultModel.dao.findPageFaultInfo(page, pageSize, fault_name);
		renderJson(list);
	}
	/**
	 * 
	 * @time   2017年12月6日 下午2:52:39
	 * @author zuoqb
	 * @todo    * 根据传入的 fault_name 进行 筛选查询 
	 * 注意 此时的fault_name是故障现象 
	 * 售后页面-不同时间段售后信息统计
	 * @param  
	 * @return_type   void
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
	/**
	 * 
	 * @time   2017年12月6日 下午2:38:00
	 * @author zuoqb
	 * @todo  实时状态页面-使用分析
	 * @param  
	 * @return_type   void
	 */
	public void getDeviceUseAnalysisByDeviceSncode(){
		String device_info_sncode=getPara("device_info_sncode");//设备编码
		List<Record> record=DeviceUseAnalysisModel.dao.getDeviceUseAnalysisByDeviceSncode(device_info_sncode);
		renderJson(record);
	}
	/**
	 * @author chenxin
	 * @return List<List<Record>>
	 * 实时状态页面-展示中国地图上设备点状态信息
	 */
	public void findDeviceInfoStatusGroup(){
		Map<String,List<Record>>mp=DeviceInfoModel.dao.findDeviceInfoStatusGroup();
		renderJson(mp);
	}
	/**
	 * 
	 * @time   2017年12月6日 下午3:03:11
	 * @author zuoqb
	 * @todo   定时任务更新设备故障信息数据
	 * @param  
	 * @return_type   void
	 */
	public void quart(){
		FaultModel.dao.synchroFaultInfo();
		renderNull();
	}
}
