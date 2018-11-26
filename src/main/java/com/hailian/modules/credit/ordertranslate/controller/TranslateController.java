package com.hailian.modules.credit.ordertranslate.controller;

import java.util.List;

import org.junit.Test;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
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
		List<BaseProjectModel> parseArray;
//		String jsonString = HttpKit.readData(getRequest());
		JSONObject fromObject = JSONObject.fromObject("[{name:'a',value:'aa'},{name:'b',value:'bb'},{name:'c',value:'cc'},{name:'d',value:'dd'}]");
		parseArray = JSON.parseArray("[{name:'a',value:'aa'},{name:'b',value:'bb'},{name:'c',value:'cc'},{name:'d',value:'dd'}]", BaseProjectModel.class);
		String status = fromObject.getString("Status"); //获取调用api接口的状态码	
			
		
	}
	
	
}
