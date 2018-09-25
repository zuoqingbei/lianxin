package com.hailian.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hailian.modules.admin.ordermanager.service.OrderManagerService;

public class getOrderNum {
	private String num=String.valueOf(OrderManagerService.service.getMaxId().getInt("id")+1);

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public  String getNumber() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String date=sdf.format(new Date());
		if(num.length()==1) {
			num="000"+num;
		}if(num.length()==2) {
			num="00"+num;
		}if(num.length()==3) {
			num="0"+num;
		}
		return "00"+date+num;
	}
}
