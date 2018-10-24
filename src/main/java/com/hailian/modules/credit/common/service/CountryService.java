package com.hailian.modules.credit.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.jfinal.plugin.activerecord.Page;

public class CountryService {
	public static CountryService service = new CountryService();

	/**
	 * 国家地区下拉框
	* @author doushuihai  
	* @date 2018年8月22日下午2:32:30  
	* @TODO
	 */
	public List<CountryModel> selectCountry(String selected, BaseProjectController c) {
		String sql = "select * from credit_country where del_flag=0 ";
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(selected)) {
			sql += "  and name like ? or name_en like ? or short_name like ? or "
					+ "continent like ? or continent_en like ?";
			params.add('%' + selected + '%');
			params.add('%' + selected + '%');
			params.add('%' + selected + '%');
			params.add('%' + selected + '%');
			params.add('%' + selected + '%');
		}
		sql += "order by order_no";
		return CountryModel.dao.find(sql, params.toArray());

	}

	public List<CountryModel> CountrySelect(String content, BaseProjectController c) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from credit_country where del_flag=0  ");
		if (StringUtils.isNotBlank(content) && !"ALL".equals(content)) {
			sql.append(" and continent_en=? order by order_no");
			params.add(content);
		} else {
			sql.append("  order by continent, order_no");
		}

		return CountryModel.dao.find(sql.toString(), params.toArray());
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
	public List<CountryModel> CountrySelect(String id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from credit_country where del_flag=0  ");
		if (StringUtils.isNotBlank(id) && !"ALL".equals(id)) {
			sql.append(" and continent=? order by   scale DESC,CONVERT (`NAME` USING gbk) COLLATE gbk_chinese_ci ASC ");
			params.add(id);
		} else {
			sql.append("  order by scale DESC,CONVERT (`NAME` USING gbk) COLLATE gbk_chinese_ci ASC ");
		}

		return CountryModel.dao.find(sql.toString(), params.toArray());
	}

	/**
	 * 
	 * @time   2018年9月20日 上午10:22:39
	 * @author dyc
	 * @todo   分页查询国家信息
	 * @return_type   Page<CompanyModel>
	 */
	public Page<CountryModel> pageCountry(int pageNumber, int pageSize, String orderby, String Continent,
			String countryName, String countrynameEn, String telPre, String usabledName, BaseProjectController c) {
		return CountryModel.dao.pageCountry(pageNumber, pageSize, orderby, Continent, countryName, countrynameEn,
				telPre, usabledName, c);

	}

	/**
	 * 
	 * @time   2018年9月20日 下午3:55:29
	 * @author dyc
	 * @todo   根据id单条查看国家信息
	 * @return_type   CountryModel
	 */

	public CountryModel getOne(int id, BaseProjectController c) {
		CountryModel countryModel = CountryModel.dao.getOne(id, c);
		return countryModel;

	}

	/**
	 * 
	 * @time   2018年9月21日 上午9:20:20
	 * @author dyc
	 * @todo   根据id删除单条国家信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {

		return CountryModel.dao.updateDelFlagById(id);
	}
}
