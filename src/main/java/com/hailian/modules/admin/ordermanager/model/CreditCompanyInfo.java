package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_info")
public class CreditCompanyInfo extends BaseProjectModel<CreditCompanyInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CreditCompanyInfo dao=new CreditCompanyInfo();

	public List<CreditCompanyInfo> getCompany() {
		
		return dao.find("select * from credit_company_info order by id");
	}
}
