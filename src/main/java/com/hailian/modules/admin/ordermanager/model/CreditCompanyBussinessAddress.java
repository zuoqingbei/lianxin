package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 经营地址
 * @author Administrator
 *
 */
@ModelBind(table = "credit_company_bussiness_address")
//此标签用于模型与数据库表的连接
public class CreditCompanyBussinessAddress extends BaseProjectModel<CreditCompanyBussinessAddress> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyBussinessAddress dao = new CreditCompanyBussinessAddress();//名字都叫dao，统一命名
	

}
