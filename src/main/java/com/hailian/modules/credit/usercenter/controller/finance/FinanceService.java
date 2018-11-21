package com.hailian.modules.credit.usercenter.controller.finance;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialStatementsConf;
import com.hailian.modules.admin.ordermanager.model.CreditReditCompanyFinancialEntry;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetData;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
public class FinanceService {

	
	/**
	 * 获取财务配置信息
	 */
	public static  List<CreditCompanyFinancialStatementsConf> getFinancialConfig(String companyId,String sysLanguage,String confId) {
		if(StrUtils.isEmpty(sysLanguage,companyId,confId)) {
			return null;
		}
		ReportInfoGetDataController rgc = new ReportInfoGetDataController();
		List<CreditCompanyFinancialStatementsConf> list = rgc.getTableData( sysLanguage, companyId, "credit_company_financial_statements_conf", "CreditCompanyFinancialStatementsConf", confId,"");
		return list;
	}
	
	
	/**
	 * 增加或者修改财务配置信息(只支持单挑增加或者修改)
	 * @param jsonStr
	 * @param sysLanguage
	 * @param financialConfigId
	 * @param userId
	 * @param now
	 */
	public void alterFinancialConfig(String jsonStr, String sysLanguage,String userId,String now) {
		//实体是否存在id
		boolean exitsId = true; 
		List<Map<Object, Object>> entrys = ReportInfoGetData.parseJsonArray(jsonStr);
		if(entrys.size()!=1) {
			return;
		}
		if("".equals(entrys.get(0).get("id"))||entrys.get(0).get("id")==null){
			 exitsId = false;
		}
		CreditCompanyFinancialStatementsConf model = new CreditCompanyFinancialStatementsConf();
		
	    model.set("update_by", userId);
		model.set("update_date", now);
		
		if(!("".equals(sysLanguage)||sysLanguage==null)) {
			model.set("sys_language", Integer.parseInt(sysLanguage));
		}
		
		if(!exitsId){
			model.set("create_by", userId);
			model.set("create_date", now);
		}
		
		for (Object key : entrys.get(0).keySet()) {
			model.set((""+key).trim(), (""+(entrys.get(0).get(key))).trim());
		}
		
		if(exitsId) {
			model.update();
		}else {
			//如果是新增操作 
			Db.tx(new IAtom(){
				@Override
				public boolean run() throws SQLException {
					model.save();
					String financialConfId = model.getSql("id");
					addDictConfigToBeFinancialEntry(financialConfId,sysLanguage);
					return true;
				}
			});
			
		}
		
	}

	
	/**
	 * 删除财务配置信息
	 */
	public void deleteFinancialConfig() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * 获取财务字典表信息 
	 */
	public List<CreditCompanyFinancialDict> getFinancialDict(String sysLanguage) {
		List<CreditCompanyFinancialDict> list = null;
		if(ReportInfoGetData.English.equals(sysLanguage)) {
			list = CreditCompanyFinancialDict.englishDict;
		}else if(ReportInfoGetData.traditionalChinese.equals(sysLanguage)) {
			list = CreditCompanyFinancialDict.chineseTraditionalDict;
		}else {
			list = CreditCompanyFinancialDict.simplifiedChineseDict;
		}
		return list;
	}
	
	
	/**
	 * 从字典表获取数据里添加模板到实体表 
	 * @param sysLanguage 系统语言
	 * @param financialConfId 财务配置id
	 */
	public void addDictConfigToBeFinancialEntry(String financialConfId, String sysLanguage) {
		String [] columnName = new String[] {
				"item_name",
				"parent_sector",
				"son_sector",
				
		};
		
		CreditReditCompanyFinancialEntry entrymodel = new CreditReditCompanyFinancialEntry();
		List<CreditCompanyFinancialDict> dictList = getFinancialDict(sysLanguage);
		for (CreditCompanyFinancialDict dictModel : dictList) {
			entrymodel.set("", "");
		}
		
		
		
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
