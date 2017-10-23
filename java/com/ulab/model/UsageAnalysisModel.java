package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@TableBind(tableName="phm_usage_analysis",pkName="m_id")
public class UsageAnalysisModel extends Model<UsageAnalysisModel> {
	
	private static final long serialVersionUID = 1L;
	public static final UsageAnalysisModel dao=new UsageAnalysisModel();
	
	public List<Record>showUsageAnalysis(Integer id){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from phm_usage_analysis where m_id="+id);
		
		return Db.find(sb.toString());
	}

}
