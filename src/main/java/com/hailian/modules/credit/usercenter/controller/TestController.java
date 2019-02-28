package com.hailian.modules.credit.usercenter.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.word.BaseBusiCrdt;
@Api(tag = "测试", description = "测试")
@ControllerBind(controllerKey = "/credit/test")
public class TestController extends BaseProjectController{
	
	public void test1() {
		
		List<Integer> list = BaseBusiCrdt.getFinancialType(getRequest().getParameter("companyId"));
		for (Integer integer : list) {
			System.out.println(integer);
		}
	}
	
}
