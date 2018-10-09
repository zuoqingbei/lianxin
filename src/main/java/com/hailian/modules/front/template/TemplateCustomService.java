package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;

public class TemplateCustomService extends BaseService{
	/**
	 * 
	 * @time   2018年9月28日 下午4:21:10
	 * @author yangdong
	 * @todo   TODO 获取客户列表,如果有默认值则选择默认值
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
				sb.append("<option selected='selected' m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
			}else{
				sb.append("<option m-type='"+detail.get("name")+"' value='"+detail.get("id")+"'>"+detail.get("id")+"</option>");
				}
			}
			
		return sb.toString();
	}
	
}
