package com.hailian.modules.credit.usercenter.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.modules.credit.reportmanager.model.CreditReportDetailConf;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;

public class DetailJsonData {
	CreditReportDetailConf title = new CreditReportDetailConf();
	List<CreditReportDetailConf> contents = new ArrayList<CreditReportDetailConf>();
	String smallModileType = "";
	@Override
	public String toString() {
		return "ModuleJsonData [title=" + title + ", contents=" + contents + ", smallModileType=" + smallModileType
				+ "]";
	}
	
	public CreditReportDetailConf getTitle() {
		return title;
	}

	public void setTitle(CreditReportDetailConf title) {
		this.title = title;
	}
    
	
	public List<CreditReportDetailConf> getContents() {
		return contents;
	}

	public void setContents(List<CreditReportDetailConf> contents) {
		this.contents = contents;
	}

	public String getSmallModileType() {
		return smallModileType;
	}
	public void setSmallModileType(String smallModileType) {
		this.smallModileType = smallModileType;
	}
	public DetailJsonData(CreditReportDetailConf title, List<CreditReportDetailConf> contents, String smallModileType) {
		super();
		this.title = title;
		this.contents = contents;
		this.smallModileType = smallModileType;
	}
	
	
}
