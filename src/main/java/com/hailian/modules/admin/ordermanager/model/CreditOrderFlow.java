package com.hailian.modules.admin.ordermanager.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.usercenter.controller.OrderProcessController;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
/**
 * 
 * @className CreditOrderFlow.java
 * @time   2018年10月12日 下午4:30:15
 * @author yangdong 订单记录表
 * @todo   TODO
 */
@ModelBind(table = "credit_order_flow")
public class CreditOrderFlow extends BaseProjectModel<CreditOrderFlow> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditOrderFlow dao = new CreditOrderFlow();//名字都叫dao，统一命名
	/**
	 * 
	 * @time   2018年10月12日 下午5:18:56
	 * @author yangdong
	 * @todo   TODO 获取订单记录
	 * @param  @param object
	 * @param  @return
	 * @return_type   List<CreditOrderFlow>
	 */
	public List<CreditOrderFlow> findByNum(String num) {
		StringBuffer sql=new StringBuffer();
		sql.append("select t.*,s.detail_name as statuName,s1.realname as name from credit_order_flow t ");
		sql.append(" left join sys_dict_detail s on s.detail_id=t.order_state ");
		sql.append(" left join sys_user s1 on s1.userid=t.create_oper ");
		sql.append(" where t.del_flag='0' and 1 = 1 and t.order_num=?"); 
		return dao.find(sql.toString(), num);
	}
	public void del(String num) {
		StringBuffer sql=new StringBuffer();
		sql.append("update credit_order_flow set del_flag=1 where order_num=? and order_state!=291 ");
		Db.update(sql.toString(),num);
	}
	
	public static void addOneEntry(BaseController c,CreditOrderInfo model) {
		//获取订单记录对象
		CreditOrderFlow cof = new CreditOrderFlow();
		//订单号
		model = model.findById(Arrays.asList(new String[] {model.get("id")+""}).toArray());
		cof.set("order_num", model.get("num"));
		//订单状态
		cof.set("order_state", model.get("status"));
		//操作人
		cof.set("create_oper", c.getSessionUser()==null?444:c.getSessionUser().getUserid());
		//操作时间
		cof.set("create_time",DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD));			
		//记录生成时间
		cof.set("create_date", c.getNow());
		cof.save();
	}
	
	public static void addOneEntry(BaseController c, CreditOrderInfo model,
			String errorMessage) {
		//获取订单记录对象
		CreditOrderFlow cof = new CreditOrderFlow();
		//订单号
		model = model.findById(Arrays.asList(new String[] {model.get("id")+""}).toArray());
		cof.set("order_num", model.get("num"));
		//订单状态
		cof.set("order_state", model.get("status"));
		//操作人
		cof.set("create_oper", c.getSessionUser()==null?444:c.getSessionUser().getUserid());
		//操作时间
		cof.set("create_time",DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD));			
		//记录生成时间
		cof.set("create_date", c.getNow());
		//错误原因
		cof.set("error_message", errorMessage);
		cof.save();
	}
}
