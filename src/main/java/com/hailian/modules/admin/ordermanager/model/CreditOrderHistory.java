package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

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
	/**
	 * 
	 * @time   2018年9月21日 上午10:07:01
	 * @author yangdong
	 * @todo   TODO 根据订单id获取订单更改记录
	 * @param  @param orderid
	 * @param  @return
	 * @return_type   List<CreditOrderHistory>
	 */
	public List<CreditOrderHistory> getHistroy(String orderid) {
		StringBuffer sql=new StringBuffer();
		sql.append("select t.* from credit_order_history t where t.order_id=?");
		return CreditOrderHistory.dao.find(sql.toString(),orderid);
	}

}
