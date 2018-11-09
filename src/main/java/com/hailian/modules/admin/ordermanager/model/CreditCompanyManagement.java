package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

@ModelBind(table = "credit_company_management")
//此标签用于模型与数据库表的连接
public class CreditCompanyManagement extends BaseProjectModel<CreditCompanyManagement> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyManagement dao = new CreditCompanyManagement();//名字都叫dao，统一命名
	public CreditCompanyManagement getBycomIdAndType(String companyid,String reporttype){
		String sql="select * from credit_company_management where del_flag=0 and company_id=? and report_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(reporttype);
		return dao.findFirst(sql, params.toArray());
	}
	/**
	 * 根据公司id和报告类型删除记录
	* @author doushuihai  
	* @date 2018年11月9日上午9:24:47  
	* @TODO
	 */
	public void deleteBycomIdAndType(String companyid,String reporttype){
		String sql="update credit_company_management set del_flag=1 where company_id=? and report_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(reporttype);
		Db.update(sql, params.toArray());
	}

}
