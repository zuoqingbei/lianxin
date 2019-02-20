package com.hailian.modules.credit.statistics.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;
import com.hailian.modules.credit.statistics.service.OrderStatisticsService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
* @Title: OrderStatisticsController  
* @Description:  订单统计
* @author lxy
* @date 2018年10月24日  下午2:36:19
 */
@Api(tag = "订单统计", description = "订单统计")
@ControllerBind(controllerKey = "/credit/orderStatistic")
public class OrderStatisticsController extends BaseProjectController{

	private static final String path = "/pages/credit/usercenter/total_manage/total_order.html";
	
	/**
	 * 
	* @Description: 当日总订单量，当月总订单量，当年总订单量    type代表查询类型  1：当日 ，2：前14日 ，3：当月，4：12个月每个月订单总量 ，
	*               5：当年,6:所有订单按年总量,7:客户订单量排名,8:各报告类型占比,9:各员工延误率
	* @date 2018年10月24日 下午2:55:46
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void index(){
		List<OrderStatisticsModel> orderStatistic=  OrderStatisticsService.service.countOrder("1");
	    setAttr("orderCount", orderStatistic.size());
	    OrderStatisticsModel dayModel = OrderStatisticsService.service.getRatebyDay();
	    setAttr("Dayrate", dayModel);//比例值
	    List<OrderStatisticsModel> month= OrderStatisticsService.service.countOrder("3");
	    setAttr("month", month.size());
	    OrderStatisticsModel monthrate = OrderStatisticsService.service.getRatebyMonth();
	    setAttr("monthrate", monthrate);
	    List<OrderStatisticsModel> year =   OrderStatisticsService.service.countOrder("5");
	    setAttr("year", year.size());
	    OrderStatisticsModel yearRate= OrderStatisticsService.service.getRatebyYear();
	    setAttr("yearRate", yearRate);
	    List<CustomInfoModel> customer=CustomInfoModel.dao.getAllcutomer();
	    setAttr("customer", customer);
	  
	 render(path);
	}
	/**
	 * 
	* @Description: TODO
	* @date 2018年11月2日 下午6:08:00
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void orderListintface(){
		String type=getPara("type");
		List<OrderStatisticsModel> result=new ArrayList<OrderStatisticsModel>();
		if (type.equals("1")) {
		//近7天的日订单量
		 result= OrderStatisticsService.service.countOrder("2");
	
		}if (type.equals("2")) {
	    //月总订单量	
			result = OrderStatisticsService.service.countOrder("4");
	
		}if (type.equals("3")) {
		 //年总订单量   
		    result = OrderStatisticsService.service.countOrder("6"); 
		    
		}if (type.equals("4")) {
		 //客户订单量排名
		   result = OrderStatisticsService.service.countOrder("7");
			
		}if (type.equals("5")) {
		//各类型占比	
		   result=   OrderStatisticsService.service.countOrder("8");
	
		}if (type.equals("6")) {
		//员工延误率
		   result=  OrderStatisticsService.service.countOrder("9");

		}
		renderJson(result);
	}
	
	/**
	 * 
	* @Description: 订单分布图 按客户查询各自的订单分布情况
	* @date 2018年10月26日 上午10:33:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void Ordermap(){
		 String customId=getPara("customId");
	List<OrderStatisticsModel> country =	   OrderStatisticsService.service.getCountry(customId);
	 renderJson(country);
	}
	/**
	 * 
	* @Description: 查看订单量 按周，月 ，年查询
	* @date 2018年10月26日 下午4:10:22
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void orderBytime(){
		String type=getPara("type");
	List<OrderStatisticsModel> orderTime =	OrderStatisticsService.service.getOrderBytime(type);
	 renderJson(orderTime);
	}
	/**
	 * 
	* @Description: 总订单延误率，按周月年
	* @date 2018年10月31日 上午10:45:15
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void delayOrder(){
		String type=getPara("type");
		List<OrderStatisticsModel> delayorder=OrderStatisticsService.service.getdelayBytime(type);
	    renderJson(delayorder);
	}
	/**
	 * 
	* @Description: 员工当日情况
	* @date 2018年10月31日 上午11:54:44
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void customDay(){
		String time=getPara("time");
		String sortname=getPara("sortName");
		//防止sql注入,所以不能这么弄 建议:可以用switch case 限定sortname范围
		//if(!StringUtils.isNotBlank(sortname)) { 
			sortname="num";
		//}
		String sortorder=getPara("sortOrder");
		if(!StringUtils.isNotBlank(sortorder)) {
			sortorder="desc";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		List<OrderStatisticsModel> customDay = null;
		
		//为了防止time参数sql注入,判断其格式,格式错误则over
		try {
			sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			renderJson(new ResultType(0,null));
			return;
		}
		 
		
	 customDay = OrderStatisticsService.service.getcustomDay(time,sortname,sortorder);
	int total=customDay.size();
	List<OrderStatisticsModel> rows=customDay;
	ResultType resultType=new ResultType(total,rows); 
	renderJson(resultType);
	}

}
