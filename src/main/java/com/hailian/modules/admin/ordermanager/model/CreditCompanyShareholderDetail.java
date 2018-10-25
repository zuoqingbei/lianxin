package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_shareholder_detail")
//此标签用于模型与数据库表的连接
public class CreditCompanyShareholderDetail extends BaseProjectModel<CreditCompanyShareholderDetail> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyShareholderDetail dao = new CreditCompanyShareholderDetail();//名字都叫dao，统一命名
	

}
