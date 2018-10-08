package com.hailian.modules.admin.ordermanager.controller;

import java.util.List;

import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.system.user.SysUser;

@ControllerBind(controllerKey = "/admin/OrderFeiPei")
public class OrderFeiPei {
	public static void toOrderFenPei(){
		List<SysUser> reporterlist = SysUser.dao.getReporter();
		for(SysUser reporter:reporterlist){
			String reporterId=reporter.get("userid");
			System.out.println(reporterId);
		}
	}
	public static void main(String[] args) {
		toOrderFenPei();
	}
}
