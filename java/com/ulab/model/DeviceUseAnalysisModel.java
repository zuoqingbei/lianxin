
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author chen xin
 * @todo   
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
		"select analy.DESCRIPTION,analy.STATUS,analy.IMAGEURL from PHM_DEVICE_INFO info ,PHM_DEVICE_USE_ANALYSIS analy"+ 
		"  where analy.DEVICE_INFO_SNCODE=INFO.SNCODE and analy.DEVICE_INFO_SNCODE='" +device_info_sncode+"'";
		List<Record>list=Db.find(sql);
		return list;
	}
	
}
