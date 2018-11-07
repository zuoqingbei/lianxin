package com.hailian.modules.credit.agentmanager.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.jfinal.plugin.activerecord.Page;

/**
 * 代理价格
* @author doushuihai  
* @date 2018年11月5日下午2:06:21  
* @TODO
 */
public class AgentPriceService {
	public static AgentPriceService service = new AgentPriceService();

	/**
	 * 
	 * @time   2018年9月12日 下午3:41:17
	 * @author dyc
	 * @todo   分页查询代理信息
	 * @return_type   Page<AgentModel>
	 */
	public Page<AgentPriceModel> getAgent(Paginator paginator,String orderBy,BaseProjectController c,String id) {
		return AgentPriceModel.dao.getAgent(paginator,orderBy, c,id);
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:35:40
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   CompanyModel
	 */
	public AgentPriceModel getOne(int id, BaseProjectController c) {
		AgentPriceModel agentModel = AgentPriceModel.dao.getOne(id, c);
		return agentModel;

	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:42:14
	 * @author dyc
	 * @todo   根据id删除单条代理信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		return AgentPriceModel.dao.updateDelFlagById(id);
	}
	/**
	 * 根据代理id，省，市，代理类别获取代理价格
	* @author doushuihai  
	* @date 2018年11月7日下午1:57:44  
	* @TODO
	 */
	public AgentPriceModel getAgentPrice(int pid,int cid,String agent_id,String agent_category) {
		AgentPriceModel agentpricemodel = AgentPriceModel.dao.getAgentPrice(pid, cid,agent_id,agent_category,true);
		if(agentpricemodel== null){
			agentpricemodel = AgentPriceModel.dao.getAgentPrice(pid, cid,agent_id,agent_category,false);
		}
		return agentpricemodel;

	}
}