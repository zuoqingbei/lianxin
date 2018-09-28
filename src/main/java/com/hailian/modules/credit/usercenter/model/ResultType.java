package com.hailian.modules.credit.usercenter.model;

import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

public class ResultType {
	/**
	 * 0代表失败
	 * 1代表成功
	 */
	private Integer statusCode;
	private String message;
	private Integer total;
	List<CreditOrderInfo> rows;
	public ResultType(int total, List<CreditOrderInfo> rows) {
		this.total=total;
		this.rows=rows;
	}
	public ResultType() {
		this.statusCode = 1;
		this.message="操作成功!";
	}
	public ResultType(Integer statusCode, String message, Integer total, List<CreditOrderInfo> rows) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.total = total;
		this.rows = rows;
	}
	/**
	 * 
	 * @param statusCode 0代表失败 1代表成功
	 * @param message
	 */
	public ResultType(Integer statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
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
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResultType [statusCode=" + statusCode + ", message=" + message + ", total=" + total + ", rows=" + rows
				+ "]";
	}
	
}
