package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@TableBind(tableName="phm_monitoring_aids" ,pkName="m_id")
public class MonitoringAidsModel extends Model<MonitoringAidsModel>{
	private static final MonitoringAidsModel dao=new MonitoringAidsModel();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Record> ShowAllMonitoringAidsById(Integer id){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from phm_monitoring_aids");
		return Db.find(sb.toString());
	}

}
