
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月26日 下午2:09:06
 * @author zuoqb
 * @todo   传感器
 */
@TableBind(tableName = "lhjx_sensor_type")
public class SensorTypeModel extends Model<SensorTypeModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final SensorTypeModel dao = new SensorTypeModel();
	/**
	 * 
	 * @time   2017年5月26日 下午2:10:39
	 * @author zuoqb
	 * @todo   获取传感器信息
	 * @param  @param labTypeCode:实验室名称
	 * @param  @param testUnitId：台位
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findSensorByLab(String labTypeCode,String testUnitId){
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.*,t.rowid,sort||':'||sensor_type_name||'('||unit||')' as legend from lhjx_sensor_type t where lab_code='"+labTypeCode+"' and test_unit_id='"+testUnitId+"' order by sort ");
		return Db.find(sb.toString());
	}
}
