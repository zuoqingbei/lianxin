package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.credit.common.model.ReportTypeModel;

public class TemplateCustomService extends BaseService{
	/**
	 * 
	 * @time   2018年9月28日 下午4:21:10
	 * @author yangdong
	 * @todo   TODO 获取客户列表,如果有默认值则选择默认值,如果没有默认值就选择第一个选项
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
	public String getCustomStringObject (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCustomInfo> listCustom = new ArrayList<CreditCustomInfo>();
		listCustom.addAll(CreditCustomInfo.dao.findcustoms());
		for(CreditCustomInfo detail:listCustom){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
			}else{
				sb.append("<option m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
				}
			}
			
		return sb.toString();
	}
	/**
	 * 
	 * @time   2018年10月10日 下午1:56:24
	 * @author yangdong
	 * @todo   TODO 获取客户列表,如果有默认值则选择默认值,如果没有默认值就选择请选择客户
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
	public String getCustomStringObject1 (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCustomInfo> listCustom = new ArrayList<CreditCustomInfo>();
		listCustom.add(getDefaultCustomDetail());
		listCustom.addAll(CreditCustomInfo.dao.findcustoms());
		for(CreditCustomInfo detail:listCustom){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}else{
				if("请选择客户".equals(detail.getStr("name"))){
				sb.append("<option selected='selected' m-type='' value=''>"+detail.get("name")+"</option>");
				}
				}
			 sb.append("<option m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}
			
		return sb.toString();
	}
	/**
	 * 
	 * @time   2018年10月11日 下午4:18:56
	 * @author yangdong
	 * @todo   TODO 获取客户列表,如果有默认值则选择默认值,如果没有默认值就选择请选择客户id
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
	public String getCustomNameStringObject (String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditCustomInfo> listCustom = new ArrayList<CreditCustomInfo>();
		listCustom.add(getDefaultCustomDetail());
		listCustom.addAll(CreditCustomInfo.dao.findcustoms());
		for(CreditCustomInfo detail:listCustom){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
			}else{
				if("请选择客户".equals(detail.getStr("name"))){
				sb.append("<option selected='selected' m-type='' value=''>"+detail.get("name")+"</option>");
				}				
				}
				sb.append("<option m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");

			}
			
		return sb.toString();
	}
	
	protected CreditCustomInfo getDefaultCustomDetail() {
		CreditCustomInfo allDict=new CreditCustomInfo();
		allDict.set("id", "");
		allDict.set("name","请选择客户");
		return allDict;
	}
	
}
