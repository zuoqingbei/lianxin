package com.hailian.modules.credit.orderallocation.service;

import java.util.Map;

import javax.management.ObjectName;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.orderallocation.model.OrderAllocationRuleModel;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
* @Title: OrderAlloctionRuleService  
* @Description:  订单分配规则
* @author lxy
* @date 2018年10月16日  下午1:56:08
 */
public class OrderAlloctionRuleService {
	public static final OrderAlloctionRuleService service=new OrderAlloctionRuleService();

	
	/**
	 * 
	 * @time   2018年9月12日 下午3:41:17
	 * @author dyc
	 * @todo   分页查询代理信息
	 * @return_type   Page<AgentModel>
	 */
	public Page<OrderAllocationRuleModel> getRule( String orderBy, String keyWord,
			BaseProjectController c) {
		return OrderAllocationRuleModel.dao.getRule( orderBy, keyWord,  c);
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:35:40
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   CompanyModel
	 */
	public OrderAllocationRuleModel getOne(int id, BaseProjectController c) {
		OrderAllocationRuleModel Model = OrderAllocationRuleModel.dao.getOne(id, c);
		return Model;

	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:42:14
	 * @author dyc
	 * @todo   根据id删除单条代理信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		return OrderAllocationRuleModel.dao.updateDelFlagById(id);
	}
	
	/**
	 * 
	* @Description: 修改订单类型
	* @date 2018年10月17日 上午10:30:22
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public boolean updateid(Map<String,Object> map) {
		return OrderAllocationRuleModel.dao.updateids(map);
	}
}
