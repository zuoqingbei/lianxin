package com.ulab.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.route.ControllerBind;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.User;
/**
 * 
 * @time   2017年4月11日 上午10:59:00
 * @author zuoqb
 * @todo   测试类
 */
@ControllerBind(controllerKey = "/test", viewPath = "/test")
@Before({GlobalInterceptor.class})
public class TestController extends BaseController {
	
	//dubbo注入 预留口
	/*@Inject.BY_NAME
	private BlogService blogService;*/
    public void test() {
    	User user=User.dao.findById("7");
    	System.out.println(user);
    	setAttr("user", user);
        render("hello.html");
    }
    /**
     * 
     * @time   2017年4月11日 下午3:14:13
     * @author zuoqb
     * @todo   POST接口
     */
    @Before(POST.class)
    public void ajax() {
    	User user=User.dao.findById("7");
        renderJson(user);
    }

}
