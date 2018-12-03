package com.hailian.modules.credit.translate;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.translate.TransApi;
/**
 * 
 * @className ToEnglishController.java
 * @time   2018年9月26日 上午10:37:27
 * @author yangdong
 * @todo   TODO 翻译接口
 */
@ControllerBind(controllerKey = "/credit/translate")
public class ToEnglishController extends BaseProjectController{
	public void index() {
		
	}
	
	public void Translate() {
		String str=getPara("trans");
		String en=TransApi.Trans("密钥","en");
		renderText(en);
	}
	
}
