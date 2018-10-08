package com.hailian.modules.credit.interceptor;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.util.Attr;
import com.hailian.system.user.SysUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class CreditFrontUserInterceptor implements Interceptor {
	public static List<String> PASS = Arrays
			.asList(new String[] { "/credit/front/usercenter/showLogin", "/credit/front/usercenter/login" });

	@Override
	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		// 请求路径
		String url = ai.getActionKey();
		SysUser user = controller.getSessionAttr(Attr.SESSION_NAME);
		if (url.startsWith("/credit/front")) {
			if (isPass(url) || (user != null && user.getUserid() != null)) {
				ai.invoke();
			} else {
				String fromCommonJs=controller.getPara("from");
				if("commonjs".equals(fromCommonJs)){
					controller.renderText("nologin");
				}else{
					controller.redirect("/credit/front/usercenter/showLogin");
				}
				return;
			}
		} else {
			ai.invoke();
		}
	}

	public boolean isPass(String url) {
		boolean isPass = false;
		for (String pass : PASS) {
			if (pass.equals(url)) {
				isPass = true;
				break;
			}
		}
		return isPass;
	}

}
