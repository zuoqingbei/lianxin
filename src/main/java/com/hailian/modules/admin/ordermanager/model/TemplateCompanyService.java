package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;

public class TemplateCompanyService extends BaseService{
	/**
	 * 
	 * @time   2018年9月28日 下午4:22:08
	 * @author yangdong
	 * @todo   TODO  获取公司列表,如果有默认值则选择默认值
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
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
	/**
	 * 
	 * @time   2018年9月30日 上午11:13:07
	 * @author yangdong
	 * @todo   TODO 默认选项请选择公司id
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
	public String getCompanyStringObject2 (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCompanyInfo> listCompany = new ArrayList<CreditCompanyInfo>();
		listCompany.addAll(CreditCompanyInfo.dao.getCompany());
		listCompany.add(getDefaultCompany());
		for(CreditCompanyInfo detail:listCompany){
			
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected'  m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("name"))){
					sb.append("<option  selected='selected'  m-type1='' m-type=''  value=''>请选择公司id</option>");
				}else{
				sb.append("<option m-type1='"+detail.get("name")+"' m-type='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
				}
			}
		}
			
		return sb.toString();
	}
	
	public CreditCompanyInfo getDefaultCompany() {
		CreditCompanyInfo company=new CreditCompanyInfo();
		company.set("name", "All");
		company.set("name_en", "");
		company.set("id", "");
		return company;
	}
}
