package com.hailian.modules.credit.ordertranslate.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;













import org.apache.commons.lang.StringUtils;
//import org.junit.Test;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.util.translate.TransApi;
import com.jfinal.kit.HttpKit;

/**
 * @Description: 翻译接口
* @author: dsh 
* @date:  2018年11月26日上午10:49:09
 */
@ControllerBind(controllerKey = "/credit/ordertranslate")
public class ReportTranslateController extends BaseProjectController {
	public void translate() {
//		String json = HttpKit.readData(getRequest());
		String json = getPara("dataJson");
		String targetlanguage=getPara("targetlanguage");//目标语言
		if(StringUtils.isNotBlank(targetlanguage)){
			targetlanguage="en";
		}
//		String json="{\"id\":\"hello\",\"name\":\"肉类\"}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator iterator = jsonObject.keys();//遍历翻译代替
		while(iterator.hasNext()){
        String   key = (String) iterator.next();
        String value = jsonObject.getString(key);
        if(isChinese(value)){
//        	value = TransApi.Trans(value,targetlanguage);
        	value = TransApi.Trans(value,targetlanguage);
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
	public void translateRe() {
		String json = HttpKit.readData(getRequest());
		String targetlanguage=getPara("targetlanguage");//目标语言
		if(StringUtils.isNotBlank(targetlanguage)){
			targetlanguage="en";
		}
	 List<Map<String,Map>> list = new ArrayList<Map<String,Map>>();
//	 String json ="{\"scm\":{\"id\":\"hello\",\"name\":\"肉类\"},\"scm2\":{\"id2\":\"hello2\",\"name2\":\"肉类\"}}";
		//String json="{\"id\":\"hello\",\"name\":\"肉类\"}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONObject jsonObjecttemp=null;
		Iterator iterator = jsonObject.keys();//遍历翻译代替
		while(iterator.hasNext()){
        String   key = (String) iterator.next();
        Object listArray = new JSONTokener(jsonObject.getString(key)).nextValue();
        if (listArray instanceof JSONObject) {
        	JSONObject jsonObject3 = (JSONObject)listArray;
        	System.out.println(jsonObject3);
        	Iterator iterator2 = jsonObject3.keys();//遍历翻译代替
    		while(iterator2.hasNext()){
    			 String   key2 = (String) iterator2.next();
    			 String value = jsonObject3.getString(key2);
    		        if(isChinese(value)){
//    		        	value = TransApi.Trans(value,targetlanguage);
    		        	value = TransApi.Trans(value,targetlanguage);
    		        	TranslateModel translateByError = TranslateService.service.getTranslateByError(value);
    		        	if(translateByError!=null){
    		        		value = translateByError.get("correct_phrase");//翻译校正
    		        	}
    		        }
    		        jsonObject3.put(key2, value);
    		}
    		jsonObject.put(key, jsonObject3);
		}
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
