package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditReportUsetime.java
 * @time   2018年9月3日 下午7:32:28
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_report_usetime")
public class CreditReportUsetime  extends BaseProjectModel<CreditReportUsetime>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditReportUsetime dao = new CreditReportUsetime();//名字都叫dao，统一命名
	
	public  CreditReportUsetime getTime(String countryType, String speed, String reporttype) {
		List<String> params=new ArrayList<String>();
		StringBuffer sql=new StringBuffer("select t.* from credit_report_usetime t where t.del_flag='0' ");
		if (StringUtils.isNotBlank(countryType)) {
			sql.append(" and t.country_type=?");
			params.add(countryType);
		}
		if (StringUtils.isNotBlank(speed)) {
			sql.append(" and t.report_speed=?");
			params.add(speed);
		}
		if (StringUtils.isNotBlank(reporttype)) {
			sql.append(" and t.report_id=?");
			params.add(reporttype);
		}
		
		return CreditReportUsetime.dao.findFirst(sql.toString(),params.toArray());
	}

}
