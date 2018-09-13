package com.hailian.modules.credit.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hailian.jfinal.component.util.Attr;
import com.hailian.system.user.SysUser;

public class UserInterceptor implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletRequest rq = (HttpServletRequest) request; 
         HttpServletResponse rp = (HttpServletResponse) response; 
         HttpSession session = rq.getSession(); 
 		 String url = rq.getRequestURL().toString();
         SysUser user = (SysUser) session.getAttribute(Attr.SESSION_NAME);
        	 if(user==null && rq.getRequestURI().indexOf("showLogin")==-1 ){ 
				 String str = url.substring(0,url.toString().replace("http://", "").indexOf("/")+7)+"/credit/front/usercenter/showLogin";
				 rp.sendRedirect(str);
				//rq.getRequestDispatcher("/pages/credit/usercenter/showLogin.html").forward(request, response);
				//controller.redirect("credit/front/usercenter/showLogin");
				 //renderManager.getRenderFactory().getRedirectRender("credit/front/usercenter/showLogin");

             } 
        	 return;
         } 
         

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
