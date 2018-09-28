package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;

public class TemplateCompanyService extends BaseService{
	
	public String getCompanyStringObject (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCompanyInfo> listCompany = new ArrayList<CreditCompanyInfo>();
		listCompany.addAll(CreditCompanyInfo.dao.getCompany());
		for(CreditCompanyInfo detail:listCompany){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected'  m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}else{
				sb.append("<option m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
				}
			}
			
		return sb.toString();
	}
}
