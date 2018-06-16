package com.ulab.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ulab.model.FaultModel;

/**
 * 
 * @time   2017年12月23日 下午7:09:56
 * @author zuoqb
 * @todo   TODO
 */
public class PhmThrendClient implements Runnable {
	private String sn;
	public PhmThrendClient(String sn){
		this.sn=sn;
	}
    public void run() {
    	//System.out.println("start---------------------------------------");
        try {
        	String info=HttpClientUtil.sendGetRequest("http://10.138.87.129/api/yzd/product/"+sn+"/diagnosisResult","UTF-8");
			JSONArray jsonArray=(JSONArray) JSONObject.parse(info);
			FaultModel.dao.deleteFaultBySncode(sn);
			List<String>sqlList=new ArrayList<>();
			for(int j=0;j<jsonArray.size();j++) {
				Map mp=(Map) jsonArray.get(j);
				String cSn=(String) mp.get("sn");//故障编码
				if(cSn.equals(sn)){
					String time=(String) mp.get("time");//故障发生时间
					String faultAppearance=(String) mp.get("fault");//故障现象
					JSONArray advice=(JSONArray) mp.get("advice");
					String adviceIdString=""; //维修措施Id
					String adviceString=""; //维修措施
					//维修措施 目前放到一个字段上 后期可根据需要修改
					for(int k=0;k<advice.size();k++) {
						JSONObject adv=(JSONObject) advice.get(k);
						String advId=(String) adv.get("id");//维修指导ID
						String advZd=(String) adv.get("advice");//维修指导
						if(k==0) {
							adviceIdString+=advId;
							adviceString+=advZd;
						}else {
							adviceIdString+=","+advId;
							adviceString+=","+advZd;
						}
					} 
					//零件
					JSONArray isolatedList=(JSONArray) mp.get("isoLatedList");
					for(int k=0;k<isolatedList.size();k++) {
						String[] data= isolatedList.get(k).toString().split(":");//位号：零件名称
						//String sql="insert into phm_fault (id,sncode,fault_name,fault_repair,fault_repair_id,fault_time,fault_appearance,fault_seat_number)values('"+UUIDTool.getUUID()+"','"+sn+"','"+data[1].trim()+"','"+adviceString+"','"+adviceIdString+"','"+time+"','"+faultAppearance+"','"+data[0].trim()+"')";
						String sql="insert into phm_fault (id,sncode,fault_name,fault_repair,fault_repair_id,fault_time,fault_appearance,fault_seat_number)values('"+UUIDTool.getUUID()+"','"+sn+"','"+data[1].trim()+"','"+adviceString+"','"+adviceIdString+"',TO_DATE('"+time.substring(0, 10)+"','yyyy-MM-dd'),'"+faultAppearance+"','"+data[0].trim()+"')";
						sqlList.add(sql);
					} 
				}
			}
			if(sqlList!=null&&sqlList.size()>0){
				FaultModel.dao.addFault(sqlList);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}
