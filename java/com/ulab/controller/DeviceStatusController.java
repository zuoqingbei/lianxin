
package com.ulab.controller;

import com.jfinal.ext.route.ControllerBind;
import com.ulab.core.BaseController;

/**
 * @author chen xin
 */
@ControllerBind(controllerKey = "/deviceStatus", viewPath = "/phm")
public class DeviceStatusController extends BaseController {
	//转到实时状态页面
	public void status() {
		render("status.html");
	}

}

