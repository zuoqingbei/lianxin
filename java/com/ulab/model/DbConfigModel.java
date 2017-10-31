
package com.ulab.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
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
	public String getTableNameByColumn(BaseController c,String configName,String columnName){
		Record config=c.getSessionAttr("config_db_"+configName);
		if(config==null){
			config=getConfigDetail(configName);
			c.setSessionAttr("config_db_"+configName, config);
		}
		if(config!=null){
			return config.getStr(columnName)==null?null:config.getStr(columnName).toLowerCase();
		}
		return null;
	}
	/**
	 * 
	 * @time   2017年10月28日 下午4:17:21
	 * @author zuoqb
	 * @todo   分区sql语句拼接
	 */
	public String getPartitionSql(BaseController c,String configName,String labCode,String shortTableName){
		if(shortTableName==null||"null".equals(shortTableName)){
			shortTableName="";
		}
		if(StringUtils.isNotBlank(shortTableName)){
			shortTableName=shortTableName+".";
		}
		String partition="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String now=sdf.format(new Date());
		Record config=c.getSessionAttr("config_db_"+configName);
		if(config==null||!"1".equals(config.getStr("partition").trim())){
			return partition;
		}else{
			String sql="select * from t_b_db_partition where lab_code='"+labCode+"' and config_name='"+configName+"' and del_flag=0";
			Record r=Db.findFirst(sql);
			if(r==null){
				return partition;
			}
			if("0".equals(r.getStr("is_ptlabname").trim())){
				//按照实验室编码分区
				partition+=" and "+shortTableName+r.getStr("ptlabname_filed")+"='"+r.getStr("ptlabname_value")+"' ";
			}
			if("0".equals(r.getStr("is_pt").trim())){
				//按年月日分区
				partition+=" and "+shortTableName+"pt='"+now+"' ";
			}
			return partition;
		}
	}
	public String getPartitionSql(BaseController c,String configName,String labCode){
		return getPartitionSql(c, configName, labCode, null);
	}
	
}
