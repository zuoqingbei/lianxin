package com.hailian.modules.admin.ordermanager.model;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_country")
public class CreditCountry extends BaseProjectModel<CreditCountry> {
	private static final long serialVersionUID = 1L;
	public static final CreditCountry dao = new CreditCountry();//名字都叫dao，统一命名
}
