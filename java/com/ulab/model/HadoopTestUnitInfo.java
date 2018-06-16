package com.ulab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.core.Constants;
/**
 * 
 * @time   2017年9月20日09:30:53
 * @author zuoqb
 * @todo   监测单元信息（用于生成实验室与台位对照关系）
 */
public class HadoopTestUnitInfo {
	public static final HadoopTestUnitInfo dao = new HadoopTestUnitInfo();
	public List<Record> findAllLabHive(BaseController c,String configName){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTUNITINFO);
		String sql="select labcode, testunitid, testunitname, englishname from "+tableName;
		List<Record> labList=Db.use(configName).find(sql);
		Map<String,Record> labMap=new HashMap<String,Record>();
		for(Record r:labList){
			labMap.put(r.getStr("labcode"), r);
		};
		List<Record> labs = new ArrayList<Record>(labMap.values()); 
		for(Record lab:labs){
			List<Record> testUnitList=toUnitListByLabCode(lab.getStr("labcode"),labList);
			testUnitList=testingStatus(c, configName, lab.getStr("labcode"), testUnitList);
			lab.set("testunitlist",testUnitList);
		}
		return labs;
	}
	public List<Record> testingStatus(BaseController c,String configName,String labCode,List<Record> testUnitList){
		
		//查询该台位目前测试状态isTesting 用来标识实验是否进行当中，正在测试取值为1，反之为0
		for(Record unit:testUnitList){
			Record r=HadoopTestMetadata.dao.findLastTestMetadata(c,configName, labCode, unit.get("testunitid")+"");
			if(r!=null){
				unit.set("istesting", r.get("istesting"));
				unit.set("testIdentification", r.get("testIdentification"));
			}
			if(unit.get("istesting")!=null&&StringUtils.isNotBlank(unit.get("istesting")+"")&&"1".equals(unit.get("istesting"))){
				unit.set("testunitstatus", "在测");
				unit.set("istesting",true);
			}else{
				unit.set("testunitstatus", "停测");
				unit.set("istesting",false);
			}
		}
		return testUnitList;
	}
	public List<Record> toUnitListByLabCode(String labCode,List<Record> labList){
		Map<String,Record> unitMap=new HashMap<String,Record>();
		for(Record lab:labList){
			if(lab.getStr("labcode").equals(labCode)){
				unitMap.put(lab.get("testunitid")+"", lab);
			}
		}
		List<Record> unitList = new ArrayList<Record>(unitMap.values()); 
		return unitList;
	}
	
	/**
	 * 
	 * @time   2017年9月20日 上午9:41:45
	 * @author zuoqb
	 * @todo   查询所有实验室包含对应台位
	 * @param  @param configName
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findAllLab(BaseController c,String configName){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTUNITINFO);
		String sql="select distinct labcode from "+tableName+"   order by labcode  ";
		List<Record> labList=Db.use(configName).find(sql);
		for(Record lab:labList){
			lab.set("testunitlist", findTestUnitListByLabCode(c,configName, lab.getStr("labcode")));
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
	public List<Record> findTestUnitListByLabCode(BaseController c,String configName,String labCode){
		String tableName=DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTUNITINFO);
		String sql=" ";
		if(DbConfigModel.dao.isPartition(c, configName)){
			/*sql+=" select distinct t.labcode as labcode,t.testunitid as testunitid,t.testunitname as testunitname,t.englishname as englishname ";
			sql+=" from "+tableName+" t   ";
			sql+=" where t.labcode='"+labCode+"' ";*/
			sql+=" select distinct t.labcode as labcode,t.testunitid as testunitid,t.testunitname as testunitname,t.englishname as englishname ,m.istesting as istesting ";
			sql+=" from "+tableName+" t   ";
			sql+=" left join (select t1.* from "+DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA)+" t1 inner join(select  labcode,max(testbegintime) as testbegintime,testunitid ";
			sql+=" from "+DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA)+" where  labcode='"+labCode+"' " +DbConfigModel.dao.getPartitionSql(c, configName, labCode) ;
			sql+=" group by labcode,testunitid) t2 on t1.labcode=t2.labcode and t1.testunitid=t2.testunitid and t1.testbegintime=t2.testbegintime ";
			sql+=" ) m on m.labcode=t.labcode and t.testunitid=m.testunitid ";
			sql+=" where t.labcode='"+labCode+"' "+DbConfigModel.dao.getPartitionSql(c, configName, labCode,"t",true)+"   ";
		}else{
			sql+=" select distinct t.labcode as labcode,t.testunitid as testunitid,t.testunitname as testunitname,t.englishname as englishname ,m.istesting as istesting ";
			sql+=" from "+tableName+" t   ";
			sql+=" left join (select t1.* from "+DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA)+" t1 inner join(select  labcode,max(testbegintime) as testbegintime,testunitid ";
			sql+=" from "+DbConfigModel.dao.getTableNameByColumn(c,configName, Constants.TESTMETADATA)+" where  labcode='"+labCode+"' " +DbConfigModel.dao.getPartitionSql(c, configName, labCode) ;
			sql+=" group by labcode,testunitid) t2 on t1.labcode=t2.labcode and t1.testunitid=t2.testunitid and t1.testbegintime=t2.testbegintime ";
			sql+=" ) m on m.labcode=t.labcode and t.testunitid=m.testunitid ";
			sql+=" where t.labcode='"+labCode+"' "+DbConfigModel.dao.getPartitionSql(c, configName, labCode,"t",true)+"   ";
		}
		
		List<Record> testUnitList=Db.use(configName).find(sql);
		//查询该台位目前测试状态isTesting 用来标识实验是否进行当中，正在测试取值为1，反之为0
		for(Record unit:testUnitList){
			/*Record r=HadoopTestMetadata.dao.findLastTestMetadata(c,configName, labCode, unit.get("testunitid")+"");
			if(r!=null){
				unit.set("istesting", r.get("istesting"));
				unit.set("testIdentification", r.get("testIdentification"));
			}*/
			if(unit.get("istesting")!=null&&StringUtils.isNotBlank(unit.get("istesting")+"")&&("true".equals(unit.get("istesting")+"")||"1".equals(unit.get("istesting")+""))){
				unit.set("testunitstatus", "在测");
			}else{
				unit.set("testunitstatus", "停测");
				unit.set("istesting",false);
			}
		}
		return testUnitList;
	}
}
