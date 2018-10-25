package com.hailian.modules.credit.statistics.service;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.hailian.modules.credit.statistics.model.OrderStatisticsModel;

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
	
}