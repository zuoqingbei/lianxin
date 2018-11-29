package com.hailian.modules.credit.ordertranslate.controller;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import net.sf.json.JSONArray;

import net.sf.json.JSONObject;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.util.translate.TransApi;

/**
 * @Description: 翻译接口
* @author: dsh 
* @date:  2018年11月26日上午10:49:09
 */
@ControllerBind(controllerKey = "/credit/ordertranslate")
public class ReportTranslateController extends BaseProjectController {
	public void translate() {
//		String jsonString = HttpKit.readData(getRequest());
		String json="{\"id\":\"hello\",\"name\":\"肉类\"}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator iterator = jsonObject.keys();//遍历翻译代替
		while(iterator.hasNext()){
        String   key = (String) iterator.next();
        String value = jsonObject.getString(key);
        if(isChinese(value)){
        	value = TransApi.Trans(value);
        	TranslateModel translateByError = TranslateService.service.getTranslateByError(value);
        	if(translateByError!=null){
        		value = translateByError.get("correct_phrase");//翻译校正
        	}
        }
        jsonObject.put(key, value);
		}
		System.out.println(jsonObject.toString());
		renderJson(jsonObject);
			
		
	}
	 public static boolean isChinese(String str){

	      String regEx = "[\\u4e00-\\u9fa5]+";

	      Pattern p = Pattern.compile(regEx);

	      Matcher m = p.matcher(str);

	     if(m.find())

	       return true;

	     else

	       return false;

	   }
}