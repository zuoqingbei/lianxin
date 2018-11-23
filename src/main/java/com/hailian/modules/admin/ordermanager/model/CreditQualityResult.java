package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_quality_result")
public class CreditQualityResult extends BaseProjectModel<CreditQualityResult>{
	private static final long serialVersionUID = 1L;
	public static final CreditQualityResult dao = new CreditQualityResult();//名字都叫dao，统一命名
}
