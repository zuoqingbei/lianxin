package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_creditlevel_describe_dict")
public class CreditCompanyCreditlevelDescribeDict extends BaseProjectModel<CreditCompanyCreditlevelDescribeDict>{
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyCreditlevelDescribeDict dao=new CreditCompanyCreditlevelDescribeDict();
}
