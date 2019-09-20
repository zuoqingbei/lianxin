package com.hailian.modules.admin.ordermanager.model;


import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_transaction_payment")
//此标签用于模型与数据库表的连接
public class CreditTransactionPayment extends BaseProjectModel<CreditTransactionPayment> {
	private static final long serialVersionUID = 1L;
	public static final CreditTransactionPayment dao = new CreditTransactionPayment();//名字都叫dao，统一命名
	

}
