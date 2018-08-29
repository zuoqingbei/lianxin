package com.hailian.modules.credit.ordermanager.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.ordermanager.model.CreditReportType;
import com.hailian.modules.credit.ordermanager.service.OrderManagerService;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
@Api(tag = "订单菜单路由", description = "订单菜单")
@ControllerBind(controllerKey = "/credit/ordermanager")
public class OrdermanagerController extends BaseProjectController{
	private static final String path = "/pages/credit/ordermanager/order_";

	/**
	 * 
	 * @time   2018年8月24日 下午6:16:22
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/ordermanager",httpMethod="post", 
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
	@ApiOperation(url = "/credit/ordermanager/list",httpMethod="post", 
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
	@ApiOperation(url = "/credit/ordermanager/delete",httpMethod="post", 
			description = "删除订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void delete() {
		String id=getPara("id");
		CreditOrderInfo coi=CreditOrderInfo.dao;
		coi.set("id",id);
		coi.set("del_flag", "1");
		OrderManagerService.service.deleteOrder(coi, this);
		render(path + "list.html");
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:54
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/ordermanager/edit",httpMethod="get", 
			description = "定位修改订单页面")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void edit() {
		String id=getPara("id");
		CreditOrderInfo coi=OrderManagerService.service.editOrder(id,this);
		String continent=coi.get("continent").toString();
		String countryid=coi.get("country").toString();
		CountryModel cm=OrderManagerService.service.getCountryType(countryid);
		String type=cm.get("type");
		SysUser user = (SysUser) getSessionUser();
		List<CreditReportType> reportType=OrderManagerService.service.getReportType();
		List<SysDictDetail> orderType=OrderManagerService.service.getOrderType();
		List<SysDictDetail> reportLanguage=OrderManagerService.service.getReportLanguage();
		List<CountryModel> country=OrderManagerService.service.getCountrys(continent);
		List<SysDictDetail> speed=null;
		if("0".equals(type)) {
			//国外
			speed=OrderManagerService.service.getspeed("interreportsspeed");
		}else {
			//国内
			speed=OrderManagerService.service.getspeed("exterreportsspeed");
		}
		
		setAttr("model", coi);
		setAttr("user",user);
		setAttr("reporttype",reportType);
		setAttr("reportlanguage",reportLanguage);
		setAttr("country",country);
		setAttr("speed",speed);
		setAttr("ordertype",orderType);
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
	@ApiOperation(url = "/credit/ordermanager/save",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单", required = false, dataType = "Model"),
		})
	public void save() {
		String id=getPara("id").toString();
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		model.set("id", id);
		String changeReason=getAttr("changeReason");
		SysUser user = (SysUser) getSessionUser();
		try {
			OrderManagerService.service.modifyOrder(model,changeReason,user,this);
			renderMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			renderMessage("保存失败");
		}
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:16:48
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/ordermanager/view",httpMethod="post", 
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
	public void getSpeed() {
		String type=getPara("countrytype").toString();
		List<SysDictDetail> speed=null;
		if("0".equals(type)) {
			//国外
			speed=OrderManagerService.service.getspeed("interreportsspeed");
		}else {
			//国内
			speed=OrderManagerService.service.getspeed("exterreportsspeed");
		}
		renderJson(speed);
	}
	
}
