package com.hailian.modules.credit.usercenter.controller;

public interface ReportInfoGetDataInterface {
	/**
	 * 获取bootStrapTable式的数据
	 */
	void getBootStrapTable();
	/**
	 * 获取form式的数据
	 */
	void getForm();
	/**
	 * 从字典表里获取下拉选
	 * @param type
	 * @param selectedId
	 * @param disPalyCol
	 */
	void getSelete();
}
