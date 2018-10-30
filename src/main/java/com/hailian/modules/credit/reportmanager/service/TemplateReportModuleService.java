package com.hailian.modules.credit.reportmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;

public class TemplateReportModuleService extends BaseService{
	
	public List<CreditReportModuleConf> getReportModule() {
		List<CreditReportModuleConf> listDetail = new ArrayList<CreditReportModuleConf>();
		//listDetail.add(getDefaultAgentDetail());
		listDetail.addAll(CreditReportModuleConf.dao.findReportType());
		return listDetail;
	}
	
	public String getReportModuleString(String report) {
		StringBuffer sb=new StringBuffer();
		List<CreditReportModuleConf> listDetail =getReportModule();
		for(CreditReportModuleConf detail:listDetail){
				if(report.equals(detail.getStr("report"))){
					sb.append("<option selected='selected' m-id='"+detail.get("id")+"'  value='"+detail.get("report")+"'>"+detail.get("temp_name")+"</option>");
				}else{
					sb.append("<option m-id='"+detail.get("id")+"'  value='"+detail.get("report")+"'>"+detail.get("temp_name")+"</option>");
				}			
		}
		return sb.toString();
	}

}
