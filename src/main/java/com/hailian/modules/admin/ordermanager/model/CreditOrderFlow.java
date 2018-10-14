package com.hailian.modules.admin.ordermanager.model;

import java.io.Serializable;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
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
		sql.append(" where 1 = 1 and t.order_num=?"); 
		return dao.find(sql.toString(), num);
	}

}
