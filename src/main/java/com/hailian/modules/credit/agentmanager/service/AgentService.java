package com.hailian.modules.credit.agentmanager.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月12日 上午11:46:01
* @todo  代理信息处理业务
*/
public class AgentService {
	public static AgentService service = new AgentService();

	/**
	 * 
	 * @time   2018年9月12日 下午3:41:17
	 * @author dyc
	 * @todo   分页查询代理信息
	 * @return_type   Page<AgentModel>
	 */
	public Page<AgentModel> getAgent(int pageNumber, int pageSize, String orderBy, String keyWord, String searchType,
			BaseProjectController c) {
		return AgentModel.dao.getAgent(null, pageNumber, pageSize, orderBy, keyWord, searchType, c);
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:35:40
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   CompanyModel
	 */
	public AgentModel getOne(int id, BaseProjectController c) {
		AgentModel agentModel = AgentModel.dao.getOne(id, c);
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
		return AgentModel.dao.updateDelFlagById(id);
	}
	}