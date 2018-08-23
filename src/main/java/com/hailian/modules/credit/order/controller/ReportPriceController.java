package com.hailian.modules.credit.order.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.ReportPrice;
import com.hailian.modules.credit.order.service.ReportPriceService;

/**
* @author dyc:
* @version 2018年8月23日 下午5:29:22
* @todo
*/
@ControllerBind(controllerKey="/credit/price")
public class ReportPriceController extends BaseProjectController{
	
/**
 * 
 * @time   2018年8月23日 下午5:39:02
 * @author dyc
 * @todo   根据id查询报告价格信息
 * @return_type   void
 */
	public void getOne(){
	    String id=getPara("id");
		ReportPrice price=ReportPriceService.service.selectByid(id, this);
		setAttr("getOne", price);
	}
	
}
