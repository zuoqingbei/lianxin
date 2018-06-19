
package com.ulab.controller;

import com.jfinal.ext.route.ControllerBind;
import com.ulab.core.BaseController;

/**
 * @author chen xin
 * 实时状态
 */
@ControllerBind(controllerKey = "/phm", viewPath = "/phm")
public class PhmRenderController extends BaseController {
	//系统首页
	public void index_sys(){
//		render("strategy.html");
		render("index_sys.html");
	}
	public void index(){
//		render("strategy.html");
		render("phm2.html");
	}
	public void indexAnimation(){
		render("indexAnimation.html");
	}
	//转到实时状态页面
	public void status() {
		render("status.html");
	}
	//转发到售后页面
	public void service(){
		render("service.html");
	}
	//大数据分析页面
	public void health() {
		render("health.html");
	}
	/**
	 * 
	 * @time   2017年12月6日 下午2:18:01
	 * @author zuoqb
	 * @todo   使用分析3D效果下面的波纹
	 * @param  
	 * @return_type   void
	 */
	public void lightEffect() {
		render("lightEffect.html");
	}
}

