package com.ulab.model;

import java.util.List;

/**
 * @ClassName: LabAllData
 * @Description: 实验室数据汇总
 * @Author     Jilei
 * @Date  2017-5-27 下午3:56:38
 */
public class LabAllData {
	
	/**
	 * @Fields labCount : 实验室总数
	 */
	int labCount;
	/**
	 * @Fields lowMonthRate : 最低负荷率
	 */
	String lowMonthRate;
	/**
	 * @Fields highMonthRate : 最高负荷率
	 */
	String highMonthRate;
	/**
	 * @Fields aveMonthRate : 平均负荷率
	 */
	String aveMonthRate;
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
	/**
	 * @Fields labSingleDataList : 实验室数据明细
	 */
	List<LabSingleData> labSingleDataList;
	
	public int getLabCount() {
		return labCount;
	}
	public void setLabCount(int labCount) {
		this.labCount = labCount;
	}
	public String getLowMonthRate() {
		return lowMonthRate;
	}
	public void setLowMonthRate(String lowMonthRate) {
		this.lowMonthRate = lowMonthRate;
	}
	public String getHighMonthRate() {
		return highMonthRate;
	}
	public void setHighMonthRate(String highMonthRate) {
		this.highMonthRate = highMonthRate;
	}
	public String getAveMonthRate() {
		return aveMonthRate;
	}
	public void setAveMonthRate(String aveMonthRate) {
		this.aveMonthRate = aveMonthRate;
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
	public List<LabSingleData> getLabSingleDataList() {
		return labSingleDataList;
	}
	public void setLabSingleDataList(List<LabSingleData> labSingleDataList) {
		this.labSingleDataList = labSingleDataList;
	}

}
