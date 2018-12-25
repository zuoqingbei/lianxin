package com.hailian.modules.admin.ordermanager.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.usercenter.controller.OrderProcessController;
import com.hailian.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
/**
 * @Description: 用户操作按钮记录表
* @author: dsh 
* @date:  2018年11月22日
 */
@ModelBind(table = "credit_operation_log")
public class CreditOperationLog extends BaseProjectModel<CreditOperationLog> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditOperationLog dao = new CreditOperationLog();//名字都叫dao，统一命名

	
	
	public static void addOneEntry(Integer userid,CreditOrderInfo model,String button,String url) {
		//获取操作记录对象
		CreditOperationLog cof = new CreditOperationLog();
		if(model != null){
			//订单号
			model = model.findById(Arrays.asList(new String[] {model.get("id")+""}).toArray());
			cof.set("order_num", model.get("num"));
			//订单状态
			cof.set("order_state", model.get("status"));
		}
		
		//操作人
		cof.set("create_oper", userid);
		cof.set("button", button);
		cof.set("url", url);
		//操作时间
		cof.set("create_time",DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD));			
		//记录生成时间
		cof.set("create_date", new Date());
		cof.save();
	}
}
