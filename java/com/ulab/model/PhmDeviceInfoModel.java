
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author zuoqb
 * @todo   
 */
@TableBind(tableName = "phm_device_info",pkName="id")
public class PhmDeviceInfoModel extends Model<PhmDeviceInfoModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final PhmDeviceInfoModel dao = new PhmDeviceInfoModel();
	public  List<Record> getAllDevice(){
		String sql=" select * from phm_device_info ";
		return Db.find(sql);
	}
}
