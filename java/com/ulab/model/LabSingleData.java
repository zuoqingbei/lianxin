package com.ulab.model;



/**
 * @ClassName: LabSingleData
 * @Description: 单个实验室数据
 * @Author     Jilei
 * @Date  2017-5-27 下午3:54:59
 */
public class LabSingleData {
	
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
	 * @Fields labStatus : 实验室状态
	 */
	String labStatus;
	/**
	 * @Fields testUnitStatus : 台位负荷
	 */
	String testUnitStatus;
	/**
	 * @Fields monthRate : 月负荷率
	 */
	String monthRate;
	/**
	 * @Fields equipmentCount : 设备数
	 */
	int equipmentCount;
	/**
	 * @Fields finishOrderCount : 已测订单
	 */
	int finishOrderCount;
	/**
	 * @Fields testingOrderCount : 在测订单
	 */
	int testingOrderCount;
	
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
	public String getLabStatus() {
		return labStatus;
	}
	public void setLabStatus(String labStatus) {
		this.labStatus = labStatus;
	}
	public String getTestUnitStatus() {
		return testUnitStatus;
	}
	public void setTestUnitStatus(String testUnitStatus) {
		this.testUnitStatus = testUnitStatus;
	}
	public String getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(String monthRate) {
		this.monthRate = monthRate;
	}
	public int getEquipmentCount() {
		return equipmentCount;
	}
	public void setEquipmentCount(int equipmentCount) {
		this.equipmentCount = equipmentCount;
	}
	public int getFinishOrderCount() {
		return finishOrderCount;
	}
	public void setFinishOrderCount(int finishOrderCount) {
		this.finishOrderCount = finishOrderCount;
	}
	public int getTestingOrderCount() {
		return testingOrderCount;
	}
	public void setTestingOrderCount(int testingOrderCount) {
		this.testingOrderCount = testingOrderCount;
	}
}
