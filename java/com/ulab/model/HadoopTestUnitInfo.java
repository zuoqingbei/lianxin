package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.Constants;
/**
 * 
 * @time   2017年9月20日09:30:53
 * @author zuoqb
 * @todo   监测单元信息（用于生成实验室与台位对照关系）
 */
public class HadoopTestUnitInfo {
	public static final HadoopTestUnitInfo dao = new HadoopTestUnitInfo();
	/**
	 * 
	 * @time   2017年9月20日 上午9:41:45
	 * @author zuoqb
	 * @todo   查询所有实验室包含对应台位
	 * @param  @param configName
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findAllLab(String configName){
		String tableName=DbConfigModel.dao.getTableNameByColumn(configName, Constants.TESTUNITINFO);
		String sql="select distinct labcode from "+tableName+"  order by labcode  ";
		List<Record> labList=Db.use(configName).find(sql);
		for(Record lab:labList){
			lab.set("testUnitList", findTestUnitListByLabCode(configName, lab.getStr("labCode")));
		}
		return labList;
	}
	/**
	 * 
	 * @time   2017年9月20日 上午9:41:45
	 * @author zuoqb
	 * @todo   根据实验室查询对应台位
	 * @param  @param configName
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findTestUnitListByLabCode(String configName,String labCode){
		String tableName=DbConfigModel.dao.getTableNameByColumn(configName, Constants.TESTUNITINFO);
		String sql=" ";
		sql+=" select t.labcode,t.testunitid,t.testunitname,t.englishname,m.isTesting,m.testIdentification ";
		sql+=" from "+tableName+" t   ";
		sql+=" left join (select t1.* from tb_testmetadata t1 inner join(select labcode,max(createdate) as createdate,testunitid ";
		sql+=" from tb_testmetadata where  labcode='"+labCode+"' ";
		sql+=" group by labcode,testunitid) t2 on t1.labcode=t2.labcode and t1.testunitid=t2.testunitid and t1.createdate=t2.createdate ";
		sql+=" ) m on m.labcode=t.labcode and t.testunitid=m.testunitid ";
		sql+=" where t.labcode='"+labCode+"'  order by t.testunitid ";
		List<Record> testUnitList=Db.use(configName).find(sql);
		//查询该台位目前测试状态isTesting 用来标识实验是否进行当中，正在测试取值为1，反之为0
		for(Record unit:testUnitList){
			if(unit.get("isTesting")!=null&&StringUtils.isNotBlank(unit.get("isTesting")+"")&&unit.getBoolean("isTesting")){
				unit.set("testUnitStatus", "在测");
			}else{
				unit.set("testUnitStatus", "停测");
				unit.set("isTesting",false);
			}
		}
		return testUnitList;
	}
}
