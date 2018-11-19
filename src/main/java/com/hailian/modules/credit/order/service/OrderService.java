package com.hailian.modules.credit.order.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.order.model.TbOrder;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo   订单业务处理
 * @time   2018年8月20日 下午7:00:38
 * @author zuoqb
 */
public class OrderService {

	public static OrderService service = new OrderService();//名字都叫service，统一命名

	/**
	 * @todo   根据订单ID查询订单信息
	 * @time   2018年8月20日 下午7:10:23
	 * @author zuoqb
	 * @return_type   TbOrder
	 */
	public TbOrder getOrder(String orderId, BaseProjectController c) {
		return TbOrder.dao.findById(orderId, c);
	}

	/**
	 * 
	 * @todo   分页查询订单
	 * @time   2018年8月20日 下午7:20:39
	 * @author zuoqb
	 * @return_type   Page<TbOrder>
	 */
	public Page<TbOrder> pagerOrder(int pageNumber, int pagerSize, String customName, BaseProjectController c) {
		return TbOrder.dao.pagerOrder(pageNumber, pagerSize, customName, c);
	}
}
