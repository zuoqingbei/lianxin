package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_kpi_result")
public class CreditKpiResult extends BaseProjectModel<CreditKpiResult> {
	private static final long serialVersionUID = 1L;
	public static final CreditKpiResult dao = new CreditKpiResult();//名字都叫dao，统一命名
}
