package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_creditlevel_table_dict")
public class CreditCompanyCreditlevelTableDict extends BaseProjectModel<CreditCompanyCreditlevelTableDict>{
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyCreditlevelTableDict dao=new CreditCompanyCreditlevelTableDict();
}
