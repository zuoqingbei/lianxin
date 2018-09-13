
package com.hailian.modules.credit.order.controller;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
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



@Api( tag = "订单路由", description = "操作订单信息" )
@ControllerBind(controllerKey = "/credit/order")
public class OrderController extends BaseProjectController {
	/**
	 * @todo   订单列表
	 * @time   2018年8月20日 下午7:10:02
	 * @author zuoqb
	 * @return_type   void
	 */

	@Params(value = { 
			@Param(name = "pageNumber", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "customName", description = "客户名称", required = false, dataType = "String")
			})
	

	public void list() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//分页查询
		Page<TbOrder> pager = OrderService.service.pagerOrder(pageNumber, pageSize, getPara("customName"), this);
		setAttr("page", pager);
		keepPara();
		render("/pages/credit/order/order_list.html");
	}
	@ApiOperation(url = "/credit/order/listJson",httpMethod="get", 
	description = "获取订单列表",response=Page.class)
	public void listJson() {
		int pageNumber=getParaToInt("pageNumber", 1);
		int pageSize=getParaToInt("pageSize", 10);
		//分页查询
		Page<TbOrder> pager = OrderService.service.pagerOrder(pageNumber,pageSize,getPara("customName"),this);
		renderJson(pager);
	}

	/**
	 * @todo   单条订单明细
	 * @time   2018年8月20日 下午7:10:02
	 * @author zuoqb
	 * @return_type   void
	 */
	public void view() {
		TbOrder model = TbOrder.dao.getOrder(getPara("id"), this);
		setAttr("model", model);
		render("/pages/credit/order/order_view.html");
	}
	
	

}
