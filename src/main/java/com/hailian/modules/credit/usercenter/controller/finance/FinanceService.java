package com.hailian.modules.credit.usercenter.controller.finance;

import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.jfinal.plugin.activerecord.Db;
public class FinanceService {

	
	/**
	 * 获取财务配置信息
	 */
	public static  List<Integer> getFinancialConfig() {
		List<CreditCompanyFinancialDict> list =  CreditCompanyFinancialDict.dao.find("select * from credit_company_financial_statements_conf");
		return null;
	}
	
	
	/**
	 * 增加或者修改财务配置信息
	 */
	public void alterFinancialConfig() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 删除财务配置信息
	 */
	public void deleteFinancialConfig() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 获取财务字典表信息(不包含合计项目)
	 */
	public void getFinancialDictExTotal() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 获取财务字典表信息 (所有)
	 */
	public void getFinancialDict() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * 从字典表获取数据里添加模板到实体表(页面触发)
	 */
	public void addDictConfigToBeFinancialEntry() {
		getFinancialDict();
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * 从字典表获取数据里添加模板到实体表(excel上传触发)
	 */
	public void addDictConfigToBeFinancialEntryForUpload() {
		getFinancialDictExTotal();
		
	}
	
	
	
	/**
	 * 获取财务实体表信息
	 */
	public void getFinancialEntryList() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * 增加或者修改财务实体表信息(页面触发)
	 */
	public void alterFinancialEntryList() {
		// TODO Auto-generated method stub
	}
	
	
	
	/**
	 * 增加或者修改财务实体表信息(excel上传触发)
	 */
	public void alterFinancialEntryListForUpload() {
		//解析excel,获取财务实体对象
		
		//将实体插入财务信息实体表
	}
	
	
	
	/**
	 * 删除财务实体信息
	 */
	public void deleteFinancialEntryList() {
		// TODO Auto-generated method stub
		
	}
	
}
