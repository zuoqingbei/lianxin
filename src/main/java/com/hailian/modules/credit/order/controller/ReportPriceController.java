package com.hailian.modules.credit.order.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.ReportPrice;
import com.hailian.modules.credit.order.service.ReportPriceService;

/**
* @author dyc:
* @version 2018年8月23日 下午3:17:51
* @todo  报告价格
*/
@ControllerBind(controllerKey="/credit/price")
public class ReportPriceController extends BaseProjectController{
	/**
	 * 
	 * @time   2018年8月23日 下午3:32:00
	 * @author dyc
	 * @todo   TODO
	 * @return_type   void
	 */
	
	public  void  getOne(){
		String id=getPara("id");
		ReportPrice price=ReportPriceService.service.selectOne(id, this);
		setAttr("priceone", price);
	}
	
	
  
}
