package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_creditlevel_table")
public class CreditCompanyCreditlevelTable extends BaseProjectModel<CreditCompanyCreditlevelTable>{
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyCreditlevelTable dao=new CreditCompanyCreditlevelTable();
}
