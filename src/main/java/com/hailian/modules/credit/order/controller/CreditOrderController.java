package com.hailian.modules.credit.order.controller;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.CreditOrderInfo;
import com.hailian.modules.credit.order.service.OrderService;
@Api( tag = "订单路由", description = "操作订单信息" )
@ControllerBind(controllerKey = "/credit/orderinfo")
public class CreditOrderController extends BaseProjectController{
	private OrderService service=OrderService.service;
	public CreditOrderInfo getOrderInfo() {
		return null;
		
	}
}
