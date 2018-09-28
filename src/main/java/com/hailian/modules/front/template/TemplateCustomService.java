package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;

public class TemplateCustomService extends BaseService{
	
	public String getCustomStringObject (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCustomInfo> listCustom = new ArrayList<CreditCustomInfo>();
		listCustom.addAll(CreditCustomInfo.dao.findcustoms());
		for(CreditCustomInfo detail:listCustom){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}else{
				sb.append("<option m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
				}
			}
			
		return sb.toString();
	}
}
