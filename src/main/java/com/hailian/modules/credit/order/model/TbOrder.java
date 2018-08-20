package com.hailian.modules.credit.order.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * @todo   订单表
 * @time   2018年8月20日 下午5:01:17
 * @author zuoqb
 */
@ModelBind(table = "tb_order")
public class TbOrder extends BaseProjectModel<TbOrder> {

	private static final long serialVersionUID = 1L;
	public static final TbOrder dao = new TbOrder();
	/**
	 * @todo   根据Id查询订单
	 * @time   2018年8月20日 下午5:01:28
	 * @author zuoqb
	 * @return_type   TbOrder
	 */
	public TbOrder getOrder(String orderId) {
		return TbOrder.dao.findById(orderId);
	}

}