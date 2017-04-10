package com.controller;

import com.core.BaseController;
import com.model.User;
public class HelloController extends BaseController {
	/*@Inject.BY_NAME
	private BlogService blogService;*/
    public void hello() {
    	User user=User.dao.findById("7");
    	System.out.println(user);
        render("/WEB-INF/pages/index.html");
    }

}
