
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author chen xin
 * @todo   使用分析
 */
@TableBind(tableName = "phm_device_use_analysis",pkName="id")
public class DeviceUseAnalysisModel extends Model<DeviceUseAnalysisModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceUseAnalysisModel dao = new DeviceUseAnalysisModel();
	
	public List<Record> getAllDeviceUseAnalysis(){
		String sql="select * from phm_device_use_analysis";
		return Db.find(sql);
	}
	/*
	 * 根据是商品使用表所关联的商品sn查询该商品的使用状况
	 */
	public List<Record> getDeviceUseAnalysisByDeviceSncode(String device_info_sncode){
		String sql=
		"select analy.* from phm_device_info info ,phm_device_use_analysis analy"+ 
		"  where analy.device_info_sncode=info.sncode and analy.device_info_sncode='" +device_info_sncode+"'";
		List<Record>list=Db.find(sql);
		return list;
	}
	
}

