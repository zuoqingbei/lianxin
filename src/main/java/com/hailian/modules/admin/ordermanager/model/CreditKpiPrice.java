package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_kpi_price")
public class CreditKpiPrice extends BaseProjectModel<CreditKpiPrice> {
	private static final long serialVersionUID = 1L;
	public static final CreditKpiPrice dao = new CreditKpiPrice();//名字都叫dao，统一命名
}
