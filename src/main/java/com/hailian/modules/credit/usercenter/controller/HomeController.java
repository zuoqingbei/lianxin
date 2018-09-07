
package com.hailian.modules.credit.usercenter.controller;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.order.model.TbOrder;
import com.hailian.modules.credit.order.service.OrderService;
import com.hailian.modules.credit.usercenter.service.HomeService;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @className HomeController.java
 * @time   2018年9月5日 下午2:00:19
 * @author yangdong
 * @todo   用户控制台
 */


@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/home")
public class HomeController extends BaseProjectController {
	private static final String path = "/pages/credit/usercenter/";
	public void index() {
		render(path + "index.html");
		
	}
	public void list() {
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String companyName=getPara("companyName","");
		String customName=getPara("customName","");
		
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy, this);
		SysUser user2=HomeService.service.getUser(this);
		setAttr("page", page);
		setAttr("attr", model);
		setAttr("user",user2);
		render(path + "index.html");
	}
	/**
	 * 
	 * @time   2018年9月5日 下午5:25:42
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
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

	

}
