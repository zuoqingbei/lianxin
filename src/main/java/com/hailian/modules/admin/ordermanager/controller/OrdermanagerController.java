package com.hailian.modules.admin.ordermanager.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.base.SessionUser;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.db.SQLUtils;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
@Api(tag = "订单菜单路由", description = "订单菜单")
@ControllerBind(controllerKey = "/admin/ordermanager")
public class OrdermanagerController extends BaseProjectController{
	private static final String path = "/pages/admin/ordermanager/order_";

	/**
	 * 
	 * @time   2018年8月24日 下午6:16:22
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager",httpMethod="post", 
			description = "获取订单列表")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单信息", required = true, dataType = "Model"),
		})
	public void index() {
		list();
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:36
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/list",httpMethod="post", 
			description = "获取订单列表")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单信息", required = true, dataType = "Model"),
		})
	public void list() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		SysUser user = (SysUser) getSessionUser();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy,user, this);	
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:02
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/delete",httpMethod="post", 
			description = "删除订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void delete() {
		String id=getPara();
		CreditOrderInfo coi=CreditOrderInfo.dao;
		coi.set("id",id);
		coi.set("del_flag", "1");
		OrderManagerService.service.deleteOrder(coi, this);
		list();
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:54
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/edit",httpMethod="get", 
			description = "定位修改订单页面")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void edit() {
		String id=getPara();
		CreditOrderInfo coi=OrderManagerService.service.editOrder(id,this);
		SysUser user = (SysUser) getSessionUser();
		setAttr("model", coi);
		setAttr("user",user);
//		renderJson(coi);
		render(path + "edit.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:44
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/save",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单", required = false, dataType = "Model"),
		})
	public void save() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String changeReason=getAttr("changeReason");
		SysUser user = (SysUser) getSessionUser();
		OrderManagerService.service.modifyOrder(model,changeReason,user,this);
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:48
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void view() {
		String id=getPara("id");
		CreditOrderInfo model=OrderManagerService.service.orderView(id,this);
		setAttr("model", model);
//		renderJson(model);
		render(path + "view.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:56
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	public void add() {
		render(path + "add.html");
	}
	public void addOrder() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		Boolean flag=OrderManagerService.service.saveOrder(model,this);
		if(flag) {
			renderMessage("订单创建成功");
		}else {
			renderMessage("订单创建失败");
		}
	}
	
}
