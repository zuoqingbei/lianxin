
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
@TableBind(tableName = "phm_device_info",pkName="id")
public class DeviceInfoModel extends Model<DeviceInfoModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceInfoModel dao = new DeviceInfoModel();
	
	public List<Record> findAllDeviceInfo(){
		String sql="select * from phm_device_info";
		return Db.find(sql);
	}
	/*
	 * 查询商品名字相对应的数量
	 */
	public List<Record> findDeviceKindNumber(){
		String sql="select (select count(id) from PHM_DEVICE_INFO)as total,(select COUNT(id) from PHM_DEVICE_INFO where name='冰箱') as fridge, (select COUNT(id) from PHM_DEVICE_INFO where name='商空') as bussinessAirCondition ,(select COUNT(id) from PHM_DEVICE_INFO where name='洗衣机') as washMachine,(select cOUNT(id) from PHM_DEVICE_INFO where name='冷柜') as icebox,(select COUNT(id) from PHM_DEVICE_INFO where name='家空') as homeAirCondition from dual";
		return Db.find(sql);
	}
	/*
	 * 根据sn查询商品信息
	 */
	public Record findDeviceInfoById(String sn){
		String sql="select * from phm_device_info where sncode="+sn;
		List<Record>list= Db.find(sql);
		return list.size()>0?list.get(0):null;
	}
	
}
