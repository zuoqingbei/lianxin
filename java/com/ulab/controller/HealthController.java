package com.ulab.controller;

import com.jfinal.ext.route.ControllerBind;
import com.ulab.core.BaseController;
@ControllerBind(controllerKey="/health",viewPath="/phm")
public class HealthController extends BaseController{
	//转发到health.html
	public void health() {
		render("health.html");
	}
	
}
