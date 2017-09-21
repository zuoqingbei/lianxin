package com.ulab.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.Constants;
/**
 * 
 * @time   2017年9月16日 下午5:00:58
 * @author zuoqb
 * @todo   传感器信息（传感器用于生成测试数据）
 */
public class HadoopSensorInfo {
	public static final HadoopSensorInfo dao = new HadoopSensorInfo();
	public List<Record> findSensorInfoByTestIdentification(String configName,String testIdentification){
		String tableName=DbConfigModel.dao.getTableNameByColumn(configName, Constants.SENSORINFO);
		String sql="select * from "+tableName+" where testIdentification='"+testIdentification+"' and selected=1 ";
		List<Record> sensorInfo=Db.use(configName).find(sql);
		for(Record sType:sensorInfo){
			sType.set("legend", sType.get("testunitid")+":"+sType.get("sensorname")+"("+sType.get("unit")+")");
		}
		return sensorInfo;
	}
}
