package com.hailian.modules.credit.statistics.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;
import com.hailian.modules.credit.statistics.service.OrderStatisticsService;
import com.hailian.system.dict.SysDictDetail;
/**
 * 
* @Title: CusServiceOrderController  
* @Description:  客服订单统计页面
* @author lxy
* @date 2018年12月3日  下午6:41:11
 */
@Api(tag = "客服订单统计", description = "客服订单统计")
@ControllerBind(controllerKey = "/credit/CustomerServiceStatistic")
public class CusServiceOrderController  extends BaseProjectController{
	
	private static final String path = "/pages/credit/usercenter/total_manage/total_order_service.html";
	public void index() {
	List<SysDictDetail> country=	SysDictDetail.dao.find("select * from sys_dict_detail where dict_type=?","country");
	setAttr("country", country);
	List<SysDictDetail> customerId=	SysDictDetail.dao.find("select * from credit_custom_info ");
    setAttr("customer", customerId);
	render(path);
	}
	/**
	 * 
	* @Description: 订单量趋势图
	* @date 2018年12月3日 下午6:42:06
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getCusOrderTrend(){
       String  country = getPara("country");
       String customer=getPara("customer");
       Integer userId = getSessionUser().getUserid();
       List<OrderStatisticsModel> orderTrend =    OrderStatisticsModel.dao.getCusOrderTrend(country, customer);
	   setAttr("CusorderTrend", orderTrend);
	   renderJson(orderTrend);
	}
	/**
	 * 客户订单同比增长率
	* @Description: TODO
	* @date 2018年12月3日 下午8:02:36
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public  void  getCusOrderRate(){
		
	     Integer userId = getSessionUser().getUserid();
	    List<OrderStatisticsModel> list=new ArrayList<OrderStatisticsModel>();
	 	DecimalFormat df=new DecimalFormat("0.0");
	 	  String rate="";
	    List<OrderStatisticsModel> reportDelayOrde =  OrderStatisticsModel.dao.find("select COUNT(ord.id) as num ,cus.name as name from credit_order_info ord "
	    		+ " LEFT JOIN credit_custom_info cus on cus.id=ord.custom_id "
	    		+ " where  DATE_FORMAT( receiver_date, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) and ord.del_flag='0' GROUP BY cus.name ORDER BY ord.id desc limit 0,10");
	    for (OrderStatisticsModel orderStatisticsModel : reportDelayOrde) {
	    	
	    OrderStatisticsModel  model=	 OrderStatisticsModel.dao.findFirst("select COUNT(ord.id) as num ,cus.name as name from credit_order_info ord "
		         		+ " LEFT JOIN credit_custom_info cus on cus.id=ord.custom_id "
		         		+ " where  PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( receiver_date, '%Y%m' ) ) =1 "
		         		+ " and ord.del_flag='0' and cus.name='"+orderStatisticsModel.get("name")+"'  GROUP BY cus.name ORDER BY ord.id desc");
	   int num=Integer.parseInt(orderStatisticsModel.get("num").toString()) ;   
	   int num2=0;
	   if (model!=null) {
		num2 = Integer.parseInt(model.get("num").toString());
	  }
	 
	   rate=df.format(((float)(num-num2)/num2 )*100);
	   OrderStatisticsModel statisticsModel=new OrderStatisticsModel();
	   statisticsModel.set("fax", orderStatisticsModel.get("name"));
	   statisticsModel.set("num", rate);
	   list.add(statisticsModel);
	    }   
	    renderJson(list);
	}
	
	/**
	 * 
	* @Description:客户订单量排名
	* @date 2018年12月8日 下午6:20:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getCustomerOrder(){
    //周，月，年
	String type=	getPara("type");
	List<OrderStatisticsModel> orderTime =	OrderStatisticsModel.dao.getCustomerOrder(type);
	 renderJson(orderTime);
	 
	}

}
