package com.hailian.modules.admin.ordermanager.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportType;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className OrdermanagerController.java
 * @time   2018年8月31日 下午3:41:17
 * @author yangdong
 * @todo   TODO
 * 订单管理
 */
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
	
		try {
			int id=getParaToInt("id");
			CreditOrderInfo coi=CreditOrderInfo.dao;
			coi.set("id",id);
			coi.set("del_flag", "1");
			OrderManagerService.service.deleteOrder(coi, this);
			renderMessage("删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderMessage("删除失败");
		}
		
	}
	/**
	 * 
	 * @time   2018年8月24日 下午6:15:54
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 转到订单修改页面
	 */
	@ApiOperation(url = "/admin/ordermanager/edit",httpMethod="get", 
			description = "定位修改订单页面")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void edit() {
		int id=getParaToInt("id");
		CreditOrderInfo coi=OrderManagerService.service.editOrder(id,this);
		String continent=coi.get("continent").toString();
//		String countryid=coi.get("country").toString();
//		String createBy=coi.getStr("custom_id");
//		CountryModel cm=OrderManagerService.service.getCountryType(countryid);
//		String type=cm.get("type");
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CreditReportType> reportType=OrderManagerService.service.getReportType();
		List<SysDictDetail> orderType=OrderManagerService.service.getDictByType("ordertype");
		List<SysDictDetail> reportLanguage=OrderManagerService.service.getDictByType("language");
		List<CountryModel> country=OrderManagerService.service.getCountrys(continent);
		List<SysDictDetail> speed=null;
//		if("0".equals(type)) {
			//国外
			speed=OrderManagerService.service.getDictByType("reportsspeed");
//		}else {
			//国内
//			speed=OrderManagerService.service.getDictByType("exterreportsspeed");
//		}
		
		setAttr("model", coi);
		setAttr("user",user);
		setAttr("customs",customs);
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
	 * 修改订单
	 */
	@ApiOperation(url = "/admin/ordermanager/save",httpMethod="post", 
			description = "修改订单")
	@Params(value = { 
		@Param(name = "CreditOrderInfo", description = "订单", required = false, dataType = "Model"),
		})
	public void save() {
		int id=getParaToInt("id");
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		SysUser user = (SysUser) getSessionUser();
		try {
			OrderManagerService.service.modifyOrder(id,model,user,this);
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
	 * 根据传入id的不同来区分修改添加
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
			description = "查看订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void view() {
		int id=getParaToInt("id");
		CreditOrderInfo model=OrderManagerService.service.editOrder(id,this);
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
	 * 转到添加页面
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
			description = "查看订单")
	public void add() {
		List<CreditReportType> reportType=OrderManagerService.service.getReportType();
		List<SysDictDetail> orderType=OrderManagerService.service.getDictByType("ordertype");
		List<SysDictDetail> language=OrderManagerService.service.getDictByType("language");
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		//默认国内
		List<SysDictDetail> speed=OrderManagerService.service.getDictByType("orderspeed");
		List<CountryModel> country=OrderManagerService.service.getCountrys("157");
		setAttr("user",user);
		setAttr("reporttype",reportType);
		setAttr("reportlanguage",language);
		setAttr("customs",customs);
		setAttr("ordertype",orderType);
		setAttr("speed",speed);
		setAttr("country",country);
		render(path + "add.html");
	}
	/**
	 * 
	 * @time   2018年8月31日 下午3:28:55
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 获取速度
	 */
	@ApiOperation(url = "/admin/ordermanager/view",httpMethod="post", 
	description = "查看订单")
	@Params(value = { 
	@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
	})
	public void getSpeed() {
		String type=getPara("countrytype").toString();
		List<SysDictDetail> speed=null;

			speed=OrderManagerService.service.getDictByType("orderspeed");

		renderJson(speed);
	}
	
}
