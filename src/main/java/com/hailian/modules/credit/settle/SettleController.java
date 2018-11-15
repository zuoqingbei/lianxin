package com.hailian.modules.credit.settle;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
@Api(tag = "订单结算", description = "订单结算")
@ControllerBind(controllerKey = "/credit/settle")
public class SettleController extends BaseController{
	private static final String path = "/pages/credit/orderallocationrule/orderrule_";
	public void index() throws ParseException {
		list();
	}
	/**
	 * 
	* @Description: 结算订单列表
	* @date 2018年11月14日 下午3:32:44
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void list() throws ParseException {
		CreditOrderInfo model = new CreditOrderInfo();
		
		String time = getPara("time");
		String sortname=getPara("sortName");
		String customerId=getPara("customerId");
		String agentId=getPara("agentId");
		if(!StringUtils.isNotBlank(sortname)) {
			sortname="create_date";
		}
		if("receiver_date".equals(sortname)) {
			sortname="create_date";
		}
		String sortorder=getPara("sortOrder");
		if(!StringUtils.isNotBlank(sortorder)) {
			sortorder="desc";
		}
		
		Paginator pageinator=getPaginator();
		Page<CreditOrderInfo> page=OrderManagerService.service.getSettleOrders(pageinator,model,customerId,agentId,time,sortname,sortorder);
		int total= page.getTotalRow();
		List<CreditOrderInfo> rows=page.getList();
		ResultType resultType=new ResultType(total,rows);
		renderJson(resultType);
	}
}
