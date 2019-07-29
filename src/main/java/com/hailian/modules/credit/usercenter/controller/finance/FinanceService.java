package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialStatementsConf;
import com.hailian.system.dict.DictCache;
import com.hailian.util.StrUtils;
import com.hailian.util.translate.TransApi;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.sun.star.uno.RuntimeException;

/**
 * @author lzg
 */
public class FinanceService {
	
	/**
	 * 财务类型
	 */
	public final static List<Integer>   FINANCIAL_TYPE = new ArrayList<>();
	/**
	 * 由于财务模块需要翻译且百度翻译接口无法满足要求,
	 * 所以财务字典中录入了中英文版本(以后可能拓展其它语言),
	 * ZH_TO_EN_MAPPING这个变量目前记录了财务字典中文版到英文版的类型直接的映射
	 */
	 public final static Map<String,Integer> ZH_TO_EN_MAPPING = new HashMap<>();
	 static {
		 ZH_TO_EN_MAPPING.put(1+"", 2);
		 ZH_TO_EN_MAPPING.put(2+"", 2);
		 ZH_TO_EN_MAPPING.put(3+"", 3);
		 ZH_TO_EN_MAPPING.put(4+"", 3);
		 FINANCIAL_TYPE.add(1);FINANCIAL_TYPE.add(2);FINANCIAL_TYPE.add(3);FINANCIAL_TYPE.add(4);
	 }
	 
	/**
	 * 增加或者修改财务配置信息(包含在实体表里克隆一份默认的)
	 * @param dataJson
	 * @param type
	 * @param financialConfigId
	 * @param userId
	 * @param now
	 */
	public static void alterFinancialConfig(List<Map<Object, Object>> entrys, int type,String userId,String now) {
		//实体是否存在id
		boolean exitsId = true; 
		if(entrys.size()!=1) {
			return;
		}
		if("".equals(entrys.get(0).get("id"))||entrys.get(0).get("id")==null){
			 exitsId = false;
		}
		CreditCompanyFinancialStatementsConf model = new CreditCompanyFinancialStatementsConf();
		
	    model.set("update_by", userId);
		model.set("update_date", now);
		
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
					model.set("type", type);
					model.save();
					Integer financialConfId = model.get("id");
					addDictConfigToBeFinancialEntry(financialConfId+"",type,userId,now);
					return true;
				}
			});
			
		}
		
	}

	
	/**
	 * 删除财务配置信息(以及对应的财务实体信息)
	 */
	public static void deleteFinancialConfig(String financialConfId,String userId,String now) {
		 CreditCompanyFinancialStatementsConf funancialConfModel = new CreditCompanyFinancialStatementsConf();
		 funancialConfModel.set("id", financialConfId);
		 funancialConfModel.set("del_flag", "1");
		 funancialConfModel.set("update_by", userId);
		 funancialConfModel.set("update_date", now);
		 funancialConfModel.update();
		 deleteFinancialEntrysByConfig(financialConfId,  userId,  now);
	}
	
	
	/**
     * 根据配置信息批量删除财务实体
	 * @param financialConfId
	 */
	public static void deleteFinancialEntrysByConfig(String financialConfId,String userId,String now) {
		//找到对应的财务实体id
		if(!StrUtils.isEmpty(financialConfId))
		Db.update("update credit_company_financial_entry set update_by=?,update_date=?,del_flag=1 where conf_id =?",
	    Arrays.asList(new String[] {userId,now, financialConfId}).toArray());
	}
	
	/**
	 * 获取财务字典表信息 
	 */
	public static List<CreditCompanyFinancialDict> getFinancialDict(int type) {
		return DictCache.getFinancialDictMap().get(type);
	} 
	
	
	/**
	 * 从字典表获取数据里添加模板到实体表 
	 * @param type 财务实体类型
	 * @param financialConfId 财务配置id
	 */
	public static  void addDictConfigToBeFinancialEntry(String financialConfId, int  type,String userId,String now) {
		//模板里有用字段
		String [] columnName = new String[] {
				"item_name",
				"parent_sector",
				"son_sector",
				"is_sum_option",
				"sort_no",
				"is_default",
				"class_name1",
				"class_name2",
				"type"
		};
		CreditCompanyFinancialEntry entrymodel = null;
		List<CreditCompanyFinancialDict> dictList = getFinancialDict(type);
		if(dictList==null) {
			throw new RuntimeException("该报告类型没有对应的财务字典表!");
		}
		List<CreditCompanyFinancialEntry> targetList = new ArrayList<>();
			for (CreditCompanyFinancialDict dictModel : dictList) {
				entrymodel = new CreditCompanyFinancialEntry();
				for (int i = 0; i < columnName.length; i++) {
					entrymodel.set(columnName[i],dictModel.get(columnName[i]));
				}
				entrymodel.set("conf_id", financialConfId);
				entrymodel.set("create_by", userId);
				entrymodel.set("create_date", now);
				entrymodel.set("update_by", userId);
				entrymodel.set("update_date", now);
				targetList.add(entrymodel);
			}
			Db.batchSave(targetList, dictList.size());
		
	}
	
	
	
	
	/**
	 * 获取财务实体表信息
 	 *@author  lzg
	 * @param  String financialConfId,
	 * @param type 财务类型
	 */
	public static List<CreditCompanyFinancialEntry> getFinancialEntryList(String financialConfId, String type ) {
		Integer realType = ZH_TO_EN_MAPPING.get(type);
		if(StrUtils.isEmpty(financialConfId)||realType==null) {return new ArrayList<>();}
		CreditCompanyFinancialEntry model = new CreditCompanyFinancialEntry();
		List<CreditCompanyFinancialEntry> entryList
				= model.find("select * from credit_company_financial_entry where conf_id=?  and del_flag=0 order by sort_no,id ",
				  Arrays.asList(new String[] {financialConfId}).toArray());
		//获取当前财务对应的英文版字典
		List<CreditCompanyFinancialDict> englishDictList = DictCache.getFinancialDictMap().get(realType);
		if(englishDictList!=null) {
			int index = 0;
			for (CreditCompanyFinancialEntry  entry : entryList) {
				if(entry==null)continue;
				if(!(entry.getInt("is_default")==null)&&entry.getInt("is_default")==1) {//如果该条实体是默认选项
					String otherLanguageItemValue = englishDictList.get(index).get("item_name");index++;
					entry.put("item_name_en", otherLanguageItemValue);
				}else {
					String itemValue = entry.get("item_name");
					if(StringUtils.isEmpty(itemValue)) {continue;}
					String targetValue = TransApi.Trans(itemValue,"en");//百度云英文翻译
					entry.put("item_name_en", targetValue);
				}
			}
		}
		return entryList;
	}
	/**. 
	 * 获取财务配置信息
	 * @param type 
	 */
	public static List<CreditCompanyFinancialStatementsConf> getFinancialConfigList(String companyId, Object type ) {
		CreditCompanyFinancialStatementsConf model = new CreditCompanyFinancialStatementsConf();
		List<CreditCompanyFinancialStatementsConf> list
				= model.find("select * from credit_company_financial_statements_conf where company_id=? and type=? and del_flag=0 order by sort_no,id ",
				  Arrays.asList(new String[] {companyId,type+""}).toArray());
		return list;
	}
	
	
	/**
	 * 增加或者修改财务实体表信息(页面触发)
	 * @param dataJson 数据源字符串
	 * @param userId 用户id
	 * @param now 当前时间
	 * @param financialConfId 财务配置id
	 */
	public  static void alterFinancialEntryList(List<Map<Object, Object>> entrys,String userId,String now ) {
		boolean exitsId = true;
		if("".equals(entrys.get(0).get("id"))||entrys.get(0).get("id")==null){
			 exitsId = false;
		}
		List<CreditCompanyFinancialEntry> list = new ArrayList<>();
		for (Map<Object, Object> entry : entrys) {
			CreditCompanyFinancialEntry model = new CreditCompanyFinancialEntry();
		    model.set("update_by", userId);
			model.set("update_date", now);
			if(!exitsId){
				model.set("create_by", userId);
				model.set("create_date", now);
			}
			for (Object key : entry.keySet()) {
				model.set((""+key).trim(), (""+(entry.get(key))).trim());
			}
			list.add(model);
		}
		//批量执行
		if(!exitsId){
			Db.batchSave(list, list.size());
		}else{
			Db.batchUpdate(list, list.size());
		}
	}
	
	
	
	/**
	 * 增加或者修改财务实体表信息(excel上传触发)
	 * financialConfId 财务配置id
	 */
	public static String alterFinancialEntryListForUpload(File file,int type,String financeConfigId,String userId, String now) {
		//解析excel,获取财务实体对象	
		 List<CreditCompanyFinancialEntry> modelList = ExcelModule.parseExcel(file, type, financeConfigId, userId, now);
		 if(modelList==null||modelList.size()==0) {
			 return "数据量为0,导入失败!";
		 }
		boolean result = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				//删除当前配置下已有实体
				 deleteFinancialEntrysByConfig(financeConfigId,userId,now);
				 //将实体插入财务信息实体表
				 for(CreditCompanyFinancialEntry m:modelList){
					 if("营业总额/资产总额".equals(m.getStr("item_name"))){
						 System.out.println(m.getStr("begin_date_value"));
						//System.out.println( m.save());
						//System.out.println(m.get("id"));
					 }
				 }
				 System.out.println( modelList.size());
				 int[] i=Db.batchSave(modelList, modelList.size());
				 System.out.println(i.length);
				 System.out.println(i);
				 //删除无用数据
				  Db.update("delete  from credit_company_financial_entry where del_flag=9");
				return true;
			}
		});
		 return  result?"成功导入"+(modelList.size()-1)+"条数据!":"导入失败,请检查文件内容!";
	}
	
	
	
	/**
	 * 删除财务实体信息
	 */
	public static void deleteFinancialEntryList(String entryId,String userId,String now) {
		CreditCompanyFinancialEntry model = new CreditCompanyFinancialEntry();
		model.set("del_flag", "1");
		model.set("id", entryId);
	    model.set("update_by", userId);
		model.set("update_date", now);
		model.update();
	}
	
}