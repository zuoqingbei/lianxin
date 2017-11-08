
package com.ulab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.util.HttpClientUtil;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author chen xin
 * @todo   
 */
@TableBind(tableName = "phm_device_status",pkName="id")
public class DeviceStatusModel extends Model<DeviceStatusModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceStatusModel dao = new DeviceStatusModel();
	
	/*
	 * 根据是商品状态表所关联的商品sn查询该商品的实时状态
	 */
//	public Record getDeviceStatusByDeviceSncode(String device_info_sncode){
//		String sql="select * from phm_device_status where device_info_sncode="+device_info_sncode;
//		List<Record>list=Db.find(sql);
//		return list.size()>0?list.get(0):null;
//	}
	
	//通过http请求获得实时状态
	public Record getDeviceStatusByHttp(String sncode){
		String s=HttpClientUtil.sendGetRequest("http://localhost:8088/api/yzd/product/"+sncode,"UTF-8");
		Map mp=(Map) JSONObject.parse(s);
		String location=(String) mp.get("location");
//		String caseStatus="";//壳体
//		String evaporimeter_in="";//蒸发器入口
//		String evaporimeter_out="";//蒸发器出口
//		String condensator_in="";//冷凝器入口
//		String condensator_out="";//冷凝器出口
//		String environment="";//环境
//		String speed="";//转速
//		String exhaust_pressure="";//排气压力
//		String acclerance="";//加速度
//		String refrigerating="";//制冷量
		List<Status> list=new ArrayList<>();
		
		JSONArray monitorArray=(JSONArray) mp.get("monitorConf");
			for(int i=0;i<monitorArray.size();i++){
					Map monitorConf=(Map) monitorArray.get(i);
					String type=(String) monitorConf.get("type");
					Map param=(Map) monitorConf.get("param");
					if(param==null)
						continue;
					String title=(String) param.get("title");
					if("转速T4".equals(title)){
						Status status=Status.getSingleStatus().setStatus("speed", param);
						list.add(status);
					}else if("壳体T11".equals(title)){
						Status status=Status.getSingleStatus().setStatus("case", param);
						list.add(status);
					}else if("排气压力T8".equals(title)){
						Status status=Status.getSingleStatus().setStatus("exhaust_pressure", param);
						list.add(status);
					}else if("蒸发器出口T4".equals(title)){
						Status status=Status.getSingleStatus().setStatus("evaporimeter_out", param);
						list.add(status);
					}else if("冷冻温度T5".equals(title)){
						Status status=Status.getSingleStatus().setStatus("environment", param);
						list.add(status);
					}
					
				
			}
			System.out.println(list);
		return null;
		
	}
	public static void main(String[] args){
		new DeviceStatusModel().getDeviceStatusByHttp("abc");
	}
	
}
	class Status{
		private String name;//指标名字
		private Integer max;//指标最大值
		private Integer min;//指标最小值
		private static Status status=new Status();
		private Status(){}
		protected static Status getSingleStatus(){ return status;}
		protected Status setStatus(String name,Map param){
			Status status=new Status();
			status.setName(name);
			Integer mx=(Integer) param.get("max");
			status.setMax(mx);
			Integer mi=(Integer) param.get("min");
			status.setMin(mi);
			return status;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getMax() {
			return max;
		}
		public void setMax(Integer max) {
			this.max = max;
		}
		public Integer getMin() {
			return min;
		}
		public void setMin(Integer min) {
			this.min = min;
		}
		
		
	}

