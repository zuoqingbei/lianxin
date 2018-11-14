package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_creditlevel_describe")
public class CreditCompanyCreditlevelDescribe extends BaseProjectModel<CreditCompanyCreditlevelDescribe>{
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyCreditlevelDescribe dao=new CreditCompanyCreditlevelDescribe();
}
