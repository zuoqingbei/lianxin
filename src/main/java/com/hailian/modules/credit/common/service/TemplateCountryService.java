package com.hailian.modules.credit.common.service;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.credit.common.model.CountryModel;

public class TemplateCountryService extends BaseService{
	public List<CountryModel> getCountry() {
		List<CountryModel> listDetail = new ArrayList<CountryModel>();
		listDetail.add(getDefaultCountryDetail());
		listDetail.addAll(CountryModel.dao.findAll());
		return listDetail;
	}
	protected CountryModel getDefaultCountryDetail() {
		CountryModel allDict=new CountryModel();
		allDict.set("id", "");
		allDict.set("name","请选择国家");
		allDict.set("name_en", "");
		return allDict;
	}
	
	public String getCountryString() {
		StringBuffer sb=new StringBuffer();
		List<CountryModel> listDetail =getCountry();
		for(CountryModel detail:listDetail){
				if("请选择国家".equals(detail.getStr("name"))){
					sb.append("<option selected='selected'  value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
				}else{
					sb.append("<option  value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
				}			
		}
		return sb.toString();
	}
	
	public String getCountryString(String countryid) {
		StringBuffer sb=new StringBuffer();
		List<CountryModel> listDetail =getCountry();
		for(CountryModel detail:listDetail){
			
			 if(countryid!=null&&countryid.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected'  m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
			}else{
				if("请选择国家".equals(detail.getStr("name"))){
					sb.append("<option  selected='selected'  m-type1='' m-type=''  value=''>"+detail.get("name")+"</option>");
				}else {
					sb.append("<option m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");

				}
			}

		}
		return sb.toString();
	}
	
}
