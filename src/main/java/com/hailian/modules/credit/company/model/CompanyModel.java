package com.hailian.modules.credit.company.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 公司model层
* @author doushuihai  
* @date 2018年9月10日上午9:58:38  
* @TODO
 */
@ModelBind(table = "credit_company_info")
public class CompanyModel extends BaseProjectModel<CompanyModel> {
	/** 
	* @Fields serialVersionUID : TODO 
	*/
	private static final long serialVersionUID = 1L;
	public static final CompanyModel dao = new CompanyModel();
	private Page<CompanyModel> companyPage;
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("name");
		columnnNames.add("name_en");
		columnnNames.add("registration_num");
		columnnNames.add("credit_code");
		
	}

	/**
	 * 根据id获取公司
	* @author doushuihai  
	* @date 2018年9月10日上午9:58:14  
	* @TODO
	 */
	public List<CompanyModel> getCompany(Integer id) {
		StringBuffer sql = new StringBuffer("select * from credit_company_info where del_flag=0");
		List<Object> params = new ArrayList<Object>();
		if (id != null) {
			sql.append(" and id=?");
			params.add(id);
		}
		sql.append(" order by name desc");
		List<CompanyModel> find = CompanyModel.dao.find(sql.toString(), params.toArray());
		return find;

	}

	/**
	 * 
	 * @time   2018年9月10日 下午2:31:35
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   Page<CompanyModel>
	 */
	public Page<CompanyModel> getPage(Paginator paginator, CompanyModel attr, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select * ");
		StringBuffer fromSql = new StringBuffer(" from credit_company_info t where 1=1 and t.del_flag=0  ");
		Page<CompanyModel> companyodel = CompanyModel.dao.paginate(new Paginator(), selectSql.toString(),
				fromSql.toString());
		return companyodel;

	}

	/**
	 * 
	 * @time   2018年9月10日 下午5:36:04
	 * @author dyc
	 * @todo   删除单条企业信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		CompanyModel company = CompanyModel.dao.findById(id);
		if (company != null) {
			return company.set("del_flag", 1).update();

		}

		return false;
	}

	/**
	 * 
	 * @time   2018年9月11日 上午10:01:07
	 * @author dyc
	 * @todo   分页查询企业信息
	 * @return_type   Page<CompanyModel>
	 */
	public Page<CompanyModel> pageCompany(int pageNumber, int pageSize, String orderBy,
		String companyName, String companyNameEn, String registrationNum, String creditCode, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select c.detail_name as Currency ,t.*");
		StringBuffer fromSql = new StringBuffer("  from credit_company_info t  ");
		fromSql.append("	LEFT JOIN sys_dict_detail c on c.detail_id=t.currency");
		fromSql.append(" where c.del_flag=0 and t.del_flag=0 ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(companyName)) {
			fromSql.append("and t.name  like concat('%',?,'%')  ");
			params.add(companyName);
		}
		if (StringUtils.isNotBlank(companyNameEn)) {
			fromSql.append("and t.name_en  like concat('%',?,'%')  ");
			params.add(companyNameEn);
		}
		if (StringUtils.isNotBlank(registrationNum)) {
			fromSql.append("and t.registration_num  like concat('%',?,'%')  ");
			params.add(registrationNum);
		}
		if (StringUtils.isNotBlank(creditCode)) {
			fromSql.append("and t.credit_code like concat('%',?,'%')  ");
			params.add(creditCode);
		}
		if (StrUtils.isEmpty(orderBy)) {
			fromSql.append(" order by t.create_date desc");
		} else {
			fromSql.append(" order by ").append(orderBy);
		}
		Page<CompanyModel> company = CompanyModel.dao.paginate(new Paginator(pageNumber, pageSize),
				selectSql.toString(), fromSql.toString(), params.toArray());

		return company;

	}

	/**
	 * 
	 * @time   2018年9月11日 上午10:39:50
	 * @author dyc
	 * @todo   单条查看企业信息
	 * @return_type   CompanyModel
	 */
	public CompanyModel getOne(int id, BaseProjectController c) {
		StringBuffer sql = new StringBuffer("select c.detail_name as Currency ,t.* from credit_company_info t ");
		sql.append("  LEFT JOIN sys_dict_detail c on c.detail_id=t.currency");
		sql.append(" where c.del_flag=0 and t.del_flag=0 and t.id=?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return CompanyModel.dao.findFirst(sql.toString(), params.toArray());

	}
}
