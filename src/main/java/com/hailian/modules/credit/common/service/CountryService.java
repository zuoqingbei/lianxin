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
	public List<CountryModel> CountrySelect(String content,BaseProjectController c) {
		List<Object> params=new ArrayList<Object>();
			StringBuffer sql=new StringBuffer("select * from credit_country where del_flag=0  ");
			if(StringUtils.isNotBlank(content)) {
				sql.append(" and continent_en=? order by order_no");
				params.add(content);
			}else {
				sql.append("  order by continent, order_no");
			}
			
		return  CountryModel.dao.find(sql.toString(),params.toArray());
		/*String sql="select * from credit_country where del_flag=0 group by continent order by order_no";
		List<CountryModel> continentList = CountryModel.dao.find(sql);
		Map<Object,Object> map=new HashMap<Object, Object>();
		for(CountryModel model:continentList){
			List<Object> params=new ArrayList<Object>();
			String innerSql="select name,name_en from credit_country where del_flag=0 and continent=? order by order_no";
			params.add(model.get("continent"));
			List<CountryModel> countrylist = CountryModel.dao.find(innerSql,params.toArray());
			map.put(model, countrylist);
		}
		return map;*/
	}
}

