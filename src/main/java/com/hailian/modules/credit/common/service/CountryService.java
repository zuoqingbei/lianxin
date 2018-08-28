package com.hailian.modules.credit.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.common.model.CountryModel;


public class CountryService {
	public static CountryService service=new CountryService();
	/**
	 * 国家地区下拉框
	* @author doushuihai  
	* @date 2018年8月22日下午2:32:30  
	* @TODO
	 */
	public List<CountryModel> selectCountry(String selected,BaseProjectController c) {
		String sql="select * from credit_country where del_flag=0 ";
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(selected)){
			sql+="  and name like ? or name_en like ? or short_name like ? or "
					+ "continent like ? or continent_en like ?";
			params.add('%'+selected+'%');
			params.add('%'+selected+'%');
			params.add('%'+selected+'%');
			params.add('%'+selected+'%');
			params.add('%'+selected+'%');
		}
		sql+="order by order_no";
		return CountryModel.dao.find(sql,params.toArray());
	
	}
	public List<CountryModel> CountrySelect(String continent, BaseProjectController c) {
		List<Object> params=new ArrayList<Object>();
		String innerSql="select * from credit_country where del_flag=0 and continent_en=? order by order_no";
		params.add(continent);
		List<CountryModel> countrylist = CountryModel.dao.find(innerSql,params.toArray());
		return countrylist;
	}
}

