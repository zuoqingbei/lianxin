package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;

@ModelBind(table = "credit_company_industry_revenue")
//此标签用于模型与数据库表的连接
public class CreditCompanyIndustryRevenue extends BaseProjectModel<CreditCompanyIndustryRevenue> {
	private static final long serialVersionUID = 1L;
    //名字都叫dao，统一命名
	public static final CreditCompanyIndustryRevenue dao = new CreditCompanyIndustryRevenue();
	public void deleteBycomIdAndLanguage(String companyid,String sys_language){
		String sql="update credit_company_his set del_flag=1 where company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		Db.update(sql, params.toArray());
	}

}
