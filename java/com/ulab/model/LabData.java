package com.ulab.model;

import java.util.List;

/**
 * @ClassName: LabData
 * @Description: 实验室信息
 * @Author     Jilei
 * @Date  2017-5-30 下午1:51:29
 */
public class LabData {
	
	/**
	 * @Fields labCode : 实验室编码
	 */
	String labCode;
	/**
	 * @Fields labName : 实验室名称
	 */
	String labName;
	/**
	 * @Fields url : 实验室URL
	 */
	String url;	
	/**
	 * @Fields testUnitList : 实验室台位明细
	 */
	List<LabTestUnit> testUnitList;
	
	
	public String getLabCode() {
		return labCode;
	}
	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<LabTestUnit> getTestUnitList() {
		return testUnitList;
	}
	public void setTestUnitList(List<LabTestUnit> testUnitList) {
		this.testUnitList = testUnitList;
	}	

}
