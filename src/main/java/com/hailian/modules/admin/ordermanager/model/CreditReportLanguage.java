package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditReportLanguage.java
 * @time   2018年9月3日 下午7:32:08
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_report_language")
public class CreditReportLanguage extends BaseProjectModel<CreditReportLanguage>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String languageName;
	public void setLanguageName(String languageName) {
		set("languageName",languageName);
	}
	public String getLanguageName() {
		return get("languageName");
	}
	
	public static final CreditReportLanguage dao= new CreditReportLanguage();
	/**
	 * 
	 * @time   2018年9月5日 上午9:14:42
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryType
	 * @param  @param reporttype
	 * @param  @return
	 * @return_type   List<CreditReportLanguage>
	 */
	public List<CreditReportLanguage> getLanguage(String countryType, String reporttype) {
		
		List<String> params=new ArrayList<String>();
		StringBuffer sql=new StringBuffer();
		sql.append(" select t.*,s.detail_name as languageName from credit_report_language t  ");
		sql.append(" left join sys_dict_detail s on s.detail_id=t.language_id ");
		sql.append("where t.del_flag='0'");
		if (StringUtils.isNotBlank(countryType)) {
			sql.append(" and t.country_id=?");
			params.add(countryType);
		}
		if (StringUtils.isNotBlank(reporttype)) {
			sql.append(" and t.report_id=?");
			params.add(reporttype);
		}
		
		return CreditReportLanguage.dao.find(sql.toString(),params.toArray());
	}

}
