package com.ulab.controller;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.Random;







import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.core.Constants;
import com.ulab.model.DicModel;
import com.ulab.model.LabMapModel;
//import com.ulab.model.LabModel;
import com.ulab.model.ProviderDicModel;
import com.ulab.model.UserModel;
import com.ulab.util.MD5Util;
//import com.ulab.util.JsonUtils;
import com.ulab.util.SqlUtil;
import com.ulab.util.UUIDTool;

/**
 * 
 * @time   2018年1月30日 下午12:55:15
 * @author zuoqb
 * @todo   用户登陆相关
 */
@ControllerBind(controllerKey = "/user", viewPath = "/user")
@Before({ GlobalInterceptor.class })
public class UserController extends BaseController {
	public void login(){
		System.out.println("登陆");
		renderJson("登陆");
	}
	public void loginForm(){
		String loginName=getPara("login_name");
		String pwd=getPara("pwd");
		Map<String,Object> map=UserModel.dao.login(loginName, pwd);
		if(Boolean.parseBoolean(map.get("success").toString())){
			//登陆成功
			setSessionAttr("user", map.get("user"));
			redirect("/lab/index");
		}else{
			renderJson(map);
		}
	}
	public void userList() {
		int pageNumber=getParaToInt("pageNumber",1);//当前页码
		int pageSize=getParaToInt("pageSize",10);//每页条数
		Page<UserModel> pager=UserModel.dao.paginates(pageSize, pageNumber);
		renderJson(pager);
	}
	/**
	 * 
	 * @time   2018年1月30日 下午1:06:57
	 * @author zuoqb
	 * @todo   更新或者插入用户
	 * @param  
	 * @return_type   void
	 */
	public void updateUser() {
		UserModel user=new UserModel();
		String uid=getPara("uid");
		String name=getPara("name");
		String login_name=getPara("login_name");
		String pwd=getPara("pwd");
		String forbid=getPara("forbid","0");
		String del_flag=getPara("del_flag","0");
		if(StringUtils.isNotBlank(uid)){
			user=UserModel.dao.findById(uid);
		}else{
			user.set("id", UUIDTool.getUUID());
		}
		user.set("name", name);
		user.set("login_name", login_name);
		user.set("pwd", MD5Util.getStringMD5(pwd));
		user.set("forbid", forbid);
		user.set("del_flag", del_flag);
		if(StringUtils.isNotBlank(uid)){
			user.update();
		}else{
			user.save();
		}
		renderJson(user);
	}

}
