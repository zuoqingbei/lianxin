package com.hailian.modules.credit.usercenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
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
		//实体是否存在id
		boolean exitsId = true; 
		if(jsonStr==null||"".equals(jsonStr.trim())||!jsonStr.contains("{")||!jsonStr.contains(":")){
			return new ArrayList<BaseProjectModel>();
		}
		List<BaseProjectModel> list = new ArrayList<BaseProjectModel>();
		Map<String,String> map = new HashMap<>();
		String jsonStr2 = jsonStr.replace("\"", "");
		String[] jsonStr3 = jsonStr2.split("}");
		if(jsonStr3!=null||"".equals(jsonStr3)){
			for (String string : jsonStr3) {
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
				//反射获取Class对象
				@SuppressWarnings("rawtypes")
				Class entryType = null;
				BaseProjectModel model = null;
					entryType = Class.forName(className);
						//根据Class对象创建实例
						model = (BaseProjectModel) entryType.newInstance();
				System.out.println("\n\t\t\t\ttable:"+className.substring(className.lastIndexOf(".")+1)+"\n");
				if("".equals(map.get("id"))||map.get("id")==null){
					 exitsId = false;
				}
				for (String key : map.keySet()) {
					System.out.println(key+":"+map.get(key));
					model.set(key.trim(), map.get(key).trim());
				}
				Integer userId = getSessionUser().getUserid();
				String now = getNow();
				model.set("update_by", userId);
				model.set("update_date", now);
				if(!exitsId){
					model.set("create_by", userId);
					model.set("create_date", now);
				}
				list.add(model);
				map.clear();
			}
			
		}
		if(!exitsId){
			Db.batchSave(list, list.size());
		}else{
			Db.batchUpdate(list, list.size());
		}
		return list;
	}
	  
}
