package com.hailian.modules.admin.ordermanager.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Reporter implements Comparable<Reporter>{
	String reportId;
	double finalScore;//报告员评分
	long inDoingOrderNum;
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}

	public long getInDoingOrderNum() {
		return inDoingOrderNum;
	}

	public void setInDoingOrderNum(long inDoingOrderNum) {
		this.inDoingOrderNum = inDoingOrderNum;
	}
	
	
	public Reporter(String reportId, double finalScore, long inDoingOrderNum) {
		super();
		this.reportId = reportId;
		this.finalScore = finalScore;
		this.inDoingOrderNum = inDoingOrderNum;
	}

	@Override  
    public int compareTo(Reporter o) {
		long i = this.getInDoingOrderNum() - o.getInDoingOrderNum();    
		if(i>0){
			return 1;
		}else if(i<0){
			return -1;
		}
		if(i == 0){ 
			 double i2=this.finalScore - o.getFinalScore();
			 if(i2>0){
				 return -1;
			 }else if(i2<0){
				 return 1;
			 }else{
				 return 0;
			 }
		}
		return 0;          
		}
	public static void main(String[] args) {          
		List<Reporter> users = new ArrayList<Reporter>(); 
		users.add(new Reporter("1",88.00, 26));
		users.add(new Reporter("2",90.00, 23));          
		users.add(new Reporter("4",92.00,56));          
		users.add(new Reporter("5",94.00, 23));          
		Collections.sort(users);          
		for(Reporter user : users){              
			System.out.println(user.getInDoingOrderNum() + "," + user.getFinalScore());
			}  
	}
	public String getreporterToOrder(List<Reporter> reporterList){
		Collections.sort(reporterList);
		return reporterList.get(0).getReportId();
	}

	@Override
	public String toString() {
		return "Reporter [reportId=" + reportId + ", finalScore=" + finalScore + ", inDoingOrderNum=" + inDoingOrderNum
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(finalScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (inDoingOrderNum ^ (inDoingOrderNum >>> 32));
		result = prime * result + ((reportId == null) ? 0 : reportId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reporter other = (Reporter) obj;
		if (Double.doubleToLongBits(finalScore) != Double.doubleToLongBits(other.finalScore))
			return false;
		if (inDoingOrderNum != other.inDoingOrderNum)
			return false;
		if (reportId == null) {
			if (other.reportId != null)
				return false;
		} else if (!reportId.equals(other.reportId))
			return false;
		return true;
	}

}
