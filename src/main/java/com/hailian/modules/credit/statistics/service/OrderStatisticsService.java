package com.hailian.modules.credit.statistics.service;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;

public class OrderStatisticsService {
	public static final OrderStatisticsService service=new OrderStatisticsService();
    /**
     * 
    * @Description: 订单量总量
    * @date 2018年10月24日 下午4:41:04
    * @author: lxy
    * @version V1.0
    * @return
     */
	public  List<OrderStatisticsModel> countOrder(String type){
		return OrderStatisticsModel.dao.getcountOrder(type);	
	}
	/**
	 * 
	* @Description: 订单分布图
	* @date 2018年10月29日 上午10:10:57
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public  List<OrderStatisticsModel> getCountry(String customId){
		return OrderStatisticsModel.dao.getcountry(customId);
	}
	
	/**
	 * 
	* @Description: 总订单趋势图
	* @date 2018年10月29日 上午10:21:06
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getOrderBytime(String type){
		return OrderStatisticsModel.dao.getOrderBytime(type);
	}
	/**
	 * 
	* @Description: 日增幅比例
	* @date 2018年10月29日 下午2:35:45
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyDay(){
		    OrderStatisticsModel model=new OrderStatisticsModel();
			OrderStatisticsModel yestoday=	 OrderStatisticsModel.dao.getRatebyDay();
			String numString=yestoday.get("num").toString();
			int num = Integer.parseInt(numString);
		    List<OrderStatisticsModel>  today = OrderStatisticsModel.dao.getcountOrder("1");
			int size = today.size();
			int add=0;
			if (num>size) {
			   add=-1;	
			 }
			else if(num<size) {
				add=1;	
				}
			String rate="";
			DecimalFormat df=new DecimalFormat("0.0");
         	int re=	Math.abs(num-size);
			if (num>0) {
				rate=df.format(((float)re/num)*100);
			}else {
				rate=df.format((float)re/1);
			} 
			model.set("num", rate);
			model.set("remarks", add);
			return model;
	}
	/**
	 * 
	* @Description: 月比例
	* @date 2018年10月29日 下午3:37:05
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyMonth(){
		   OrderStatisticsModel model=new OrderStatisticsModel();
			OrderStatisticsModel lastMonth=	 OrderStatisticsModel.dao.getRatebyMonth();
			int num =Integer.parseInt(lastMonth.get("num").toString());
		    List<OrderStatisticsModel>  month = OrderStatisticsModel.dao.getcountOrder("3");
			int size = month.size();
			int add=0;
			if (num>size) {
			   add=-1;	
				}else if(num<size) {
				add=1;	
				}
			String rate="";
			DecimalFormat df=new DecimalFormat("0.0");
         	int re=	Math.abs(num-size);
         	if (num>0) {
				rate=df.format(((float)re/num)*100);
			}else {
				rate=df.format((float)re/1);
			} 
			model.set("num", rate);
			model.set("remarks", add);
			return model;       
	}
	/**
	 * 
	* @Description: 年比例
	* @date 2018年10月29日 下午4:02:16
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public OrderStatisticsModel getRatebyYear(){
		OrderStatisticsModel model=new OrderStatisticsModel();
		OrderStatisticsModel lastYear=	 OrderStatisticsModel.dao.getRatebyYear();
		int num =Integer.parseInt(lastYear.get("num").toString()) ;
	    List<OrderStatisticsModel>  year = OrderStatisticsModel.dao.getcountOrder("5");
		int size = year.size();
		int add=0;
		if (num>size) {
		   add=-1;	
			}else if(num<size){
			add=1;	
			}
		String rate="";
		DecimalFormat df=new DecimalFormat("0.0");
     	int re=	Math.abs(num-size);
     	if (num>0) {
			rate=df.format(((float)re/num)*100);
		}else {
			rate=df.format((float)re/1);
		} 
		model.set("num", rate);
		model.set("remarks", add);
		return model;       
	}
	/**
	 * 
	* @Description: 总订单延误率，按周，月，年
	* @date 2018年10月31日 上午10:39:16
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getdelayBytime(String type){
		return OrderStatisticsModel.dao.getdelaybyTime(type);
	}
	/**
	 * 
	* @Description: 员工当日情况
	* @date 2018年10月31日 上午11:42:48
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<OrderStatisticsModel> getcustomDay(String time, String sortname,String sortorder){
		return OrderStatisticsModel.dao.getcustomday(time,sortname,sortorder);
	}
	
}