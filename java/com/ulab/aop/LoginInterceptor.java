package com.ulab.aop;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.ulab.core.BaseController;
import com.ulab.model.UserModel;

/**
 * 
 * @time   2018年1月30日13:46:53
 * @author zuoqb
 * @todo   用户登陆校验拦截器
 */
public class LoginInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		BaseController c = (BaseController) ai.getController();
		UserModel user=c.getSessionAttr("user");
		if(user==null){
			//未登陆
			c.redirect("/user/login");
		}else{
			ai.invoke();
			
		}
	}

}
