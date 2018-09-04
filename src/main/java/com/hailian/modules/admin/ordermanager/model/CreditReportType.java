package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditReportType.java
 * @time   2018年9月3日 下午7:32:15
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_report_type")//此标签用于模型与数据库表的连接
public class CreditReportType extends BaseProjectModel<CreditReportType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditReportType dao= new CreditReportType();
	
	public List<CreditReportType> getReportType() {
		List<CreditReportType> list=dao.find("select t.* from credit_report_type t where t.del_flag='0' order by order_no ");
		return list;
	}

}
