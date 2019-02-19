package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "credit_company_shareholder")
//此标签用于模型与数据库表的连接
public class CreditCompanyShareholder extends BaseProjectModel<CreditCompanyShareholder> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyShareholder dao = new CreditCompanyShareholder();//名字都叫dao，统一命名
	public CreditCompanyShareholder getBycomIdAndType(String companyid,String reporttype){
		String sql="select * from credit_company_shareholder where del_flag=0 and company_id=? and report_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(reporttype);
		return dao.findFirst(sql, params.toArray());
	}
	public void deleteBycomIdAndLanguage(String companyid,String sys_language){
		String sql="update credit_company_shareholder set del_flag=1 where company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		Db.update(sql, params.toArray());
	}
	public List<CreditCompanyShareholder> getBycomIdAndLanguage(String companyid,String sys_language){
		String sql="select * from credit_company_shareholder where del_flag=0 and  company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		return dao.find(sql, params.toArray());
	}
	

}
