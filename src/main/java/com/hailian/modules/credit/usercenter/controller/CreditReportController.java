package com.hailian.modules.credit.usercenter.controller;


import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.word.BaseInfoZh;
import com.hailian.util.word.BusinessZh;
import com.hailian.util.word.CreditZh;

/**
 * @className UserLoginController.java
 * @time   2018年9月10日 上午9:30:00
 * @author lzg
 * @todo   用户登录控制
 */
@Api( tag = "用户登录控制", description = "用户登录控制" )
@ControllerBind(controllerKey = "/report/test")
public class CreditReportController extends BaseProjectController{

		//登录
		public void report(){
            //BaseInfoZh test = new BaseInfoZh();
            //test.reportTable();
            //BusinessZh test = new BaseInfoZh();
            BusinessZh.reportTable();
            //CreditZh.reportTable();
		}


		
}
