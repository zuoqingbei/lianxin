package com.hailian.modules.credit.company.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table = "credit_company_info")
/**
 * 公司model层
* @author doushuihai  
* @date 2018年9月10日上午9:58:38  
* @TODO
 */
public class CompanyModel extends BaseProjectModel<CompanyModel> {
	/** 
	* @Fields serialVersionUID : TODO 
	*/  
	private static final long serialVersionUID = 1L;
	public static final CompanyModel dao=new CompanyModel();
	/**
	 * 根据id获取公司
	* @author doushuihai  
	* @date 2018年9月10日上午9:58:14  
	* @TODO
	 */
	public List<CompanyModel> getCompany(Integer id){
		StringBuffer sql=new StringBuffer("select * from credit_company_info where del_flag=0");
		List<Object> params=new ArrayList<Object>();
		if(id !=null){
			sql.append(" and id=?");
			params.add(id);
		}
		sql.append(" order by name desc");
		List<CompanyModel> find = CompanyModel.dao.find(sql.toString(),params.toArray());
		return find;
		
	}
}
