package com.hailian.modules.admin.ordermanager.controller;

import java.util.List;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportLanguage;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
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
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy, this);	
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
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CountryModel> country=OrderManagerService.service.getCountrys(continent);
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("model", coi);
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("country",country);
		setAttr("company",company);
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
	@Before(Tx.class)
	public void save() {
		int id=getParaToInt("id");
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		SysUser user = (SysUser) getSessionUser();
		try {
			OrderManagerService.service.modifyOrder(id,model,user,this);
			OrderManagerService.service.addOrderHistory(id, user);
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
			description = "增加订单")
	public void add() {
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		//默认国内
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("company",company);
		setAttr("model",new CreditOrderInfo());
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
	/**
	 * 
	 * @time   2018年9月3日 下午4:27:56
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 * 获取订单时间
	 */
	@ApiOperation(url = "/admin/ordermanager/getTime",httpMethod="get", 
			description = "返回所用时间")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "speed", description = "速度", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getTime() {
		String countryType=getPara("countrytype", "");
		String speed=getPara("speed", "");
		String reporttype=getPara("reporttype", "");
		CreditReportUsetime usetime=OrderManagerService.service.getTime(countryType,speed,reporttype);
			renderJson(usetime);

	}
	/**
	 * 
	 * @time   2018年9月3日 下午7:26:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回价格")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "speed", description = "速度", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getPrice() {
		String countryType=getPara("countrytype", "");
		String speed=getPara("speed", "");
		String reporttype=getPara("reporttype", "");
		CreditReportPrice price=OrderManagerService.service.getPrice(countryType,speed,reporttype);
		renderJson(price);

	}
	/**
	 * 
	 * @time   2018年9月4日 下午2:13:00
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回语言")
			@Params(value = { 
			@Param(name = "countrytype", description = "国家类型", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String")
			})
	public void getLanguage() {
		String countryType=getPara("countrytype", "");
		String reporttype=getPara("reporttype", "");
		List<CreditReportLanguage> language=OrderManagerService.service.getLanguage(countryType,reporttype);
		renderJson(language);
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:08:44
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/admin/ordermanager/getPrice",httpMethod="get", 
			description = "返回公司")
	public void getCompany() {
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		renderJson(company);
	}
	
}
