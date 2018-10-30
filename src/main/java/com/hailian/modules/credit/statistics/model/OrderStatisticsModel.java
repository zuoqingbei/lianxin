package com.hailian.modules.credit.statistics.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "credit_order_info")
public class OrderStatisticsModel extends BaseProjectModel<OrderStatisticsModel>{
	private static final long serialVersionUID = 1L;
	public static final OrderStatisticsModel dao=new OrderStatisticsModel();
	/**
	 * 
	* @Description: 当日订单量
	* @date 2018年10月24日 下午4:42:28
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getcountOrder(String type){
		String sql="";
		if (type.equals("1")) {
			//当日订单总量
			sql="SELECT * from credit_order_info WHERE date(create_date) = curdate()";	
		}
		if(type.equals("2")){
			//前14日订单量
		    sql="select receiver_date,count(id) as id from credit_order_info where date_sub(curdate(), INTERVAL 14 DAY) <= create_date GROUP BY  receiver_date ORDER BY create_date DESC";
		} 
		if(type.equals("3")){
			   //查询当月订单量
			sql="SELECT * from credit_order_info where date_format(create_date, '%Y%m') = date_format(curdate() , '%Y%m')";
		} 
		if(type.equals("4")){
			 //查询当年12个月的每个月总订单量
		    sql="SELECT COUNT(id) ad id,month  from credit_order_info WHERE YEAR(receiver_date)=YEAR(NOW()) GROUP BY month";
		} 
		if(type.equals("5")){
			//一年中的总订单量
		    sql="SELECT * from credit_order_info  WHERE YEAR(create_date)=YEAR(NOW())";
		} 
		if(type.equals("6")){
			  //所有订单年份的总订单量
		    sql="SELECT COUNT(id) as id,year from  credit_order_info GROUP BY `year` ORDER BY receiver_date DESC";
		}

		return OrderStatisticsModel.dao.find(sql);	
	}
	
	
}
