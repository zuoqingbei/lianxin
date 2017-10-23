package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName="phm_facility_index",pkName="m_name")
public class FacilityIndexModel extends Model<FacilityIndexModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final FacilityIndexModel dao=new FacilityIndexModel();
	public List<Record>ShowAllFacilityIndexModelById(Integer id){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from phm_facility_index f where f.m_id");
		return Db.find(sb.toString());
	} 
	

}
