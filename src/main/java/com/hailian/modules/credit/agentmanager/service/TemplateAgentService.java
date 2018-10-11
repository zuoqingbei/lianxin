package com.hailian.modules.credit.agentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.agentmanager.model.AgentModel;

public class TemplateAgentService extends BaseService{
	
	
	public List<AgentModel> getAgent() {
		List<AgentModel> listDetail = new ArrayList<AgentModel>();
		listDetail.add(getDefaultAgentDetail());
		listDetail.addAll(AgentModel.dao.findAll());
		return listDetail;
	}
	
	/**
	 * 
	 * @todo  报告类型
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public String getAgentString() {
		StringBuffer sb=new StringBuffer();
		List<AgentModel> listDetail =getAgent();
		for(AgentModel detail:listDetail){
				if("请选择代理".equals(detail.getStr("agent_name"))){
					sb.append("<option selected='selected'  value='"+detail.get("agent_id")+"'>"+detail.get("agent_name")+"</option>");
				}else{
					sb.append("<option  value='"+detail.get("agent_id")+"'>"+detail.get("agent_name")+"</option>");
				}			
		}
		return sb.toString();
	}
	/**
	 * 
	 * @todo   获取全部默认报告类型
	 * @time   2018年9月3日 下午7:12:53
	 * @author zuoqb
	 * @params
	 */
	protected AgentModel getDefaultAgentDetail() {
		AgentModel allDict=new AgentModel();
		allDict.set("agent_id", "");
		allDict.set("agent_name","请选择代理");
		return allDict;
	}
}
