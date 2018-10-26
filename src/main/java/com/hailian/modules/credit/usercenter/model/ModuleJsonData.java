package com.hailian.modules.credit.usercenter.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;

public class ModuleJsonData {
	String title="";
	List<CreditReportModuleConf> contents=new ArrayList<CreditReportModuleConf>();
	public ModuleJsonData(String title, List<CreditReportModuleConf> contents) {
		this.title=title;
		this.contents=contents;
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<CreditReportModuleConf> getContents() {
		return contents;
	}
	public void setContents(List<CreditReportModuleConf> contents) {
		this.contents = contents;
	}
	
}
