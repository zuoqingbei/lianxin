package com.hailian.modules.credit.statistics.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.stat.ast.Else;

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
			sql="SELECT * from credit_order_info WHERE date(create_date) = curdate() and del_flag='0'";	
		}
		if(type.equals("2")){
			//前14日订单量
		    sql="select receiver_date as time,count(id) as num from credit_order_info where date_sub(curdate(), INTERVAL 14 DAY) <= create_date and del_flag='0' GROUP BY  receiver_date ORDER BY create_date DESC ";
		} 
		if(type.equals("3")){
			   //查询当月订单量
			sql="SELECT * from credit_order_info where date_format(create_date, '%Y%m') = date_format(curdate() , '%Y%m') and del_flag='0'";
		} 
		if(type.equals("4")){
			 //查询当年12个月的每个月总订单量
		    sql="SELECT COUNT(id) as num,month  from credit_order_info WHERE YEAR(receiver_date)=YEAR(NOW()) and del_flag='0' GROUP BY month";
		} 
		if(type.equals("5")){
			//一年中的总订单量
		    sql="SELECT * from credit_order_info  WHERE YEAR(create_date)=YEAR(NOW()) and del_flag='0'";
		} 
		if(type.equals("6")){
			  //所有订单年份的总订单量
		    sql="SELECT COUNT(id) as num,year from  credit_order_info where 1=1 and del_flag='0' GROUP BY `year` ORDER BY receiver_date DESC";
		}
		//客户订单量排名
		if (type.equals("7")) {
			sql="select COUNT(ord.id) as num ,cus.name as name from credit_order_info ord LEFT JOIN credit_custom_info cus on cus.id=ord.custom_id where ord.del_flag='0' GROUP BY cus.name ORDER BY ord.id desc";
		}
		//各报告类型占比
        if (type.equals("8")) {
			sql="SELECT COUNT(info.id) as num,type.name as name from credit_order_info info LEFT JOIN credit_report_type  type on info.report_type=type.id where info.del_flag='0' GROUP BY info.report_type";
		} 
        //各员工延误率
        if (type.equals("9")) {
			sql="select COUNT(o.id) as num, cus.name as name from credit_order_info o "
					+ " LEFT JOIN credit_custom_info cus on o.custom_id=cus.id"
					+ "  where datediff(o.submit_date,o.end_date)>0 and o.del_flag='0' GROUP BY o.custom_id ";
		}
		return OrderStatisticsModel.dao.find(sql);	
	}
	/**
	 * 
	* @Description: 前一日总订单量
	* @date 2018年10月29日 上午10:47:38
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyDay(){
		String sql="SELECT COUNT(*) as num from credit_order_info where  TO_DAYS(NOW( ) ) - TO_DAYS( create_date) <= 1 and del_flag='0'";
		return OrderStatisticsModel.dao.findFirst(sql);
	}
	
	/**
	 * 
	* @Description: 前一个月
	* @date 2018年10月29日 下午1:48:43
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyMonth(){
		String sql="SELECT COUNT(*) as num from credit_order_info WHERE PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( create_date, '%Y%m' ) ) =1 and del_flag='0'";
		return OrderStatisticsModel.dao.findFirst(sql);
	}
	
	/**
	 * 
	* @Description: 前一年
	* @date 2018年10月29日 下午1:49:23
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyYear(){
		String sql="select COUNT(*) as num from credit_order_info where year(create_date)=year(date_sub(now(),interval 1 year)) and del_flag='0'";
		return OrderStatisticsModel.dao.findFirst(sql);
	}
	/**
	 * 
	* @Description: 根据客户id查询订单分布
	* @date 2018年10月26日 上午11:00:09
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getcountry(String customId){
		String sql="";
		if (StringUtils.isNotBlank(customId)) {
			sql="select DISTINCT c.name as country from credit_order_info o LEFT JOIN credit_country c on o.country=c.id where  o.del_flag='0'o.custom_id='"+customId+"'";
		}else {
			sql="select DISTINCT c.name as country from credit_order_info o LEFT JOIN credit_country c on o.country=c.id where o.del_flag='0' ";
		}
		
		return OrderStatisticsModel.dao.find(sql);
	}
	/**
	 * 
	* @Description: 总订单趋势图，按周，月，年
	* @date 2018年10月26日 下午4:13:52
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getOrderBytime(String type){
		String sql="";
		if (type.equals("week")) {
			sql="select receiver_date as time,count(id) as num from credit_order_info where date_sub(curdate(), INTERVAL 7 DAY) <= create_date and del_flag='0' GROUP BY  receiver_date ORDER BY receiver_date asc";
		}
		if (type.equals("month")) {
			sql="SELECT COUNT(id) as num,month as time  from credit_order_info WHERE YEAR(receiver_date)=YEAR(NOW()) and del_flag='0' GROUP BY month order by receiver_date asc";
		}
		if (type.equals("year")) {
			sql="SELECT COUNT(id) as num,year as time from  credit_order_info  where  del_flag='0' GROUP BY `year` ORDER BY receiver_date asc";
		}
		return OrderStatisticsModel.dao.find(sql);
	}
	/**
	 * 
	* @Description: 总订单延误率，按周，月，年
	* @date 2018年10月29日 上午9:18:32
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public  List<OrderStatisticsModel> getdelaybyTime(String type){
		String sql="";
		
		if (type.equals("week")) {
			sql="select receiver_date as time,COUNT(receiver_date) as num  from credit_order_info where datediff(submit_date,end_date)>0  and date_sub(curdate(), INTERVAL 14 DAY) <= receiver_date and del_flag='0' GROUP BY receiver_date order BY receiver_date asc";
		}
		if (type.equals("month")) {
			sql="select `month` as time,COUNT(receiver_date) as num  from credit_order_info where datediff(submit_date,end_date)>0  and YEAR(receiver_date)=YEAR(NOW()) and del_flag='0' GROUP BY month order BY receiver_date asc";
		}
		if (type.equals("year")) {
			sql="select `year` as time,COUNT(`year`) as num  from credit_order_info where datediff(submit_date,end_date)>0 and del_flag='0'  GROUP BY `year` order BY receiver_date asc";
		}
		return OrderStatisticsModel.dao.find(sql.toString());
	}
	/**
	 * 
	* @Description: 各员工当日情况
	* @date 2018年10月31日 上午11:41:36
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getcustomday(String time, String sortname,String sortorder){
		String sql="select cu.name as name,COUNT(o.id) as num ,(select COUNT(od.id) as num from credit_order_info od"
				+ " where '"+time+"' =date_format(od.receiver_date,'%Y-%m-%d')and od.status='314' and o.id=od.id) as num2 "
	            +" from credit_order_info o"
				+ " LEFT JOIN credit_custom_info cu on o.custom_id=cu.id"
				+ " where '"+time+"' =date_format(o.receiver_date,'%Y-%m-%d')  and o.del_flag='0' GROUP BY o.custom_id";
		if (StringUtils.isNotBlank(sortname)) {
			sql+=" order by "+sortname+" "+sortorder;
		}
		return OrderStatisticsModel.dao.find(sql);
	}
   /**
    * 
   * @Description: 报告-订单量趋势 
   * @date 2018年10月31日 下午4:21:40
   * @author: lxy
   * @version V1.0
   * @return
    */
	public  List<OrderStatisticsModel> getOrderTrend(String type,String id){
		String sql="";
		if (type.equals("week")) {
		sql="select receiver_date as time,count(id) as num from credit_order_info "
		  + " where (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
		  + " and date_sub(curdate(), INTERVAL 7 DAY) <= create_date "
		  + " and del_flag='0' GROUP BY  receiver_date ORDER BY create_date asc";	
		}
		if (type.equals("month")) {
			sql="SELECT COUNT(id) as num,month as time  from credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			  + " and del_flag='0' and YEAR(receiver_date)=YEAR(NOW()) GROUP BY month order by receiver_date asc";
		}
		if (type.equals("year")) {
			sql="SELECT COUNT(id) as num,year as time from  credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			  + " and del_flag='0' GROUP BY `year` ORDER BY `year` asc";
		}
		return OrderStatisticsModel.dao.find(sql);
	}
	/**
	 * 
	* @Description: 报告-订单延迟率
	* @date 2018年10月31日 下午4:36:39
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getReportdelayOrder(String type,String id){
		String sql="";
		if (type.equals("week")) {
			sql="select receiver_date as time,COUNT(receiver_date) as num  from credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			  + "  and datediff(submit_date,end_date)>0  and date_sub(curdate(), INTERVAL 14 DAY) <= receiver_date and del_flag='0' GROUP BY receiver_date order BY receiver_date asc";
		}
		if (type.equals("month")) {
			sql="select `month` as time,COUNT(receiver_date) as num  from credit_order_info "
			   + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			   + " and datediff(submit_date,end_date)>0  and YEAR(receiver_date)=YEAR(NOW()) and del_flag='0' GROUP BY month order BY receiver_date asc";
		}
		if (type.equals("year")) {
			sql="select `year` as time,COUNT(`year`) as num  from credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
              + " and datediff(submit_date,end_date)>0  and del_flag='0'  GROUP BY `year` order BY `year` asc";
		}
		return OrderStatisticsModel.dao.find(sql);
	}
	/**
	 * 
	* @Description: 订单完成率
	* @date 2018年10月31日 下午4:52:00
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getOrderFinsh(String type,String id){
		String sql="";
		if (type.equals("week")) {
			sql="select receiver_date as time ,COUNT(receiver_date) as num  from credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			  + " and status='314' and del_flag='0' and datediff(submit_date,end_date)>0  and date_sub(curdate(), INTERVAL 14 DAY) <= receiver_date GROUP BY receiver_date order BY receiver_date asc";
		}
		if (type.equals("month")) {
			sql="select `month` as time,COUNT(receiver_date) as num  from credit_order_info "
			   + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
			   + " and status='314' and del_flag='0' and datediff(submit_date,end_date)>0  and YEAR(receiver_date)=YEAR(NOW()) GROUP BY month order BY month asc";
		}
		if (type.equals("year")) {
			sql="select `year` as time,COUNT(`year`) as num  from credit_order_info "
			  + " WHERE (report_user='"+id+"'or translate_user='"+id+"' or IQC='"+id+"' OR analyze_user='"+id+"') "
              + " and status='314' and del_flag='0' and datediff(submit_date,end_date)>0   GROUP BY `year` order BY `year` asc";
		}
		return OrderStatisticsModel.dao.find(sql);
	}
}
