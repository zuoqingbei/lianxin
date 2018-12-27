package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;

@ModelBind(table = "credit_company_legalstru")
//此标签用于模型与数据库表的连接
public class CreditCompanyLegalstru extends BaseProjectModel<CreditCompanyLegalstru> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyLegalstru dao = new CreditCompanyLegalstru();//名字都叫dao，统一命名
}
