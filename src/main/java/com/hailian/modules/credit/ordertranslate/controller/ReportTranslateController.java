package com.hailian.modules.credit.ordertranslate.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
	private static List<String> personNameColumnList = new ArrayList<>();
	
	static {
		personNameColumnList.add("legal"); 
		personNameColumnList.add("sh_name"); 
		personNameColumnList.add("th-inner"); 
		personNameColumnList.add("name"); 
	}
	
	
	
	public void translate() {
		String json = getPara("dataJson").replace("null", "''");
		String targetlanguage=getPara("targetlanguage");//目标语言
		String reporttype=getPara("reportType");//报告类型
		String className = getPara("className");//模块的key
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(json.contains("北京市石景山区西井路7号1号楼2层221室")){
			System.out.println(1);
		}
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
						System.err.println(value+"   翻译为英文失败,"+e.getMessage());
						value_en="";
					}
					if("cht".equals(targetlanguage)){
						try {
							value_cht=TransApi.Trans(value.replace(" ", "").replace("%", ""),targetlanguage);
							if("14".equals(reporttype)){ 
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
					
					List<TranslateModel> translateDict = TranslateModel.dao.refreshDict();
					
					
					//翻译校正
					if(!StrUtils.isEmpty(value,value_en)) {
						value_en = doCheck(value,value_en,translateDict);
					}
					
					if(!StrUtils.isEmpty(value,value_cht)) {
						value_cht = doCheck(value,value_cht,translateDict);
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
	 //分词翻译
	private  String doCheck(String fromCustom,String fromBaidu,List<TranslateModel> translateDict) {
		 
		if(StrUtils.isEmpty(fromCustom,fromBaidu)) {
			return fromBaidu;
		}
		Map<String,String> mapping = new HashMap<>();//占位符与字符的映射
		for (TranslateModel translateModel : translateDict) {
			String from = translateModel.get("correct_phrase_ch");//原来的中文
			if(StrUtils.isEmpty(from)) {continue;}
			if("科技".equals(from)) {
				System.out.println("又到了");
			}
			String error = translateModel.get("error_phrase");//错误翻译
			String right = translateModel.get("correct_phrase");//正确翻译
			//1.原字中含有翻译库记录的原有的中文
			
			if(fromCustom.indexOf(from)!=-1) {
				int errorIndex = fromBaidu.indexOf(error);
				//2.百度翻译的内容中含有错误的翻译         
				if(errorIndex!=-1) {
					String placeHolder = UUID.randomUUID().toString();
					fromBaidu = fromBaidu.replace(error, placeHolder);
					mapping.put(placeHolder, right);
				}
				
			}
		}
		for (String key : mapping.keySet()) {
			fromBaidu = fromBaidu.replace(key, mapping.get(key));
		}
		return fromBaidu;
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
		//String  value_cht=TransApi.Trans("股权投资,股权投资管理,商务咨询,财务咨询(不得从事代理记账),实业投资,资产管理,投资咨询。【依法须经批准的项目,经相关部门批准后方可开展经营活动】","en");
		//System.out.println(value_cht);
		 String str = "有";
		 System.out.println("如果有一天我变得很有钱!".indexOf(str));
		 System.out.println("如果有一天我变得很有钱!".lastIndexOf(str));
		 System.out.println(UUID.randomUUID());//2b4706b2-c1e3-4cd9-9915-4eaaeffcad75
	}
}

