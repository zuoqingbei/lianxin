package com.hailian.modules.credit.orderallocation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
* @Title: OrderAllocationRuleModel 
* @Description: 订单分配规则
* @author lxy
* @date 2018年10月16日  下午1:35:19
 */
@ModelBind(table = "credit_order_allocation_conf", key = "order_allocation_id")
public class OrderAllocationRuleModel extends BaseProjectModel<OrderAllocationRuleModel>{
	private static final long serialVersionUID = 1L;
	public static final OrderAllocationRuleModel dao=new OrderAllocationRuleModel();
     /**
      * 
     * @Description: 订单分配规则 分页查询
     * @date 2018年10月16日 下午2:20:22
     * @author: lxy
     * @version V1.0
     * @return
      */
	public Page<OrderAllocationRuleModel> getRule( String orderBy, String keyWord,
			 BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"select a.*,s.detail_name ");
		StringBuffer fromsql = new StringBuffer(" from credit_order_allocation_conf a ");
				fromsql.append(" left join sys_dict_detail s on s.detail_id=a.order_allocation_id"); 
				fromsql.append(" where a.del_flag=0 ");
	
		if (StringUtil.isNotEmpty(keyWord)) {
			fromsql.append(" and s.detail_name like '%"+keyWord+"%'");
		}
		
		if (StrUtils.isEmpty(orderBy)) {
			fromsql.append(" order by create_date desc");
		} else {
			fromsql.append(" order by ").append(orderBy);
		}
		Page<OrderAllocationRuleModel> orderAllocation = OrderAllocationRuleModel.dao.paginate(new Paginator(), selectsql.toString(), fromsql.toString());
		return orderAllocation;
	}
	public List<OrderAllocationRuleModel> getRuleList() {
		StringBuffer selectsql = new StringBuffer(
				"select a.*,s.detail_name ");
		selectsql.append(" from credit_order_allocation_conf a ");
		selectsql.append(" left join sys_dict_detail s on s.detail_id=a.order_allocation_id"); 
		selectsql.append(" where a.del_flag=0 ");
	
		
		List<OrderAllocationRuleModel> orderAllocation = OrderAllocationRuleModel.dao.find(selectsql.toString());
		return orderAllocation;
	}
    /**
     * 
    * @Description: 根据id查询
    * @date 2018年10月16日 下午2:25:36
    * @author: lxy
    * @version V1.0
    * @return
     */
	public OrderAllocationRuleModel getOne(int id, BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"select a.order_allocation_id,a.allocation_value,s.detail_name");
		StringBuffer fromsql = new StringBuffer(" from credit_order_allocation_conf a ");
		fromsql.append(" left join sys_dict_detail s on s.detail_id=a.order_allocation_id");
         fromsql.append(" where a.del_flag=0 and a.order_allocation_id='"+id+"'");
		return OrderAllocationRuleModel.dao.findFirst(selectsql.toString() + fromsql.toString());
	}
   /**
    * 
   * @Description: 删除
   * @date 2018年10月16日 下午2:42:51
   * @author: lxy
   * @version V1.0
   * @return
    */
	public boolean updateDelFlagById(Integer id) {
		OrderAllocationRuleModel ruleModel = OrderAllocationRuleModel.dao.findById(id);
		if (ruleModel != null) {
			return ruleModel.set("del_flag", 1).update();
		}
		return false;
	}
	/**
	 * 
	* @Description: 修改规则类型
	* @date 2018年10月17日 上午10:32:52
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public boolean updateids(Map<String, Object> map) {
		StringBuffer selectsql = new StringBuffer("update credit_order_allocation_conf set ");
			if (StringUtils.isNotBlank(map.get("newid").toString())) {
			selectsql.append(" order_allocation_id="+map.get("newid"));	
			}
			if (StringUtils.isNotBlank(map.get("allocation_value").toString())) {
				selectsql.append(", allocation_value='"+map.get("allocation_value").toString()+"'");	
			}
			if (StringUtils.isNotBlank(map.get("update_by").toString())) {
				selectsql.append(", update_by='"+map.get("update_by").toString()+"'");			
			}
			if (StringUtils.isNotBlank(map.get("update_date").toString())) {
				selectsql.append(", update_date='"+map.get("update_date").toString()+"'");
			}
			if (StringUtils.isNotBlank(map.get("order_allocation_id").toString())) {
				selectsql.append(" where order_allocation_id="+map.get("order_allocation_id").toString());
			}	
        Db.update(selectsql.toString());
		return false;
		
	}

}
