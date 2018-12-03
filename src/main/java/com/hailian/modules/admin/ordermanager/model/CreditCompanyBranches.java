package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_branches")
//此标签用于模型与数据库表的连接
public class CreditCompanyBranches extends BaseProjectModel<CreditCompanyBranches> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyBranches dao = new CreditCompanyBranches();//名字都叫dao，统一命名
	

}
