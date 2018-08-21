package com.hailian.modules.credit.order.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.TbOrder;
import com.hailian.modules.credit.order.service.OrderService;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @todo   订单
 * @time   2018年8月20日 下午5:02:19
 * @author zuoqb
 */
@ControllerBind(controllerKey = "/credit/order")
public class OrderController extends BaseProjectController {


	/**
	 * @todo   订单列表
	 * @time   2018年8月20日 下午7:10:02
	 * @author zuoqb
	 * @return_type   void
	 */
	public void list() {
		int pageNumber=getParaToInt("pageNumber", 1);
		int pageSize=getParaToInt("pageSize", 10);
		//分页查询
		Page<TbOrder> pager = OrderService.service.pagerOrder(pageNumber,pageSize,getPara("customName"),this);
		setAttr("page", pager);
		keepPara();
		render("/pages/credit/order/order_list.html");
	}

	/**
	 * @todo   单条订单明细
	 * @time   2018年8月20日 下午7:10:02
	 * @author zuoqb
	 * @return_type   void
	 */
	public void view() {
		TbOrder model = TbOrder.dao.getOrder(getPara("id"));
		setAttr("model", model);
		render("/pages/credit/order/order_view.html");
	}

}
