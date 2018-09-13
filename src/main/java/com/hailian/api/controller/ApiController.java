package com.hailian.api.controller;

import com.hailian.api.form.ApiForm;
import com.hailian.api.form.ApiResp;
import com.hailian.api.interceptor.ApiInterceptor;
import com.hailian.api.service.ApiService;
import com.hailian.api.util.ApiUtils;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.StrUtils;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;

@ControllerBind(controllerKey = "/api")
@Before(ApiInterceptor.class)
public class ApiController extends BaseProjectController {

	ApiService service = new ApiService();

	/**
	 * api测试入口
	 * 
	 * 2016年10月3日 下午5:47:55 flyfox 369191470@qq.com
	 */
	public void index() {
		ApiForm from = getForm();

		renderJson(new ApiResp(from).addData("notice", "api is ok!"));
	}

	/**
	 * 开关调试日志
	 * 
	 * 2016年10月3日 下午5:47:46 flyfox 369191470@qq.com
	 */
	public void debug() {
		ApiForm from = getForm();

		ApiUtils.DEBUG = !ApiUtils.DEBUG;
		renderJson(new ApiResp(from).addData("debug", ApiUtils.DEBUG));
	}

	/**
	 * 获取信息入口
	 * 
	 * 2016年10月3日 下午1:38:27 flyfox 369191470@qq.com
	 */
	public void action() {
		long start = System.currentTimeMillis();

		ApiForm from = getForm();
		if (StrUtils.isEmpty(from.getMethod())) {
			String method = getPara();
			from.setMethod(method);
		}

		// 调用接口方法
		ApiResp resp = service.action(from);
		// 没有数据输出空
		resp = resp == null ? new ApiResp(from) : resp;

		// 调试日志
		if (ApiUtils.DEBUG) {
			log.info("API DEBUG ACTION \n[from=" + from + "]" //
					+ "\n[resp=" + JsonKit.toJson(resp) + "]" //
					+ "\n[time=" + (System.currentTimeMillis() - start) + "ms]");
		}
		renderJson(resp);
	}

	public ApiForm getForm() {
		ApiForm form = getBean(ApiForm.class, null);
		return form;
	}

}
