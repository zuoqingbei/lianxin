package com.hailian.modules.credit.common.controller;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
/**
 * 
 * @className CommonController.java
 * @time   2018年9月5日 下午1:47:34
 * @author yangdong
 * @todo   TODO
 */
@Api( tag = "公用", description = "公用" )
@ControllerBind(controllerKey = "/credit/common")
public class CommonController extends BaseProjectController {
private static final String path = "/pages/credit/common/";
	/**
	 * @time   2018年9月5日 下午1:49:46
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	public void menu() {
		render(path+"menu.html");
	}


}
