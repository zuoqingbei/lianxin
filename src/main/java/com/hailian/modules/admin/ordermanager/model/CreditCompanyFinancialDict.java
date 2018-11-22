package com.hailian.modules.admin.ordermanager.model;


import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_company_financial_dict")
//此标签用于模型与数据库表的连接

public class CreditCompanyFinancialDict extends BaseProjectModel<CreditCompanyFinancialDict> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyFinancialDict dao = new CreditCompanyFinancialDict();//名字都叫dao，统一命名
	public static List<CreditCompanyFinancialDict> simplifiedChineseDict;
	public static List<CreditCompanyFinancialDict> chineseTraditionalDict;
	public static List<CreditCompanyFinancialDict> englishDict;
}
