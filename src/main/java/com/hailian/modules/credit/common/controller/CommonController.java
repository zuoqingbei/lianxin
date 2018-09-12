package com.hailian.modules.credit.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionContext;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.usercenter.service.HomeService;
import com.hailian.system.menu.SysMenu;
import com.hailian.system.user.SysUser;
import com.hailian.system.user.UserSvc;
/**
 * 
 * @className CommonController.java
 * @time   2018年9月5日 下午1:47:34
 * @author yangdong
 * @todo   TODO
 */
@Api( tag = "公用", description = "公用" )
@ControllerBind(controllerKey = "/credit/front/common")
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
		SysUser user=HomeService.service.getUser(this);
		Map<Integer, List<SysMenu>> map = new UserSvc().getAuthMap(user);
		System.out.println(map);
		setAttr("user",user);
		setAttr("menu", map);
		render(path+"menu.html");
	}


}
