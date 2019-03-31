package com.hailian.modules.credit.usercenter.controller;

import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.jfinal.plugin.activerecord.Record;

/**
 * 2019/03/29
 * lzg
 * @author lzg
 *
 */
@ControllerBind(controllerKey = "/credit/front/getKpi")
public class KpiController extends BaseController {
	


	public void run(){
        Integer userId =  getSessionUser()==null?555:getSessionUser().getUserid();
        String orderNum = getPara("orderNum");
        CreditOrderInfo model = CreditOrderInfo.dao.findFirst("select * from credit_order_info where num = ?",orderNum);
        new OrderProcessController().getKpi(model,userId);
        renderJson( new Record().set("kpi","计算完成"));
    }

	 
}
