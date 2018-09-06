package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditOrderHistory.java
 * @time   2018年8月31日 下午3:40:21
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_order_history")//此标签用于模型与数据库表的连接
public class CreditOrderHistory extends BaseProjectModel<CreditOrderHistory>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditOrderHistory dao = new CreditOrderHistory();//名字都叫dao，统一命名

}
