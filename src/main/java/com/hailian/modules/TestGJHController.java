package com.hailian.modules;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
@ControllerBind(controllerKey = "/credit/test")
public class TestGJHController extends BaseProjectController{
	public void index(){
		
		renderTemplate("index.html");
	}
}
