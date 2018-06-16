package com.ulab.aop;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.ulab.core.BaseController;

/**
 * 
 * @time   2017年4月11日 下午3:12:32
 * @author zuoqb
 * @todo   单文件拦截器
 */
public class GlobalInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		HttpServletRequest request = controller.getRequest();
		StringBuffer url = controller.getRequest().getRequestURL();
		BaseController c = (BaseController) ai.getController();
		ai.invoke();
	}

}
