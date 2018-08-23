package com.hailian.modules.credit.order.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.order.model.ReportPrice;

/**
* @author dyc:
* @version 2018年8月23日 下午1:48:22
* @todo  报告价格业务处理
*/
public class ReportPriceService {
	public static  ReportPriceService service=new ReportPriceService();
    
	/**
	 * 
	 * @time   2018年8月23日 下午3:10:06
	 * @author dyc
	 * @todo   根据价格id查询信息
	 * @return_type   ReportPrice
	 */
	public ReportPrice selectOne(String id,BaseProjectController c){
		
		try {
			String sql="select * from credit_report_price where id=?";
			 return ReportPrice.dao.findById(id,sql);
		} catch (Exception e) {
			return null; 
		}
	}
}
