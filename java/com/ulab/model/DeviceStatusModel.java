
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
@TableBind(tableName = "phm_device_status",pkName="id")
public class DeviceStatusModel extends Model<DeviceStatusModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceStatusModel dao = new DeviceStatusModel();
	
	/*
	 * 根据是商品状态表所关联的商品sn查询该商品的实时状态
	 */
	public Record getDeviceStatusByDeviceSncode(String device_info_sncode){
		String sql="select * from phm_device_status where device_info_sncode="+device_info_sncode;
		List<Record>list=Db.find(sql);
		return list.size()>0?list.get(0):null;
	}
	
}
