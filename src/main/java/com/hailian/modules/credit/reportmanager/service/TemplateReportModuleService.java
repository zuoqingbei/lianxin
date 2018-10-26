package com.hailian.modules.credit.reportmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;

public class TemplateReportModuleService extends BaseService{
	
	public List<CreditReportModuleConf> getReportModule(String id) {
		List<CreditReportModuleConf> listDetail = new ArrayList<CreditReportModuleConf>();
		//listDetail.add(getDefaultAgentDetail());
		listDetail.addAll(CreditReportModuleConf.dao.findByReport(id));
		return listDetail;
	}

}
