package com.hailian.modules.credit.usercenter.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.usercenter.model.UserModel;
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
			render(PATH+"login.html");
		}
		//登录
		public void login(){
			Md5Utils md5 = new Md5Utils();
			String username = getPara("username");
			String targetPwd = md5.getMD5(getPara("password")+SALT);
			
			List<UserModel> list = UserModel.dao.findByUserName(username);
			String realPwd = SALT;
			if(list.size()!=0){
				realPwd = list.get(0).get("password");
			}
			if(targetPwd.equals(realPwd)){
				System.out.println("登录成功!");
			}else{
				System.out.println("登录失败!");
			}
		}
		
		
		
		
		
		
		
		
		
		
		
}
