
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年9月16日 下午4:52:34
 * @author zuoqb
 * @todo   实验室曲线数据源配置表
 */
@TableBind(tableName = "t_b_db_config",pkName="config_name")
public class DbConfigModel extends Model<DbConfigModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DbConfigModel dao = new DbConfigModel();
	/**
	 * 
	 * @time   2017年9月16日 下午5:18:58
	 * @author zuoqb
	 * @todo   查询所有配置
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> getAllConfig(){
		String sql="select * from t_b_db_config ";
		return Db.find(sql);
	}
	/**
	 * 
	 * @time   2017年9月16日 下午5:13:31
	 * @author zuoqb
	 * @todo   根据配置名称查询配置详情
	 * @param  @param configName
	 * @param  @return
	 * @return_type   Record
	 */
	public Record getConfigDetail(String configName){
		String sql="select * from t_b_db_config where config_name='"+configName+"'";
		return Db.findFirst(sql);
	}
	/**
	 * 
	 * @time   2017年9月16日 下午4:59:21
	 * @author zuoqb
	 * @todo   根据配置获取相应表
	 * @param  @param configName:配置名称
	 * @param  @param columnName:表字段名称
	 * @param  @return
	 * @return_type   String
	 */
	public String getTableNameByColumn(String configName,String columnName){
		Record config=getConfigDetail(configName);
		if(config!=null){
			return config.getStr(columnName)==null?null:config.getStr(columnName).toLowerCase();
		}
		return null;
	}
	
}
