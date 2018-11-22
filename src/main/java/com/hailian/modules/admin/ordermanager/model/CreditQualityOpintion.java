package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_quality_opintion")
public class CreditQualityOpintion extends BaseProjectModel<CreditQualityOpintion>{
	private static final long serialVersionUID = 1L;
	public static final CreditQualityOpintion dao = new CreditQualityOpintion();//名字都叫dao，统一命名
}
