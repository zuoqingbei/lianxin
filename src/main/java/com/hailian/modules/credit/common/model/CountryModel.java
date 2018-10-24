package com.hailian.modules.credit.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

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
	private static List<String> columnnNames = new ArrayList<>();
	static {
		columnnNames.add("continent");
		columnnNames.add("name");
		columnnNames.add("name_en");
		columnnNames.add("tel_pre");
		columnnNames.add("usabled");

	}

	public static List<CountryModel> getCountrys(String continent) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from credit_country t left join sys_dict_detail s on t.continent_en=s.detail_name_en where t.del_flag='0' ");
		//sql.append(" select t.* from credit_country t left join sys_dict_detail s on t.continent_en=s.detail_name_en where t.del_flag='0' ORDER BY scale DESC, CONVERT (`NAME` USING gbk) COLLATE gbk_chinese_ci ASC ");
		if (StringUtils.isNotBlank(continent)) {
			sql.append(" and s.detail_id=?");
			params.add(continent);
		}
		List<CountryModel> list = dao.find(sql.toString(), params.toArray());
		return list;
	}
	public static List<CountryModel> getCountryByName(String name) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from credit_country where del_flag='0' ");

		if (StringUtils.isNotBlank(name)) {
			sql.append(" and name=?");
			params.add(name);
		}
		List<CountryModel> list = dao.find(sql.toString(), params.toArray());
		return list;
	}
	public CountryModel findType(String countryid) {
		CountryModel cm = dao.findFirst("select t.type from credit_country t where t.id=? ", countryid);
		return cm;
	}

	/**
	 * 
	 * @time   2018年9月20日 上午10:36:55
	 * @author dyc
	 * @todo   分页查询国家信息
	 * @return_type   Page<CountryModel>
	 */
	public Page<CountryModel> pageCountry(int pageNumber, int pageSize, String orderby, String Continent,
			String countryName, String countrynameEn, String telPre, String usabledName, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(
				" select c.detail_name as usabledName,a.detail_name as Continent,t.* ");
		StringBuffer fromSql = new StringBuffer("  from credit_country t ");
		fromSql.append(" LEFT JOIN sys_dict_detail c on c.detail_id=t.usabled ");
		fromSql.append(" LEFT JOIN sys_dict_detail a on a.detail_id=t.continent");
		fromSql.append(" where c.del_flag=0 and t.del_flag=0 and a.del_flag=0");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(Continent)) {
			fromSql.append(" and t.continent  like concat('%',?,'%')  ");
			params.add(Continent);
		}
		if (StringUtils.isNotBlank(countryName)) {
			fromSql.append(" and t.name  like concat('%',?,'%')  ");
			params.add(countryName);
		}
		if (StringUtils.isNotBlank(countrynameEn)) {
			fromSql.append(" and t.name_en  like concat('%',?,'%')  ");
			params.add(countrynameEn);
		}
		if (StringUtils.isNotBlank(telPre)) {
			fromSql.append(" and t.tel_pre  like concat('%',?,'%')  ");
			params.add(telPre);
		}
		if (StringUtils.isNotBlank(usabledName)) {
			fromSql.append(" and t.usabled  like concat('%',?,'%')  ");
			params.add(usabledName);
		}
		if (StrUtils.isEmpty(orderby)) {
			fromSql.append("  order by t.create_date desc");
		} else {
			fromSql.append(" order by ").append(orderby);
		}
		Page<CountryModel> country = CountryModel.dao.paginate(new Paginator(pageNumber, pageSize),
				selectSql.toString(), fromSql.toString(), params.toArray());
		return country;

	}

	/**
	 * 
	 * @time   2018年9月20日 下午3:54:41
	 * @author dyc
	 * @todo   根据id单条查看国家信息
	 * @return_type   CountryModel
	 */
	public CountryModel getOne(int id, BaseProjectController c) {
		StringBuffer sql = new StringBuffer(" select c.detail_name as usabledName,a.detail_name as Continent,t.* ");
		StringBuffer fromsql = new StringBuffer(" from credit_country t");
		fromsql.append(" LEFT JOIN sys_dict_detail c on c.detail_id=t.usabled");
		fromsql.append(" LEFT JOIN sys_dict_detail a on a.detail_id=t.continent");
		fromsql.append(" where t.del_flag=0 and c.del_flag=0 and a.del_flag=0 and t.id=?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return CountryModel.dao.findFirst(sql.toString() + fromsql.toString(), params.toArray());

	}

	/**
	 * 
	 * @time   2018年9月21日 上午9:21:39
	 * @author dyc
	 * @todo   根据id删除单条国家信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		CountryModel country = CountryModel.dao.findById(id);
		if (country != null) {
			return country.set("del_flag", 1).update();

		}

		return false;
	}
	public List<CountryModel> findAll() {
		String sql="select t.* from credit_country t where t.del_flag=0";
		return dao.find(sql);
	}
	public List<CountryModel> findByIds(String ids) {
		StringBuffer sql= new StringBuffer();
		sql.append("select t.* from credit_country t where t.del_flag=0 and t.id in(");
		if(StringUtils.isNotBlank(ids)) {
			String[] s=ids.split(",");
			for(String id:s) {
				sql.append(id);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		return dao.find(sql.toString());
	}

}
