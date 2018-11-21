package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_staff")
//此标签用于模型与数据库表的连接

public class CreditCompanyStaff extends BaseProjectModel<CreditCompanyStaff> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyStaff dao = new CreditCompanyStaff();//名字都叫dao，统一命名
	
}