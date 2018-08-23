package com.hailian.modules.credit.order.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.CreditOrderInfo;
import com.hailian.modules.credit.order.service.OrderInfoService;
import com.jfinal.plugin.activerecord.Page;
@Api( tag = "订单路由", description = "操作订单信息" )
@ControllerBind(controllerKey = "/credit/orderinfo")
public class CreditOrderController extends BaseProjectController{
	/**
	 * 
	 * @time   2018年8月23日 下午2:33:53
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 根据订单id查找订单
	 */
	@ApiOperation(url = "/credit/orderinfo/one",httpMethod="get", 
			description = "获取订单")
	@Params(value = { 
		@Param(name = "id", description = "id", required = true, dataType = "String")
	})
	public void one() {
		String id =getPara("id");
		CreditOrderInfo coi=OrderInfoService.service.getOrder(id, this);
		if(coi==null) {
			renderText("未查到订单");
		}else {
			renderJson(coi);
		}
	}
	/**
	 * 
	 * @time   2018年8月23日 下午4:59:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 查询顾客的订单并分页
	 */
	@ApiOperation(url = "/credit/orderinfo/list",httpMethod="get", 
			description = "获取订单")
	@Params(value = { 
		@Param(name = "custom_id", description = "客户id", required = true, dataType = "String"),
		@Param(name = "pageNumber", description = "页码", required = true, dataType = "String"),
		@Param(name = "pageSize", description = "每页记录数", required = true, dataType = "String")
	})
	public void list() {
		String customid =getPara("custom_id");
		int pageNumber=getParaToInt("pageNumber");
		int pageSize=getParaToInt("pageSize");
		Page<CreditOrderInfo> coi=OrderInfoService.service.getOrders(pageNumber, pageSize, customid, this);
		if(coi==null) {
			renderText("未查到订单");
		}else {
			renderJson(coi);
		}
	}
	/**
	 * 
	 * @time   2018年8月23日 下午3:48:09
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 添加订单
	 */
	@ApiOperation(url = "/credit/orderinfo/add",httpMethod="post", 
			description = "插入订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "从表单中获取的数据", required = false, dataType = "String")
	})
	public void add() {
		CreditOrderInfo coi=getModel(CreditOrderInfo.class);
		Boolean flag=OrderInfoService.service.addOrder(coi,this);
		if(flag) {
			renderText("插入成功");
		}else {
			renderText("插入失败");
		}
	}
	/**
	 * 
	 * @time   2018年8月23日 下午3:54:19
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 修改订单
	 */
	@ApiOperation(url = "/credit/orderinfo/modify",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "从表单中获取的数据", required = false, dataType = "String")
	})
	public void modify() {
		CreditOrderInfo coi=getModel(CreditOrderInfo.class);
		Boolean flag=OrderInfoService.service.modifyOrder(coi,this);
		if(flag) {
			renderText("修改成功");
		}else {
			renderText("修改失败");
		}
	}
	/**
	 * 
	 * @time   2018年8月23日 下午4:41:30
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 删除订单
	 */
	@ApiOperation(url = "/credit/orderinfo/delete",httpMethod="post", 
			description = "删除订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "从表单中获取的数据", required = false, dataType = "String")
	})
	public void delete() {
		CreditOrderInfo coi=getModel(CreditOrderInfo.class);
		Boolean flag=OrderInfoService.service.modifyOrder(coi,this);
		if(flag) {
			renderText("删除成功");
		}else {
			renderText("删除失败");
		}
	}
	
}
