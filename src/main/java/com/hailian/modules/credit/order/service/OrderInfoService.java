package com.hailian.modules.credit.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.order.model.CreditOrderInfo;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className OrderInfoService.java
 * @time   2018年8月23日 上午10:32:34
 * @author yangdong
 * @todo   TODO
 */
public class OrderInfoService {
	//static使该service保证了单例,public可以使Controller方便调用该service
	public static OrderService service= new OrderService();//名字都叫service，统一命名
	
	/**
	 * 
	 * @time   2018年8月23日 上午10:31:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param c
	 * @param  @return
	 * @return_type   CreditOrderInfo
	 * 根据id获取订单
	 */
	
	public CreditOrderInfo getOrder(String id,BaseProjectController c) {
//		String authorSql=DataAuthorUtils.getAuthorByUser(c);//验证权限
		return CreditOrderInfo.dao.findById(id);
	}
	/**
	 * 
	 * @time   2018年8月23日 上午10:32:20
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param pageNumber
	 * @param  @param pageSize
	 * @param  @param customid
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<CreditOrderInfo>
	 * 根据客户id获取订单并分页
	 */
	public  Page<CreditOrderInfo> getOrders(int pageNumber,int pageSize,String customid,BaseProjectController c){
//		String authorSql=DataAuthorUtils.getAuthorByUser(c);//验证权限
		StringBuffer selectSql=new StringBuffer(" select * ");
		StringBuffer fromSql=new StringBuffer(" from credit_order_info where 1=1 ");
		//参数集合
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(customid)){
			fromSql.append(" and customid =? ");
			params.add(customid);
		}
		CreditOrderInfo.dao.paginate(new Paginator(pageNumber, pageSize),  selectSql.toString()
				,fromSql.toString(),params.toArray());
		return CreditOrderInfo.dao.paginate(new Paginator(pageNumber, pageSize),  selectSql.toString()
				,fromSql.toString(),params.toArray());
	}
	
	public void addOrder(CreditOrderInfo coi) {
		CreditOrderInfo.dao.save();
		
	}
	
}
