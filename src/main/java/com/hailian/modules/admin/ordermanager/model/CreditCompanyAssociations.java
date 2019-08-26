package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_associations")
public class CreditCompanyAssociations extends BaseProjectModel<CreditCompanyAssociations> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyAssociations dao = new CreditCompanyAssociations();//名字都叫dao，统一命名
}
