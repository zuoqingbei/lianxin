package com.hailian.modules.credit.order.service;

import com.hailian.modules.credit.order.model.TbOrder;
/**
 * @todo   订单业务处理
 * @time   2018年8月20日 下午7:00:38
 * @author zuoqb
 */
public class OrderService {

	public static OrderService service= new OrderService();
	public TbOrder getOrder(String orderId) {
		return TbOrder.dao.findById(orderId);
	}
}
