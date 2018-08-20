package com.hailian.modules.credit.order.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.TbOrder;

/**
 * 
 * @todo   订单
 * @time   2018年8月20日 下午5:02:19
 * @author zuoqb
 */
@ControllerBind(controllerKey = "/credit/order")
public class OrderController extends BaseProjectController {


	
	public void view() {
		TbOrder model = TbOrder.dao.getOrder(getPara("id"));
		setAttr("model", model);
		render("/pages/credit/order/order_view.html");
	}

}
