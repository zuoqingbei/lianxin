package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
//失信被执行人
@ModelBind(table = "credit_company_broken_promises_person")
public class CreditCompanyBrokenPromisesPerson extends BaseProjectModel<CreditCompanyBrokenPromisesPerson> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyBrokenPromisesPerson dao = new CreditCompanyBrokenPromisesPerson();//名字都叫dao，统一命名

}
