
package com.ulab.controller;

import com.jfinal.ext.route.ControllerBind;
import com.ulab.core.BaseController;

/**
 * @author chen xin
 * 实时状态
 */
@ControllerBind(controllerKey = "/deviceStatus", viewPath = "/phm")
public class DeviceStatusController extends BaseController {
	//转到实时状态页面
	public void status() {
		render("status.html");
	}
	public void lightEffect() {
		render("lightEffect.html");
	}
}

