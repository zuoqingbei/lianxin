package com.hailian.modules.credit.usercenter.model;

import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

public class ResultType {
	private Integer total;
	List<CreditOrderInfo> rows;
	public ResultType(int total, List<CreditOrderInfo> rows) {
		this.total=total;
		this.rows=rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<CreditOrderInfo> getRows() {
		return rows;
	}
	public void setRows(List<CreditOrderInfo> rows) {
		this.rows = rows;
	}
	
}
