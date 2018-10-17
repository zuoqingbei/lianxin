package com.hailian.modules.credit.orderflowconf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.orderflowconf.controller.OrderFlowConfController;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

@ModelBind(table = "credit_order_flow_conf")
public class CreditOrderFlowConf extends BaseProjectModel<CreditOrderFlowConf> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CreditOrderFlowConf dao = new CreditOrderFlowConf();//名字都叫dao，统一命名
	
	public List<CreditOrderFlowConf> findByType(String type){
		StringBuffer sql=new StringBuffer();
		sql.append("select t.* from credit_order_flow_conf t ");
		sql.append(" where 1 = 1 and t.del_flag=0 and t.type=?"); 
		return dao.find(sql.toString(), type);
		
	}

	public Page<CreditOrderFlowConf> getOrderFlowConfs(Paginator pageinator, CreditOrderFlowConf model, String orderby,
			OrderFlowConfController c) {
		
		StringBuffer sql = new StringBuffer();
		//国家类型
		String id = model.getStr("id");
		//订单状态代码
		String flow_state = model.getStr("flow_state");
		//订单状态类型
		String flow_name = model.getStr("flow_name");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_order_flow_conf t ");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_user s1 on s1.userid=t.update_by ");
		sql.append(" where 1 = 1 and t.del_flag='0' and t.flow_name is not null ");
		sql.append("and t.flow_state is not null and t.type is not null  ");
		
		if (!c.isAdmin(c.getSessionUser())) {
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}

		if (StringUtils.isNotBlank(id)) {
			sql.append(" and t.id=?");
			params.add(id);
		}
		if (StringUtils.isNotBlank(flow_state)) {
			sql.append(" and t.flow_state=?");
			params.add(flow_state);
		}
		if (StringUtils.isNotBlank(flow_name)) {
			sql.append(" and t.flow_name=?");
			params.add(flow_name);
		}
		if (StrUtils.isEmpty(orderby)) {
			sql.append(" order by t.id desc");
		} else {
			sql.append(" order by ").append(orderby);
		}
		Page<CreditOrderFlowConf> page =dao
				.paginate(
						pageinator,
						"select t.*,s.realname as createName,s1.realname as updateName",
						sql.toString(), params.toArray());

		return page;
	}

	public CreditOrderFlowConf findOrderFlowById(int id) {
		StringBuffer sql=new StringBuffer();
		sql.append("select t.*,s.realname as createName,s1.realname as updateName from credit_order_flow_conf t ");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_user s1 on s1.userid=t.update_by ");
		sql.append(" where 1 = 1 and t.type=?"); 
		return dao.findFirst(sql.toString(), id);
	}
	
}


