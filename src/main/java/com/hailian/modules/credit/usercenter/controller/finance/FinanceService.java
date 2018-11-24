package com.hailian.modules.credit.usercenter.controller.finance;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialStatementsConf;
import com.hailian.modules.admin.ordermanager.model.CreditReditCompanyFinancialEntry;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetData;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.system.dict.DictCache;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
public class FinanceService {

	
	/**
	 * 获取财务配置信息
	 */
	public   List<CreditCompanyFinancialStatementsConf> getFinancialConfig(String companyId,String sysLanguage) {
		if(StrUtils.isEmpty(sysLanguage)){
			sysLanguage = ReportInfoGetData.SimplifiedChinese;
		}
		if(StrUtils.isEmpty(companyId)) {
			return null;
		}
		CreditCompanyFinancialStatementsConf model = new CreditCompanyFinancialStatementsConf();
		List<CreditCompanyFinancialStatementsConf> list = 
				model.find("select * from credit_company_financial_statements_conf where companyId=? and sysLanguage=? and del_flag=0",
				Arrays.asList(new String[] {companyId,sysLanguage}));
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
					model.set("create_by", userId);
					model.set("create_date", now);
					model.save();
					String financialConfId = model.getSql("id");
					addDictConfigToBeFinancialEntry(financialConfId,sysLanguage,userId,now);
					return true;
				}
			});
			
		}
		
	}

	
	/**
	 * 删除财务配置信息(以及对应的财务实体信息)
	 */
	public void deleteFinancialConfig(String financialConfId,String sysLanguage) {
		 CreditCompanyFinancialStatementsConf funancialConfModel = new CreditCompanyFinancialStatementsConf();
		 funancialConfModel.set("id", financialConfId);
		 funancialConfModel.set("del_flag", "1");
		 funancialConfModel.update();
		 deleteFinancialEntrysByConfig(financialConfId);
	}
	
	
	/**
     * 根据配置信息批量删除财务实体
	 * @param financialConfId
	 */
	public void deleteFinancialEntrysByConfig(String financialConfId) {
		//找到对应的财务实体id
		 List<Integer> ids = Db.query(" select id from credit_redit_company_financial_entry where financialConfId="+financialConfId);
		 String params = "";
		 for (int i = 0; i < ids.size(); i++) {
			 if(i<ids.size()-1) {
				 params += ids.get(i)+",";
			 }else {
				 params += ids.get(i);
			 }
		 }
		 Db.update("update credit_redit_company_financial_entry set del_flag=1 where id in("+params+")");
	}
	
	/**
	 * 获取财务字典表信息 
	 */
	public static List<CreditCompanyFinancialDict> getFinancialDict(String sysLanguage) {
		return DictCache.getFinancialDictMap().get(sysLanguage);
	} 
	
	
	/**
	 * 从字典表获取数据里添加模板到实体表 
	 * @param sysLanguage 系统语言
	 * @param financialConfId 财务配置id
	 */
	public void addDictConfigToBeFinancialEntry(String financialConfId, String sysLanguage,String userId,String now) {
		//模板里有用字段
		String [] columnName = new String[] {
				"item_name",
				"parent_sector",
				"son_sector",
				"is_sum_option",
				"sort_no"
		};
		CreditReditCompanyFinancialEntry entrymodel = null;
		List<CreditCompanyFinancialDict> dictList = getFinancialDict(sysLanguage);
		for (CreditCompanyFinancialDict dictModel : dictList) {
			entrymodel = new CreditReditCompanyFinancialEntry();
			for (int i = 0; i < columnName.length; i++) {
				entrymodel.set(columnName[i],dictModel.get(columnName[i]));
			}
			entrymodel.set("sys_language", sysLanguage);
			entrymodel.set("conf_id", financialConfId);
			entrymodel.set("create_by", userId);
			entrymodel.set("create_date", now);
			entrymodel.set("update_by", userId);
			entrymodel.set("update_date", now);
			entrymodel.save();
		}
	}
	
	
	
	
	/**
	 * 获取财务实体表信息
	 */
	public List<CreditReditCompanyFinancialEntry> getFinancialEntryList(String financialConfId ) {
		CreditReditCompanyFinancialEntry model = new CreditReditCompanyFinancialEntry();
		List<CreditReditCompanyFinancialEntry> list
				= model.find("select * from credit_company_financial_entry where conf_id=?  and del_flag=0 ",
				  Arrays.asList(new String[] {financialConfId}).toArray());
		return list;
	}
	
	
	
	/**
	 * 增加或者修改财务实体表信息(页面触发)
	 * @param dataJson 数据源字符串
	 * @param sysLanguage 系统语言
	 * @param userId 用户id
	 * @param now 当前时间
	 * @param financialConfId 财务配置id
	 */
	public void alterFinancialEntryList(String dataJson, String sysLanguage,String userId,String now,String financialConfId) {
		boolean exitsId = true;
		List<Map<Object, Object>> entrys = ReportInfoGetData.parseJsonArray(dataJson);
		if(entrys.size()!=1) {
			return;
		}
		if("".equals(entrys.get(0).get("id"))||entrys.get(0).get("id")==null){
			 exitsId = false;
		}
		CreditReditCompanyFinancialEntry model = new CreditReditCompanyFinancialEntry();
		
	    model.set("update_by", userId);
		model.set("update_date", now);
		if(!("".equals(sysLanguage)||sysLanguage==null)) {
			model.set("sys_language", Integer.parseInt(sysLanguage));
		}
		
		
		for (Object key : entrys.get(0).keySet()) {
			model.set((""+key).trim(), (""+(entrys.get(0).get(key))).trim());
		}
		
		if(exitsId) {
			model.update();
		}else {
			//如果是新增操作 
			model.set("create_by", userId);
			model.set("create_date", now);
			model.set("conf_id", financialConfId);
			model.save();
		}
	}
	
	
	
	/**
	 * 增加或者修改财务实体表信息(excel上传触发)
	 * financialConfId 财务配置id
	 */
	public void alterFinancialEntryListForUpload(String financialConfId) {
		//删除当前配置下已有实体
		 deleteFinancialEntrysByConfig(financialConfId);
		//解析excel,获取财务实体对象	
		
		//将实体插入财务信息实体表
		 
	}
	
	
	
	/**
	 * 删除财务实体信息
	 */
	public void deleteFinancialEntryList(String entryId) {
		CreditReditCompanyFinancialEntry model = new CreditReditCompanyFinancialEntry();
		model.set("del_flag", "1");
		model.set("id", entryId);
		model.update();
	}
	
}
