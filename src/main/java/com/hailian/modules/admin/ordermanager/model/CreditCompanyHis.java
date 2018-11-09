package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "credit_company_his")
//此标签用于模型与数据库表的连接
public class CreditCompanyHis extends BaseProjectModel<CreditCompanyHis> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyHis dao = new CreditCompanyHis();//名字都叫dao，统一命名
	public void deleteBycomIdAndType(String companyid,String reporttype){
		String sql="update credit_company_his set del_flag=1 where company_id=? and report_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(reporttype);
		Db.update(sql, params.toArray());
	}
	

}
