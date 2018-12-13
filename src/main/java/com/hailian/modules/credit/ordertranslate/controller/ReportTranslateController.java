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
			if(isChinese(value)){
				if(!isValidDate(value)){
					value = TransApi.Trans(value,targetlanguage);
			    	TranslateModel translateByError = TranslateService.service.getTranslateByError(value);
			    	if(translateByError!=null){
			    		value = translateByError.get("correct_phrase");//翻译校正
			    	}
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
//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	          format.setLenient(false);
	          format.parse(str);
	       } catch (ParseException e) {
	          // e.printStackTrace();
	// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
	 public static void main(String[] args) {
		String s="深圳市惟谷科技有限公司：本院受理原告深圳市阿拉町科技发展有限公司诉被告上海寻梦信息技术有限公司、深圳市惟谷科技有限公司侵害外观设计专利权纠纷一案，案号为(2018)粤03民初2956号。现因你下落不明，依照《中华人民共和国民事诉讼法》第九十二条之规定，向你公告送达本案的民事起诉状副本、原告 证据".replace(" ", "");
		s = TransApi.Trans(s,"en");
		System.out.println(s);
	}
}
