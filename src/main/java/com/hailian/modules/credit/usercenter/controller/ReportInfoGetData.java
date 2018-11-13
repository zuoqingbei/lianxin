package com.hailian.modules.credit.usercenter.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.system.dict.DictCache;
import com.jfinal.plugin.activerecord.Db;

public abstract class ReportInfoGetData extends BaseProjectController {
	/**
	 * 获取bootStrapTable式的数据
	 */
	abstract void getBootStrapTable();
	/**
	 * 修改或者更新bootStrapTable式的数据
	 */
	abstract void alterBootStrapTable();
	/**
	 * 获取form式的数据
	 */
	abstract void getForm();
	/**
	 * 保存或修改form的数据
	 */
	abstract void alterForm();
	/**
	 * 从字典表里获取下拉选
	 * @param type
	 * @param selectedId
	 * @param disPalyCol
	 */
	abstract void getSelete();
	
	void deleteOneEntry(String className,String id) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class entryType = null;
		BaseProjectModel model = null;
		entryType = Class.forName(className);
		//根据Class对象创建实例
		model = (BaseProjectModel) entryType.newInstance();
		Integer userId = getSessionUser().getUserid();
		String now = getNow();
		model.set("id", id);
		model.set("update_by", userId);
		model.set("update_date", now);
		model.set("del_flag", 1);
		model.update();
	 };
	/**
	 * 把 形如[{"a":b,"a1":b1},{"c":d,"a1":b1},{"e":f,"a1":b1}]的json数组分解放进model里并保存
	 * @param jsonStr
	 * @param entryTypeParam
	 * @param flag
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	 <T> List<BaseProjectModel> infoEntry(String jsonStr,String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
			Integer userId = getSessionUser().getUserid();
			String now = getNow();
			//实体是否存在id
			boolean exitsId = true; 
			if(jsonStr==null||"".equals(jsonStr.trim())||!jsonStr.contains("{")||!jsonStr.contains(":")){
				return new ArrayList<>();
			}
			List<BaseProjectModel> list = new ArrayList<BaseProjectModel>();
			//反射获取Class对象
			@SuppressWarnings("rawtypes")
			Class entryType = entryType = Class.forName(className);
			BaseProjectModel model = null;
		  
			System.out.println("\n\t\t\t\ttable:"+className.substring(className.lastIndexOf(".")+1)+"\n");
			
			List<Map<Object, Object>> entrys = parseJsonArray(jsonStr);
			if(entrys.size()<1){
				return null;
			}
			if("".equals(entrys.get(0).get("id"))||entrys.get(0).get("id")==null){
				 exitsId = false;
			}
			for (Map<Object, Object> entry : entrys) {
				 //根据Class对象创建实例
			    model = (BaseProjectModel) entryType.newInstance();
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
			return list;
		}
	/**
	 * 解析形如[{"a":b,"a1":b1},{"c":d,"a1":b1},{"e":f,"a1":b1}]的json数组
	 * @param jsonStr
	 * @return 
	 */
	public static  List<Map<Object,Object>> parseJsonArray(String jsonStr){
		List<Map<Object,Object>> list = new ArrayList<>();
		if(jsonStr==null||"".equals(jsonStr.trim())||!jsonStr.contains("{")||!jsonStr.contains(":")){
			return new ArrayList<>();
		}
		String jsonStr2 = jsonStr.replace("\"", "");
		String[] jsonStr3 = jsonStr2.split("}");
		Map<Object,Object> map = null;
		if(jsonStr3!=null||"".equals(jsonStr3)){
			for (String string : jsonStr3) {
				map = new LinkedHashMap<>();
				if(string==null||"".equals(string.trim())||"]".equals(string.trim())){
					continue;
				}
				String[] string4 = string.split(",");
				for (String string2 : string4) {
					if(string2==null||"".equals(string2)){
						continue;
					}
					String string5 = string2.substring(string2.indexOf("{")+1,string2.indexOf(":"));
					String string6 =  string2.substring(string2.indexOf(":")+1);
					if("null".equals(string6)){
						continue;
					}
					map.put(string5, string6);
				}
				list.add(map);
				for (Object key : map.keySet()) {
					System.out.println(key+":"+map.get(key));
				}
			}
		}
		return list;
	}
	/**
	 * 识别传入的参数从而将字典id转化为汉字
	 * @param selectSource
	 * @param rows
	 * @param columnName
	 */
	public static void dictIdToString(List<BaseProjectModel> rows,List<Map<Object,Object>>  selectInfoMap){
		for (Map<Object, Object> entry : selectInfoMap) {
			for (Object key : entry.keySet()) {
				//将字典id转化为汉字
				String selectSource = (""+key).trim();
				String columnName = (""+(entry.get(key))).trim();
				String type = selectSource.substring(selectSource.indexOf("?type=")+6,selectSource.indexOf("$selectedId=")).trim();
				String disPalyCol = selectSource.substring(selectSource.indexOf("$disPalyCol=")+12).trim();
				for (BaseProjectModel model : rows) {
					model.put(columnName, DictCache.getValueByCode(type, model.get(columnName)+"", disPalyCol));
				}
			}
		}
	}
	
	/**
	 * 判断是否是公司主表
	 */
	boolean  isCompanyMainTable() {
		String tableName = getPara("tableName","");
		String className = getPara("className","");
		return tableName.equals("credit_company_info")||className.equals("CreditCompanyInfo");
	}
}
