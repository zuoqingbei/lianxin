package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_creditanalysis")
public class CreditCompanyCreditanalysis extends BaseProjectModel<CreditCompanyCreditanalysis> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyCreditanalysis dao = new CreditCompanyCreditanalysis();//名字都叫dao，统一命名
}
