package com.ulab.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.core.Constants;
/**
 * 
 * @time   2017年9月16日 下午5:00:58
 * @author zuoqb
 * @todo   传感器信息（传感器用于生成测试数据）
 */
public class HadoopSensorInfo {
	public static final HadoopSensorInfo dao = new HadoopSensorInfo();
	public List<Record> findSensorInfoByTestIdentification(BaseController c,String configName,String testIdentification,String labCode){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.SENSORINFO);
		String sql="select testidentification,testunitid,sensortypeid,sensorname,unit,totalsequenceno,selected,labcode,maxselect,minselect,sensorid from "+tableName+" where testIdentification='"+testIdentification+"' and selected=1 "+DbConfigModel.dao.getPartitionSql(c, configName, labCode);
		List<Record> sensorInfo=Db.use(configName).find(sql);
		for(Record sType:sensorInfo){
			sType.set("legend", sType.get("sensorid")+":"+sType.get("sensorname")+"("+sType.get("unit")+")");
		}
		return sensorInfo;
	}
	public List<Record> findHiveSensorInfoByTestIdentification(BaseController c,String configName,String testIdentification,String labCode){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.SENSORINFO);
		String sql="select testidentification,testunitid,sensortypeid,sensorname,unit,totalsequenceno,selected,labcode,maxselect,minselect,sensorid from "+tableName+" where trim(primarykey)='"+testIdentification.trim()+"' and selected=1 "+DbConfigModel.dao.getPartitionSql(c, configName, labCode);
		List<Record> sensorInfo=Db.use(configName).find(sql);
		for(Record sType:sensorInfo){
			sType.set("legend", sType.get("sensorid")+":"+sType.get("sensorname")+"("+sType.get("unit")+")");
		}
		return sensorInfo;
	}
}
