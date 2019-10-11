package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "credit_company_naturalperson_shareholder_detail")
//自然人股东详情
public class CreditCompanyNaturalpersonShareholderDetail extends BaseProjectModel<CreditCompanyNaturalpersonShareholderDetail> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyNaturalpersonShareholderDetail dao = new CreditCompanyNaturalpersonShareholderDetail();//名字都叫dao，统一命名
	
	public void deleteBycomIdAndLanguage(String companyid,String sys_language){
		String sql="update credit_company_naturalperson_shareholder_detail set del_flag=1 where company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		Db.update(sql, params.toArray());
	}
}
