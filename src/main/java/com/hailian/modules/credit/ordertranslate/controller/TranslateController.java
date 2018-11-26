package com.hailian.modules.credit.ordertranslate.controller;

import java.util.Iterator;
import java.util.List;








import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.jfinal.kit.HttpKit;

/**
 * @Description: 翻译接口
* @author: dsh 
* @date:  2018年11月26日上午10:49:09
 */
@ControllerBind(controllerKey = "/credit/ordertranslate")
public class TranslateController extends BaseProjectController {
	
	/**
	 * 获取表格json数据 插入数据库
	* @author doushuihai  
	* @date 2018年9月26日下午4:36:27  
	* @TODO
	 */
	@Test
	public void translate() {
//		String jsonString = HttpKit.readData(getRequest());
		String json="{\"id\":\"10001\",\"name\":\"肉类\"}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator iterator = jsonObject.keys();
		System.out.println(json);
		while(iterator.hasNext()){
        String   key = (String) iterator.next();
        String value = jsonObject.getString(key);
}
		
			
		
	}
	
	
}
