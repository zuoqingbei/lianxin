package com.hailian.modules.credit.agentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.agentmanager.model.AgentCategoryModel;
import com.hailian.modules.credit.agentmanager.model.AgentModel;

public class TemplateAgentService extends BaseService{
	public static TemplateAgentService templateagentservice=new TemplateAgentService();
	
	
	public List<AgentModel> getAgent() {
		List<AgentModel> listDetail = new ArrayList<AgentModel>();
		listDetail.add(getDefaultAgentDetail());
		listDetail.addAll(AgentModel.dao.findAll());
		return listDetail;
	}
	
	/**
	 * 
	 * @time   2018年10月12日 上午10:52:15
	 * @author yangdong
	 * @todo   TODO 选择代理名称,默认值是请选择代理
	 * @param  @return
	 * @return_type   String
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
	 * @time   2018年10月12日 上午10:52:39
	 * @author yangdong
	 * @todo   TODO 选择代理id,默认值是请选择代理
	 * @param  @return
	 * @return_type   String
	 */
	public String getAgentIdString() {
		StringBuffer sb=new StringBuffer();
		List<AgentModel> listDetail =getAgent();
		for(AgentModel detail:listDetail){
				if("请选择代理".equals(detail.getStr("agent_name"))){
					sb.append("<option selected='selected'  value='"+detail.get("agent_id")+"'>"+detail.get("agent_name")+"</option>");
				}else{
					sb.append("<option  value='"+detail.get("agent_id")+"'>"+detail.get("agent_id")+"</option>");
				}			
		}
		return sb.toString();
	}
	/**
	 * 代理类别
	* @author doushuihai  
	* @date 2018年10月13日下午10:56:12  
	* @TODO
	 */
	public String getAgentCategoryString(String agentid) {
		StringBuffer sb=new StringBuffer();
		List<AgentCategoryModel> findAll = AgentCategoryModel.dao.findAll(agentid);
		for(AgentCategoryModel detail:findAll){
				if("请选择代理".equals(detail.getStr("categoryName"))){
					sb.append("<option selected='selected'  value='"+detail.get("agent_category")+"'>"+detail.get("categoryName")+"</option>");
				}else{
					sb.append("<option  value='"+detail.get("agent_category")+"'>"+detail.get("categoryName")+"</option>");
				}			
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 * 
	 * @time   2018年10月12日 上午10:53:31
	 * @author yangdong
	 * @todo   TODO 添加代理默认项
	 * @param  @return
	 * @return_type   AgentModel
	 */
	protected AgentModel getDefaultAgentDetail() {
		AgentModel allDict=new AgentModel();
		allDict.set("agent_id", "");
		allDict.set("agent_name","请选择代理");
		return allDict;
	}
}
