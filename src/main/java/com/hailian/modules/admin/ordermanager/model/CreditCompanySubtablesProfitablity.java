package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_subtables_profitablity")
//此标签用于模型与数据库表的连接
public class CreditCompanySubtablesProfitablity extends BaseProjectModel<CreditCompanySubtablesProfitablity> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanySubtablesProfitablity dao = new CreditCompanySubtablesProfitablity();//名字都叫dao，统一命名
	

}
