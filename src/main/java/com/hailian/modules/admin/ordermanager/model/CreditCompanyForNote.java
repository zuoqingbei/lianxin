package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;

@ModelBind(table = "credit_company_for_note")
public class CreditCompanyForNote extends BaseProjectModel<CreditCompanyForNote> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyForNote dao = new CreditCompanyForNote();//名字都叫dao，统一命名

}
