package com.hailian.modules.credit.usercenter.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;

public class ModuleJsonData {
	CreditReportModuleConf title = new CreditReportModuleConf();
	List<CreditReportModuleConf> contents = new ArrayList<CreditReportModuleConf>();
	String smallModileType = "";
	@Override
	public String toString() {
		return "ModuleJsonData [title=" + title + ", contents=" + contents + ", smallModileType=" + smallModileType
				+ "]";
	}
	public CreditReportModuleConf getTitle() {
		return title;
	}
	public void setTitle(CreditReportModuleConf title) {
		this.title = title;
	}
	public List<CreditReportModuleConf> getContents() {
		return contents;
	}
	public void setContents(List<CreditReportModuleConf> contents) {
		this.contents = contents;
	}
	public String getSmallModileType() {
		return smallModileType;
	}
	public void setSmallModileType(String smallModileType) {
		this.smallModileType = smallModileType;
	}
	public ModuleJsonData(CreditReportModuleConf title, List<CreditReportModuleConf> contents, String smallModileType) {
		super();
		this.title = title;
		this.contents = contents;
		this.smallModileType = smallModileType;
	}
	
	
}
