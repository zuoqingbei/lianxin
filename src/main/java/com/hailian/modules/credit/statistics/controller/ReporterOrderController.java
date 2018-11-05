package com.hailian.modules.credit.statistics.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;
/**
 * 
* @Title: ReporterOrderController  
* @Description:  报告员查看订单统计页面
* @author lxy
* @date 2018年10月31日  下午1:59:39
 */
@Api(tag = "报告员订单统计", description = "报告员订单统计")
@ControllerBind(controllerKey = "/credit/reporterOrderStatistic")
public class ReporterOrderController  extends BaseProjectController{
	
	private static final String path = "/pages/credit/usercenter/total_manage/total_order_reporter.html";
	public void index() {
		render(path);
	}
	/**
	 * 
	* @Description: 订单量趋势
	* @date 2018年10月31日 下午3:37:00
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getOrderTrend(){
       String  type = getPara("type");
       Integer userId = getSessionUser().getUserid();
       List<OrderStatisticsModel> orderTrend =    OrderStatisticsModel.dao.getOrderTrend(type, userId+"");
	   setAttr("orderTrend", orderTrend);
	   renderJson(orderTrend);
	}
	/**
	 * 
	* @Description: 订单延迟率
	* @date 2018年10月31日 下午5:29:53
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public  void  getOrderdelayReport(){
		 String  type = getPara("type");
	     Integer userId = getSessionUser().getUserid();
	    List<OrderStatisticsModel> reportDelayOrde =  OrderStatisticsModel.dao.getReportdelayOrder(type, userId+"");
	    setAttr("reportDelay",reportDelayOrde);
	    renderJson(reportDelayOrde);
	}
	/**
	 * 
	* @Description: 订单完成率
	* @date 2018年10月31日 下午5:32:06
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getorderFinsh(){
		 String  type = getPara("type");
	     Integer userId = getSessionUser().getUserid();
	   List<OrderStatisticsModel> finshOrder =   OrderStatisticsModel.dao.getOrderFinsh(type, userId+"");
	    setAttr("finshOrder", finshOrder);
	    renderJson(finshOrder);
	}

}
