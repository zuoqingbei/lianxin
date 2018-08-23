package com.hailian.modules.credit.order.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.order.model.ReportPrice;

/**
* @author dyc:
* @version 2018年8月23日 下午5:20:42
* @todo  报告价格处理业务
*/
public class ReportPriceService {
	public static ReportPriceService service=new ReportPriceService();
	
	/**
	 * 
	 * @time   2018年8月23日 下午5:27:06
	 * @author dyc
	 * @todo   根据id查询报告价格信息
	 * @return_type   ReportPrice
	 */
	
	public ReportPrice selectByid(String id,BaseProjectController c){
		try {
			String sql="select *  from credit_report_price where id=?";
			return ReportPrice.dao.findById(sql,c);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
