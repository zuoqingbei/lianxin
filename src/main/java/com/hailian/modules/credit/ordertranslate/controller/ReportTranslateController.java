package com.hailian.modules.credit.ordertranslate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.util.StrUtils;
import com.hailian.util.pinyin.SpellHelper;
import com.hailian.util.translate.TransApi;

/**
 * @Description: 翻译接口
* @author: dsh 
* @date:  2018年11月26日上午10:49:09
 */
@ControllerBind(controllerKey = "/credit/ordertranslate")
public class ReportTranslateController extends BaseProjectController {
	private static List<String> personNameColumnList =new ArrayList<>();
	
	static {
		personNameColumnList.add("legal"); 
		personNameColumnList.add("sh_name"); 
		personNameColumnList.add("th-inner"); 
		personNameColumnList.add("name"); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		personNameColumnList.add(""); 
		
	}
	
	
	
	public void translate() {
		String json = getPara("dataJson").replace("null", "''");
		String targetlanguage=getPara("targetlanguage");//目标语言
		String reporttype=getPara("reportType");//报告类型
		String className = getPara("className");//模块的key
		JSONObject jsonObject = JSONObject.fromObject(json);
		try {
			if(StringUtils.isBlank(targetlanguage)){
				targetlanguage="en";
			}
			Iterator iterator = jsonObject.keys();//遍历翻译代替
			while(iterator.hasNext()){
			String   key = (String) iterator.next();
			String value = jsonObject.getString(key).replace("/n", "");
			String value_en="";
			String value_cht="";
			if(isChinese(value)){
				if(!isValidDate(value)){
					try {
						if(!"12".equals(reporttype)){
							value_en = TransApi.Trans(value.replace(" ", "").replace("%", ""),"en");
							
							/*if(!"14".equals(reporttype)){//如果是102 ROC English
								if(isPersonalName102(key)) {
									value_en = SpellHelper.getUpEname(value.trim());
								}
							}*/
							
						}
						
						//102 ROC English 出资情况中出资者字段 翻译方式为getUpEname
						/*if("14".equals(reporttype)&&"CreditCompanyShareholder".equals(className)){
							value_en = SpellHelper.getUpEname(value.trim());
						}*/
						
					} catch (Exception e) {
                        e.printStackTrace();
						value_en="";
					}
					if("cht".equals(targetlanguage)){
						try {
							value_cht=TransApi.Trans(value.replace(" ", "").replace("%", ""),targetlanguage);
							if("14".equals(reporttype)){//&&"CreditCompanyInfo".equals(className)
								if("chairman".equals(key) || "vice_president".equals(key) || "board_members".equals(key) 
										|| "supervisory_board_chairman".equals(key) || "general_manager".equals(key) || "vice_general_manager".equals(key) || "managing_partner".equals(key)
										|| "name".equals(key) || "legal".equals(key) || "executive_director".equals(key) || "members_of_the_supervisors".equals(key) 
										|| "operation_scope".equals(key) || "change_font".equals(key) || "change_back".equals(key) || "change_items".equals(key) 
										|| "embranchment_count".equals(key) || "sh_name".equals(key)){
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
			    	TranslateModel translateByError = TranslateService.service.getTranslateByError(value_en);//翻译校正
			    	TranslateModel translateByError2 = TranslateService.service.getTranslateByError(value_cht);//翻译校正
			    	if(translateByError!=null){
			    		value_en = translateByError.get("correct_phrase");//翻译校正
			    	}
			    	if(translateByError2!=null){
			    		value_cht = translateByError2.get("correct_phrase");//翻译校正
			    	}
			    	if(StringUtils.isNotBlank(value_en) && StringUtils.isNotBlank(value_cht)){
			    		//value=value_en+"|"+value_cht;
                        value=value_en+" "+value_cht;
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
	
	/**
	  * 如果是规定字段,则返回true
	 * @param columnName
	 * @return
	 */
	 private boolean isPersonalName102(String columnName) {
		 if(StrUtils.isEmpty(columnName)) return false;
		 for (String personNameColumn : personNameColumnList) {
			if(personNameColumn.equals(columnName)) {
				return true;
			}
		 }
		return false;
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
		String  value_cht=TransApi.Trans("股权投资,股权投资管理,商务咨询,财务咨询(不得从事代理记账),实业投资,资产管理,投资咨询。【依法须经批准的项目,经相关部门批准后方可开展经营活动】","en");
		System.out.println(value_cht);

		 
	}
}
