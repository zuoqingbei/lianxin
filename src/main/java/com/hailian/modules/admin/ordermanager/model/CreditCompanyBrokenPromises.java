package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
//失信被执行
@ModelBind(table = "credit_company_broken_promises")
public class CreditCompanyBrokenPromises extends BaseProjectModel<CreditCompanyBrokenPromises> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyBrokenPromises dao = new CreditCompanyBrokenPromises();//名字都叫dao，统一命名

}
