package com.hailian.modules.credit.ordertranslate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONNull;
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
		String json = getPara("dataJson").replace("null", "''");
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
						if(!"12".equals(reporttype)){
							value_en = TransApi.Trans(value.replace(" ", "").replace("%", ""),"en");
						}
						
					} catch (Exception e) {
                        e.printStackTrace();
						value_en="";
//						if(targetlanguage.equals("cht")){
//							 value_en="";
//						}
                       
					}
					if("cht".equals(targetlanguage)){
						try {
							value_cht=TransApi.Trans(value.replace(" ", "").replace("%", ""),targetlanguage);
							if("14".equals(reporttype)){
								if("chairman".equals(key) || "vice_president".equals(key) || "board_members".equals(key) 
										|| "supervisory_board_chairman".equals(key) || "general_manager".equals(key) || "vice_general_manager".equals(key) || "managing_partner".equals(key)
										|| "name".equals(key) || "legal".equals(key) || "executive_director".equals(key) || "members_of_the_supervisors".equals(key) || "operation_scope".equals(key)){
									value_en="";
								}
								if("name_en".equals(key) || "business_date_end".equals(key) || "registration_authority".equals(key)){
									value_cht="";
								}
								if("type_of_enterprise_remark".equals(key)){
									continue;
								}
								
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							value_cht="";
//							value_cht=value;
						}
					}
			    	TranslateModel translateByError = TranslateService.service.getTranslateByError(value);//翻译校正
			    	if(translateByError!=null){
			    		value_en = translateByError.get("correct_phrase");//翻译校正
			    	}
			    	if(StringUtils.isNotBlank(value_en) && StringUtils.isNotBlank(value_cht)){
			    		value=value_en+"|"+value_cht;
			    	}else{
			    		value=value_en+value_cht;
			    	}
			    	
				}
			}
			jsonObject.put(key, value);
			}
//			System.out.println(jsonObject.toString().replace("\"null\"", ""));
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
		String  value_cht=TransApi.Trans("陈少杰","en");
		System.out.println(value_cht);

		 
	}
}
