package com.hailian.modules.credit.statistics.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;
import com.hailian.modules.credit.statistics.service.OrderStatisticsService;
/**
 * 
* @Title: OrderStatisticsController  
* @Description:  订单统计
* @author lxy
* @date 2018年10月24日  下午2:36:19
 */
public class OrderStatisticsController extends BaseProjectController{

	private static final String path = "/pages/credit/usercenter/orderstatistics/list_";
	public void index() {
		
	}
	/**
	 * 
	* @Description: 当日总订单量，当月总订单量，当年总订单量    type代表查询类型  1：当日 ，2：前14日 ，3：当月，4：12个月每个月订单总量 ，
	*               5：当年，6所有订单按年总量
	* @date 2018年10月24日 下午2:55:46
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void countOrder(){
		List<OrderStatisticsModel> orderStatistic=  OrderStatisticsService.service.countOrder("1");
	    setAttr("orderCount", orderStatistic.size());
	    List<OrderStatisticsModel> beforeOrder= OrderStatisticsService.service.countOrder("2");
	    setAttr("before", beforeOrder);
	    List<OrderStatisticsModel> month= OrderStatisticsService.service.countOrder("3");
	    setAttr("month", month);
	    List<OrderStatisticsModel> allMonth = OrderStatisticsService.service.countOrder("4");
	    setAttr("allMonth", allMonth);
	    List<OrderStatisticsModel> year =   OrderStatisticsService.service.countOrder("5");
	    setAttr("yaer", year);
	    List<OrderStatisticsModel> allYear = OrderStatisticsService.service.countOrder("6");
	    setAttr("allYear", allYear);
	
	}
	
}
