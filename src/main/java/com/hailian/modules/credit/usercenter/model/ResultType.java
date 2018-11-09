package com.hailian.modules.credit.usercenter.model;

import java.util.List;

import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
/**
 * 0代表失败
 * 1代表成功
 */
public class ResultType {
	/**
	 * 0代表失败
	 * 1代表成功
	 */
	private Integer statusCode;
	private String message;
	private Integer total;
	List rows;
	private Integer pageSize;
	private Integer pageNumber;
	private List files;
	
	public ResultType(Integer statusCode, String message, Integer total, List rows, Integer pageSize,
			Integer pageNumber, List files) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.total = total;
		this.rows = rows;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.files = files;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List getFiles() {
		return files;
	}

	public void setFiles(List files) {
		this.files = files;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNumber;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNumber = pageNum;
	}

	public ResultType(int total, List rows) {
		this.total=total;
		this.rows=rows;
	}
	public ResultType() {
		this.statusCode = 1;
		this.message="操作成功!";
	}
	public ResultType(Integer statusCode, String message, Integer total, List rows) {
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
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
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
