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
 * @todo   传感器类型信息（用于生成y轴信息）
 */
public class HadoopSensorTypeInfo {
	public static final HadoopSensorTypeInfo dao = new HadoopSensorTypeInfo();
	/**
	 * 
	 * @time   2017年9月20日 上午10:12:31
	 * @author zuoqb
	 * @todo   根据实验室编码获取传感器类型信息
	 * @param  @param configName
	 * @param  @param labCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findSensorTypeInfoByLabCode(BaseController c,String configName,String labCode){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.SENSORTYPEINFO);
		String sql="select labcode,sensortypeid,sensortypename,highvalue,lowvalue,unit from "+tableName+" where labcode='"+labCode+"' order by sensortypeid ";
		List<Record> sensorTypeList=Db.use(configName).find(sql);
		for(Record sType:sensorTypeList){
			sType.set("legend", sType.get("sensortypeid")+":"+sType.get("sensortypename")+"("+sType.get("unit")+")");
		}
		return sensorTypeList;
	}
}
