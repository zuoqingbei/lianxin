package com.hailian.modules.admin.ordermanager.model;


import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;
/**
 * @Description: 企业商标图案
* @author: dsh 
* @date:  2018年12月11日下午1:49:52
 */
@ModelBind(table = "credit_company_brandandpatent")
//此标签用于模型与数据库表的连接
public class CreditCompanyBrandandpatent extends BaseProjectModel<CreditCompanyBrandandpatent> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyBrandandpatent dao = new CreditCompanyBrandandpatent();//名字都叫dao，统一命名
	private String RegNo ;
	private String AppDate  ;
	private String ImageUrl ;
	

	

	public String getRegNo() {
		return RegNo;
	}
	public void setRegNo(String regNo) {
		set("registration_id", regNo);
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		set("brand_url", imageUrl);
	}
	public String getAppDate() {
		return AppDate;
	}
	public void setAppDate(String appDate) {
		set("registration_date", appDate);
	}
	public void deleteBycomIdAndLanguage(String companyid,String sys_language){
		String sql="update credit_company_brandandpatent set del_flag=1 where company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		Db.update(sql, params.toArray());
	}
	
}
