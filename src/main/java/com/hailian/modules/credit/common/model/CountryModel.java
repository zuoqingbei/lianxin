package com.hailian.modules.credit.common.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 国家地区表
* @author doushuihai  
* @date 2018年8月22日下午1:45:23  
* @TODO
 */
@ModelBind(table = "credit_country")
public class CountryModel extends BaseProjectModel<CountryModel> {
	private static final long serialVersionUID = 1L;
	public static final CountryModel dao = new CountryModel();//名字都叫dao，统一命名
	

}