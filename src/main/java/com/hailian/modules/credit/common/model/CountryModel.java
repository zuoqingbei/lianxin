
package com.hailian.modules.credit.common.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 国家地区表
* @author doushuihai  
* @date 2018年8月22日下午1:45:23  
* @TODO
 */
@ModelBind(table = "credit_country")
public class CountryModel extends BaseProjectModel<CountryModel> {
	private static final long serialVersionUID = 1L;
	public static final CountryModel dao = new CountryModel();//名字都叫dao，统一命名
	public List<CountryModel> getCountrys(String continent) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from credit_country t left join sys_dict_detail s on t.continent_en=s.detail_name_en where t.del_flag='0' ");
		
		if (StringUtils.isNotBlank(continent)) {
			sql.append(" and s.detail_id=?");
			params.add(continent);
		}
		List<CountryModel> list=dao.find(sql.toString(),params.toArray());
		return list;
	}
	public CountryModel findType(String countryid) {
		CountryModel cm=dao.findFirst("select t.type from credit_country t where t.id=? ", countryid);
		return cm;
	}
}
	

