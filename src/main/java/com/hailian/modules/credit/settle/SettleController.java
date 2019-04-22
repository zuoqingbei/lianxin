package com.hailian.modules.credit.settle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
@Api(tag = "订单结算", description = "订单结算")
@ControllerBind(controllerKey = "/credit/settle")
public class SettleController extends BaseController{
	private static final String path = "/pages/credit/usercenter/total_manage/";
	public void index() throws ParseException {
		//查询客户id，跟代理id
	List<CustomInfoModel> customer=	CustomInfoModel.dao.find("select id from credit_custom_info where del_flag=0");
	setAttr("customer", customer);
	 List<AgentModel> agent=  AgentModel.dao.find("select agent_id from credit_agent where del_flag=0");
	setAttr("agent", agent);
	render(path+"total_settle.html");
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
		String time = getPara("time");
		String customerId=getPara("customerId");
		String agentId=getPara("agentId");
		String sortname=getPara("sortName");
		String sortorder=getPara("sortOrder");
		if(!StringUtils.isNotBlank(sortorder)) {
			sortorder="desc";
		}
		Paginator pageinator=getPaginator();
		Page<CreditOrderInfo> page=OrderManagerService.service.getSettleOrders(pageinator,customerId,agentId,time,sortname,sortorder);
		int total= page.getTotalRow();
		List<CreditOrderInfo> rows=page.getList();
		ResultType resultType=new ResultType(total,rows);
		renderJson(resultType);
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
	/**
	 * 
	* @Description: 结算导出
	* @date 2018年11月15日 下午5:02:15
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void SettleExport() {
		String fileName="订单结算-"+sdf.format(new Date())+".xlsx";
		String time = getPara("time");
		String customerId=getPara("customerId");
		String agentId=getPara("agentId");
		
		List<CreditOrderInfo> infos  = OrderManagerService.service.exportSettle(customerId, agentId, time);
	   com.hailian.util.SettleExport export=new com.hailian.util.SettleExport(infos);
		 try {
			 fileName=new String(fileName.getBytes("GBK"), "ISO-8859-1");
			export.doExport(getResponse(), fileName);
		renderJson("导出成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson("导出失败");
		}
		
		
	}
}
