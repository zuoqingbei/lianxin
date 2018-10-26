package com.hailian.modules.credit.reportmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.agentmanager.model.AgentModel;

public class TemplateReportModuleService extends BaseService{
	
	public List<AgentModel> getAgent() {
		List<AgentModel> listDetail = new ArrayList<AgentModel>();
		//listDetail.add(getDefaultAgentDetail());
		listDetail.addAll(AgentModel.dao.findAll());
		return listDetail;
	}

}
