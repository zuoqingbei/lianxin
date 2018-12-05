package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_quality_opintion_his")
public class CreditQualityOpintionHistory extends BaseProjectModel<CreditQualityOpintionHistory>{
	private static final long serialVersionUID = 1L;
	public static final CreditQualityOpintionHistory dao = new CreditQualityOpintionHistory();//名字都叫dao，统一命名
}
