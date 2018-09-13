package com.hailian.modules.credit.interceptor;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hailian.jfinal.component.util.Attr;
import com.hailian.system.user.SysUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.render.RenderManager;
import com.mysql.fabric.Response;
/**
 * @time 2018/09/12
 * @author lzg
 *主拦截器
 */
public class CreditLoginInterceptor implements Interceptor {
	//private static final RenderManager renderManager = RenderManager.me();
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		HttpServletRequest request = controller.getRequest();
		String urI = request.getRequestURI();
		StringBuffer url = request.getRequestURL();
		if(urI!=null&&urI.startsWith("/credit/front")){
			HttpServletResponse response = controller.getResponse();
			response.setCharacterEncoding("utf-8");
			SysUser user = null;
			user = (SysUser) request.getAttribute(Attr.SESSION_NAME);
			if(request.getAttribute("user")==null||(int)user.get("userid")<=0&&urI.indexOf("showLogin")==-1){
					String str = url.substring(0,url.toString().replace("http://", "").indexOf("/")+7)+"/credit/front/usercenter/showLogin";
					controller.redirect(str);
			}
			inv.invoke();
		}
		
	}

}
