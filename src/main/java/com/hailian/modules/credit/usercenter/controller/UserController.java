package com.hailian.modules.credit.usercenter.controller;


import java.util.HashMap;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.JFlyFoxUtils;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.log.SysLog;
import com.hailian.system.user.SysUser;
import com.hailian.util.encrypt.Md5Utils;
import com.jfinal.kit.PropKit;
/**
 * @className UserLoginController.java
 * @time   2018年9月10日 上午9:30:00
 * @author lzg
 * @todo   用户登录控制
 */
@Api( tag = "用户登录控制", description = "用户登录控制" )
@ControllerBind(controllerKey = "/credit/front/usercenter")
public class UserController  extends BaseProjectController{
		private static final String PATH = "/pages/credit/usercenter/";
		private static final String SALT = PropKit.use("config.properties").get("salt");
		//展示登录页
		public void showLogin(){
			render(PATH+"showLogin.html");
		}
		//登录
		public void login(){
			//Md5Utils md5 = new Md5Utils();
			String username = getRequest().getParameter("username");
			String password = getRequest().getParameter("password");
			if(username==null||password==null){
				renderJson(new ResultType(0, "账号或者密码错误!"));
				return;
			}
			String targetPwd = 	JFlyFoxUtils.passwordEncrypt(password);
			SysUser user = SysUser.dao.findByUserName(username);
			String realPwd = "-102156515141521232f297a57a5a743894a0e4a801fc3";
			if(user!=null){
				realPwd = user.get("password");
			}
			if(targetPwd.equals(realPwd)){
				//设置包含权限信息的session
				setSessionUser(user);
				//setSessionAttr(Attr.SESSION_NAME, user);
				//render(PATH+"menu.html");
				//Map<Integer, List<SysMenu>> map = new UserSvc().getQTMap(user);
				//setAttr("user",user);
				//setAttr("menu", map);
				renderJson(new ResultType(1, "登录成功!"));
				//redirect("/credit/front/home/menu");
			}else{
				renderJson(new ResultType(0, "账号或者密码错误!"));
			}
		}
		/**
		 * 登出2018-09-28
		 */
		public void logout() {
			SysUser user = (SysUser) getSessionUser();
			if (user != null) {
				removeSessionUser();
			}
			redirect("/credit/front/usercenter/showLogin");
		}
		
		
		
		
		
		
		
		
		
		
}
