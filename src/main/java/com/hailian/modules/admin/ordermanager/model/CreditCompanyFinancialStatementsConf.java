package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_financial_statements_conf")
//此标签用于模型与数据库表的连接
public class CreditCompanyFinancialStatementsConf extends BaseProjectModel<CreditCompanyFinancialStatementsConf> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyFinancialStatementsConf dao = new CreditCompanyFinancialStatementsConf();//名字都叫dao，统一命名
	

}
