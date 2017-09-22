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
 * @todo   测试数据元数据信息(每次测试生成一条信息)
 */
public class HadoopTestMetadata {
	public static final HadoopTestMetadata dao = new HadoopTestMetadata();
	/**
	 * 
	 * @time   2017年9月20日 上午11:17:52
	 * @author zuoqb
	 * @todo   根据实验室以及测试单元编码获取测试信息
	 * @param  @param configName
	 * @param  @param labCode
	 * @param  @param testUnitid
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findTestMetadata(BaseController c,String configName,String labCode,String testUnitid){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA);
		String sql="select * from "+tableName+"  where testunitid='"+testUnitid+"' and  labcode='"+labCode+"' order by createdate desc  ";
		return Db.use(configName).find(sql);
	}
	
	/**
	 * 
	 * @time   2017年9月20日 上午11:17:52
	 * @author zuoqb
	 * @todo   根据实验室以及测试单元编码获取最后一次测试信息
	 * @param  @param configName
	 * @param  @param labCode
	 * @param  @param testUnitid
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public Record findLastTestMetadata(BaseController c,String configName,String labCode,String testUnitId){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA);
		String sql="select * from "+tableName+"  where testunitid='"+testUnitId+"' and  labcode='"+labCode+"' order by createdate desc  ";
		List<Record> data=Db.use(configName).find(sql);
		if(data!=null&&data.size()>0){
			return data.get(0);
		}
		return null;
	}
}
