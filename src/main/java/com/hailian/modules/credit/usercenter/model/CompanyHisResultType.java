package com.hailian.modules.credit.usercenter.model;

import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;

public class CompanyHisResultType{
	Integer total;
	List<CreditCompanyHis> rows;
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<CreditCompanyHis> getRows() {
		return rows;
	}

	public void setRows(List<CreditCompanyHis> rows) {
		this.rows = rows;
	}

	public CompanyHisResultType(Integer total,List<CreditCompanyHis> rows){
		this.total = total;
		this.rows = rows;
	}
}