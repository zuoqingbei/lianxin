package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_report_type")//此标签用于模型与数据库表的连接
public class creditReportType extends BaseProjectModel<creditReportType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final creditReportType dao= new creditReportType();
	
	public List<creditReportType> getReportType() {
		List<creditReportType> list=dao.find("select t.id,t.name from credit_report_type t where"
				+ " t.del_flag='0' and t.id!='1'");
		return list;
	}

}
