package com.ulab.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.model.FaultModel;
import com.ulab.model.PhmDeviceInfoModel;
import com.ulab.util.HttpClientUtil;
import com.ulab.util.JsonUtils;
/**
 * 
 * @time   2017年5月25日 上午10:41:40
 * @author zuoqb
 * @todo   定时器测试
 * 新增的定时器需要在quartz.properties进行配置
 */
public class TestQuartzJobOne implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		 System.out.println("我是定时任务 job one " + new Date());
		 synchronized(TestQuartzJobOne.class){
			 
			 Device();
		 }
	}
	
	/*********************************************************/
    public static void Device() {
		List<Record> list=PhmDeviceInfoModel.dao.getAllDevice();
		List<Record> FaultList=FaultModel.dao.findAllFaultInfo();
		List<String> sncodeList=new ArrayList<>();
		for(int i=0;i<FaultList.size();i++) {//遍历先有的 错误表中的已存在的sncode
			Record record=FaultList.get(i);
			sncodeList.add(record.getStr("sncode"));
		}
		for(int i=0;i<list.size();i++) {
			String sn=list.get(i).getStr("SNCODE");
			String info=HttpClientUtil.sendGetRequest("http://localhost:8088/api/yzd/product/"+sn+"/diagnosisResult","UTF-8");
			JSONArray jsonArray=(JSONArray) JSONObject.parse(info);
			if(sncodeList.contains(sn)) {
				FaultModel.dao.deleteFaultBySncode(sn);
			}else {
				sncodeList.add(sn);
			}
			List<String>sqlList=new ArrayList<>();
			for(int j=0;j<jsonArray.size();j++) {
				Map mp=(Map) jsonArray.get(j);
				String time=(String) mp.get("time");//故障发生时间
				String name=(String) mp.get("fault");//故障名字
				JSONArray advice=(JSONArray) mp.get("advice");
				String adviceString=""; //TO_DATE('"+f_date+"','yyyy-MM-dd HH24:mi:ss')
				 for(int k=0;k<advice.size();k++) {
					 if(k==0) {
						 adviceString+=advice.get(k);
					 }else {
						 adviceString+=","+advice.get(k);
					 }
				 }                    
				String sql="insert into phm_fault2 (sncode,fault_name,fault_repair,fault_time)values('"+sn+"','"+name+"','"+adviceString+"',TO_DATE('"+time+"','yyyy-MM-dd HH24:mi:ss'))";
				sqlList.add(sql);
			}
			FaultModel.dao.addFault(sqlList);
			
		}
		
		
	}
	
	

}
