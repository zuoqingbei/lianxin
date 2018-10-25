package com.hailian.modules.credit.reportmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.reportmanager.model.CreditReportTempConf;

public class TemplateReportTempService extends BaseService{
	public List<CreditReportTempConf> getTemp(String report) {
		List<CreditReportTempConf> listDetail = new ArrayList<CreditReportTempConf>();
		listDetail.add(getDefaultTempDetail());
		listDetail.addAll(CreditReportTempConf.dao.findByReport(report));
		return listDetail;
	}
	
	protected CreditReportTempConf getDefaultTempDetail() {
		CreditReportTempConf allDict=new CreditReportTempConf();
		allDict.set("id", "");
		allDict.set("temp_name","请选择模板");
		return allDict;
	}

}
