package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditReportUsetime.java
 * @time   2018年9月3日 下午7:32:28
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_company_industry_situation_dict")
public class CreditCompanyIndustrySituationDict  extends BaseProjectModel<CreditCompanyIndustrySituationDict>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyIndustrySituationDict dao = new CreditCompanyIndustrySituationDict();//名字都叫dao，统一命名
 
}
