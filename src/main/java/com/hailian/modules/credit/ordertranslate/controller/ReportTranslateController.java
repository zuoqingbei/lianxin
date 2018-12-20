package com.hailian.modules.credit.ordertranslate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.modules.credit.usercenter.model.ModuleJsonData;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.util.translate.TransApi;

/**
 * @Description: 翻译接口
* @author: dsh 
* @date:  2018年11月26日上午10:49:09
 */
@ControllerBind(controllerKey = "/credit/ordertranslate")
public class ReportTranslateController extends BaseProjectController {
	public void translate() {
		try {
			String json = getPara("dataJson");
			String targetlanguage=getPara("targetlanguage");//目标语言
			if(StringUtils.isBlank(targetlanguage)){
				targetlanguage="en";
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			Iterator iterator = jsonObject.keys();//遍历翻译代替
			while(iterator.hasNext()){
			String   key = (String) iterator.next();
			String value = jsonObject.getString(key);
			String value_en="";
			String value_cht="";
			if(isChinese(value)){
				if(!isValidDate(value)){
					value_en = TransApi.Trans(value,"en");
					if("cht".equals(targetlanguage)){
						value_cht=TransApi.Trans(value,targetlanguage);
					}
			    	TranslateModel translateByError = TranslateService.service.getTranslateByError(value);//翻译校正
			    	if(translateByError!=null){
			    		value_en = translateByError.get("correct_phrase");//翻译校正
			    	}
			    	value=value_en+value_cht;
				}
			}
			jsonObject.put(key, value);
			}
			System.out.println(jsonObject.toString());
			renderJson(jsonObject.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson("Translation failure!");
		}
			
		
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
	 public static boolean isValidDate(String str) {
	      boolean convertSuccess=true;
	       SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	       try {
	          format.setLenient(false);
	          format.parse(str);
	       } catch (ParseException e) {
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
	 public static void main(String[] args) {
		  Date date;
		try {
			 date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09 12:00:00");
			 String now = new SimpleDateFormat("yyyy年MM月dd日").format(date);
			 System.out.println(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	}
}
