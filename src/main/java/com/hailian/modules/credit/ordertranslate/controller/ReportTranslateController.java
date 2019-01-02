package com.hailian.modules.credit.ordertranslate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

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
		String json = getPara("dataJson");
		String targetlanguage=getPara("targetlanguage");//目标语言
		String reporttype=getPara("reportType");//报告类型
		JSONObject jsonObject = JSONObject.fromObject(json);
		try {
			if(StringUtils.isBlank(targetlanguage)){
				targetlanguage="en";
			}
			Iterator iterator = jsonObject.keys();//遍历翻译代替
			while(iterator.hasNext()){
			String   key = (String) iterator.next();
			String value = jsonObject.getString(key);
			String value_en="";
			String value_cht="";
			if(isChinese(value)){
				if(!isValidDate(value)){
					try {
						value_en = TransApi.Trans(value,"en");
						
						
					} catch (Exception e) {
						value_en="Translation failure!";
					}
					if("cht".equals(targetlanguage)){
						try {
							value_cht=TransApi.Trans(value,targetlanguage);
							if("14".equals(reporttype)){
								value_en="";
								if("chairman".equals(key) || "vice_president".equals(key) || "board_members".equals(key) 
										|| "supervisory_board_chairman".equals(key) || "general_manager".equals(key) || "vice_general_manager".equals(key) || "managing_partner".equals(key)){
									value_en="";
								}
							}
							if("12".equals(reporttype)){
								value_en="";
								value_cht=TransApi.Trans(value,targetlanguage);
							}
							
						} catch (Exception e) {
							value_cht="翻譯失敗!";
						}
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
			renderJson(jsonObject.toString());
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
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
