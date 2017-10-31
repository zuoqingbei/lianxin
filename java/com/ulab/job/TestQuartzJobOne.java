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
			 
			 monitorDevice();
		 }
	}
	
	/**
	 * 实时监控、设备指标
	 */
	public void monitorDevice(){
		List<Record> list=PhmDeviceInfoModel.dao.getAllDevice();
		List<Record> FaultList=FaultModel.dao.findAllFaultInfo();
		Map<String,List<String>> doubleId=new HashMap<>();
		//将product_id作为key,f_id 的list集合作为value
		//先将Record中的id 的column集合取出
		for(int i=0;i<FaultList.size();i++ ){
			Record record=FaultList.get(i);
//			doubleId.put(record.getStr("product_id"), record.getStr("f_id"));
			String product_id=record.getStr("product_id");
			String f_id=record.getStr("f_id");
				if(!doubleId.containsKey(product_id))
				doubleId.put(product_id, new ArrayList<String>());
				doubleId.get(product_id).add(f_id);
			
		}
		 for(Record record:list){
			 String s=HttpClientUtil.sendGetRequest("http://localhost:8088/api/yzd/product/"+record.getStr("SNCODE")+"/diagnosisResult","UTF-8");
//			 Object obj= JSONObject.parse(s); 
			 JSONArray jsonObject=(JSONArray) JSONObject.parse(s);
			 for(int i=0;i<jsonObject.size();i++){
				 Map mp=(Map) jsonObject.get(i);
				 String sn=(String) mp.get("sn");//设备代号
				 String product_id=(String) mp.get("_id");//产品id
				 String f_sb_number=(String) mp.get("productType");//设备型号
				 JSONArray failureMeta=  (JSONArray) mp.get("failureMeta");
				 String fault_date=(String) mp.get("time");//故障出现时间
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				 	
				 Date fdate=null;
				try {
					fdate = sdf.parse(fault_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String  f_date= sdf2.format(fdate);
			
				 String f_xx_bianma=(String) (mp.get("code").toString());//故障现象编码
				 	for(int j=0;j<failureMeta.size();j++){
				 		Map failureSingle=(Map) failureMeta.get(j);
				 		String id=(String) failureSingle.get("_id");//故障ID
				 		String f_yy_bianma=(String) failureSingle.get("fmCode");//故障编号（故障原因编码）
				 		String f_xx_miaoshu=(String) failureSingle.get("name");//故障现象描述
				 		JSONArray part=(JSONArray) failureSingle.get("part");
//				 		[{"code":"A-1","name":"组成1"}]
				 		String f_object="";
				 		if(part.size()>0){
				 			f_object=(String) ((Map)part.get(0)).get("name");
				 		}
//				 		String f_object=(String) part.get("name");//故障对象
				 		if(doubleId.containsKey(product_id)){
				 			if(doubleId.get(product_id).contains(id)){
				 				FaultModel.dao.updateFault(id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,"故障原因描述","尾号","维修措施","责任类别",f_date,"故障出现大区域","故障所属工贸",f_sb_number,sn,product_id);
				 			}else{
				 				FaultModel.dao.insertFault(id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,"故障原因描述","尾号","维修措施","责任类别",f_date,"故障出现大区域","故障所属工贸",f_sb_number,sn,product_id);
				 				doubleId.get(product_id).add(id);
				 			}
				 		}else{
				 			doubleId.put(product_id, new ArrayList<String>());
				 			FaultModel.dao.insertFault(id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,"故障原因描述","尾号","维修措施","责任类别",f_date,"故障出现大区域","故障所属工贸",f_sb_number,sn,product_id);
			 				doubleId.get(product_id).add(id);
				 		}
				 		/*if(FaultIdList.contains(id)){
				 			FaultModel.dao.updateFault(id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,"故障原因描述","尾号","维修措施","责任类别",f_date,"故障出现大区域","故障所属工贸",f_sb_number,sn);
				 		}else{
				 			FaultModel.dao.insertFault(id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,"故障原因描述","尾号","维修措施","责任类别",f_date,"故障出现大区域","故障所属工贸",f_sb_number,sn);
				 			FaultIdList.add(id);
				 		}*/
				 	}
				 
				 
			 }
			 
		 }
	}

}
