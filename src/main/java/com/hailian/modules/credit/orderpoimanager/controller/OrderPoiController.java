package com.hailian.modules.credit.orderpoimanager.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.order.model.TbOrder;
import com.hailian.modules.credit.order.service.OrderService;
import com.hailian.modules.credit.orderpoimanager.service.OrderPoiService;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月17日 下午6:57:23
* @todo
*/
@Api(tag = "订单批量导入", description = "操作订单批量导入")
@ControllerBind(controllerKey = "/credit/orderpoimanager")
public class OrderPoiController extends BaseProjectController{
	private static final String path = "/pages/credit/orderpoimanager/orderpoi_";

	public void index() {
		list();
	}
	
	public void list(){
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		SysUser user = (SysUser) getSessionUser();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy, this);	
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
		
		
	}
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:14:30
	 * @author dyc
	 * @todo   TODO
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/orderpoimanager/listJson", httpMethod = "get", description = "获取订单列表", response = Page.class)
	public void listJson() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//分页查询
		Page<CreditOrderInfo> page=OrderPoiService.service.getOrdersService(pageinator,model,orderBy, this);	
		renderJson(page);
	}
	
}
