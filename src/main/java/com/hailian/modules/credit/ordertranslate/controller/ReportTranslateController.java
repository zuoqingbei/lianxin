package com.hailian.modules.credit.ordertranslate.controller;

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
//		String json = HttpKit.readData(getRequest());
		String json = getPara("dataJson");
		String targetlanguage=getPara("targetlanguage");//目标语言
		if(StringUtils.isBlank(targetlanguage)){
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
		renderJson(jsonObject.toString());
			
		
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
	 public void alterBootStrapTable(){
		//订单id
		String orederid = getPara("id");
		//获取报告类型
		String reportType = getPara("reportType");
		//是否需要英文模板
		String istranslate = getPara("istranslate");
		//根据订单id获取订单信息
		CreditOrderInfo coi = CreditOrderInfo.dao.findById(orederid);
		if(coi==null) {
			
			return;
		}
		if("true".equals(istranslate)){
			//根据订单信息获取公司信息
			//找到当前报告类型下的父节点
			List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
			List<ModuleJsonData> list = new ArrayList<ModuleJsonData>();
			//defaultModule.forEach((CreditReportModuleConf model)->{model.removeNullValueAttrs().remove("del_flag");});
			for(CreditReportModuleConf crmc:crmcs) {
				//找到当前父节点下的子节点
				List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(crmc.get("id").toString(),reportType);
				list.add(new ModuleJsonData(crmc,child,crmc.getStr("small_module_type")));
			}
			for(ModuleJsonData data:list){
				String smallModileType = data.getSmallModileType();
				
			}
			System.out.println(list);
			System.out.println(list);
			
		}
		
	 }
}
