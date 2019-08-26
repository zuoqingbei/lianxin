package com.hailian.modules.credit.usercenter.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.notice.model.NoticeLogModel;
import com.hailian.modules.credit.notice.model.NoticeModel;
import com.hailian.util.DateUtils;
import org.apache.log4j.Logger;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.system.dict.DictCache;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import net.sf.json.JSONArray;

public abstract class ReportInfoGetData extends BaseProjectController {
  /**
   * 简体中文
   */
  public final static String SimplifiedChinese = "612";
  /**
   * 繁体中文
   */
  public final static String traditionalChinese = "614";
  /**
   * 英文
   */
  public final static String English = "613";
  
  
  private static Map<String,String> countryColumn = new HashMap<>();
  
  static {
	  countryColumn.put("credit_company_naturalperson_shareholder_detail", "country_type");
	  countryColumn.put("credit_company_management", "nationality");
  }
  
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
	 从字典表里获取下拉选
	 */
	abstract void getSelete();
	/**
	 * 导出财务excel
	 */
	abstract void getFinanceExcelExport( );

	/**
	 * 站内信增加错误提醒
	 * @param order
	 * @param userid
	 * @param errorMessage
	 * @param log
	 */
	public static void sendErrMsg  (CreditOrderInfo order, Integer userid, String errorMessage,Logger log) {
		try {
			//新增公告内容
			NoticeModel model = new NoticeModel();
			//公告子表添加
			NoticeLogModel logModel = new NoticeLogModel();
			model.set("notice_title", "报告异常提醒!");
			model.set("notice_content", "您查档的" + order.get("right_company_name_en") + ","+errorMessage);

			String now = DateUtils.getNow(com.hailian.util.DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();

			//向单子创建人发起(客服或者管理员角色)
			logModel.set("user_id", order.get("create_by"));
			logModel.set("notice_id", model.get("id"));
			logModel.set("read_unread", "1");
			logModel.save();

		} catch (Exception e) {
			e.printStackTrace();
			if(log!=null)
			outPutErroLog(log, e);
		}
	}

	public static void sendMessageWhenCreate  (String orderNum, Integer userid, String errorMessage) {
		try {
			//新增公告内容
			NoticeModel model = new NoticeModel();
			//公告子表添加
			NoticeLogModel logModel = new NoticeLogModel();

			model.set("notice_title", "报告创建时异常提醒!");
			if(StrUtils.isEmpty(orderNum)){
				model.set("notice_content", "您刚刚进行创建订单的操作,"+errorMessage+"  (如订单创建失败请忽略此条消息!)");
			}else{
				model.set("notice_content", "您刚刚进行创建订单的操作,订单号:"+orderNum+ ","+errorMessage+"  (如订单创建失败请忽略此条消息!)");
			}
			String now = DateUtils.getNow(com.hailian.util.DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
			//向单子创建人发起(客服或者管理员角色)
			logModel.set("user_id", userid);
			logModel.set("notice_id", model.get("id"));
			logModel.set("read_unread", "1");
			logModel.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void sendMessageWhenCreate  ( Integer userid, String errorMessage) {
		sendMessageWhenCreate(null,userid,errorMessage);
	}

	public static void sendErrMsg  (CreditOrderInfo order, Integer userid, String errorMessage )  {
		sendErrMsg ( order,  userid,  errorMessage ,null);
	}


	public static String outPutErroLog(Logger log,Exception e) {
		 String sOut = "";
	        sOut += e.getMessage() + "\r\n";
	        StackTraceElement[] trace = e.getStackTrace();
	        for (StackTraceElement s : trace) {
	            sOut += "\tat " + s + "\r\n";
	        }
	        log.error("PLUS-ERRO-INPUT==================\n"+sOut);
			return sOut.substring(0,sOut.length()>=1000?1000:sOut.length());
	}
	
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

	public boolean checkExist(boolean isMainTable,Map<Object, Object> entrys){
		boolean exitsId = true;
		if(isMainTable) {
			if("".equals(entrys.get("company_id"))||entrys.get("company_id")==null){
				 exitsId = false;
			}
		}else {
			if("".equals(entrys.get("id"))||entrys.get("id")==null){
				 exitsId = false;
			}
		}
		return exitsId;
	}
	/**
	 *  把 形如[{"a":b,"a1":b1},{"c":d,"a1":b1},{"e":f,"a1":b1}]的json数组分解放进model里并保存
	 * @param jsonStr
	 * @param className
	 * @param sysLanguage
	 * @param isMainTable
	 * @param reportType
	 * @param <T>
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> List<BaseProjectModel> infoEntry(String isTranslate,List<Map<Object, Object>> entrys,String className,String sysLanguage,boolean isMainTable,String reportType) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
			Integer userId =getSessionUser().getUserid();
			String now = getNow();
			//实体是否存在id
			//boolean exitsId = true;
			if(entrys==null||entrys.size()==0){
				return new ArrayList<>();
			}
			List<BaseProjectModel> list = new ArrayList<BaseProjectModel>();
			List<BaseProjectModel> update = new ArrayList<BaseProjectModel>();
			List<BaseProjectModel> insert = new ArrayList<BaseProjectModel>();
			//反射获取Class对象
			@SuppressWarnings("rawtypes")
			Class entryType = entryType = Class.forName(className);
			BaseProjectModel model = null;
			System.out.println("\n\t\t\t\ttable:"+className.substring(className.lastIndexOf(".")+1)+"\n");
			
			if(entrys.size()<1){
				return null;
			}
			String companyId=null;
			for (Map<Object, Object> entry : entrys) {
				boolean exit=checkExist(isMainTable, entry);
				if(isMainTable) {
					String id = entry.get("company_id")+"";
					companyId=id;
					entry.remove("company_id");
					entry.put("id",id);
					
					
				}else {
					if(!("".equals(sysLanguage)||sysLanguage==null)) {
						entry.put("sys_language", Integer.parseInt(sysLanguage));
					}
					companyId=entry.get("company_id")+"";
				}
				//根据Class对象创建实例
			    model = (BaseProjectModel) entryType.newInstance();
			    model.set("update_by", userId);
				model.set("update_date", now);
				if(!exit){
					model.set("create_by", userId);
					model.set("create_date", now);
				}
				
				Map b = new HashMap<>();
				for (Object key : entry.keySet()) {
					if("com.hailian.modules.admin.ordermanager.model.CreditCompanyHis".equals(className)){
						if(!"company_type".equals(key.toString())&&
								!"currency".equals(key.toString())&&
								!"roc_registration_status".equals(key.toString())&&
								!"register_code_type".equals(key.toString())&&
								!"year_result".equals(key.toString())&&
								!"capital_type".equals(key.toString()))
						model.set((""+key).trim(), (""+(entry.get(key))).trim());
					}else if("com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder".equals(className)){
						if(!"company_type".equals(key.toString())&&
								!"currency".equals(key.toString())&&
								!"roc_registration_status".equals(key.toString())&&
								!"register_code_type".equals(key.toString())&&
								!"year_result".equals(key.toString())&&
								!"capital_type".equals(key.toString()))
						model.set((""+key).trim(), (""+(entry.get(key))).trim());
					}else{
						model.set((""+key).trim(), (""+(entry.get(key))).trim());
					}
				}
				if(!exit){
					insert.add(model);
				}else{
					update.add(model);
				}
				list.add(model);
			}
			boolean needDetele=false;
			//若是翻译步骤且是非主表则先删除
			 if("true".equals(isTranslate)&&!isCompanyMainTable()
					 &&companyId!=null
					 &&((ReportInfoGetDataController.PAKAGENAME_PRE+"CreditCompanyHis").equals(className)||
							 (ReportInfoGetDataController.PAKAGENAME_PRE+"CreditCompanyShareholder").equals(className))){
			     needDetele=true;
			 }
			 if(needDetele){
				 //先删除
				 Class<?>  c =  Class.forName(className);
			     BaseProjectModel model2 = (BaseProjectModel) c.newInstance();
			     String sql="select * from "+model2.getTable().getName()+" where del_flag=0 and company_id=?";
			     List<BaseProjectModel> l=model2.dao().find(sql,new String[]{companyId});
			     for(BaseProjectModel m:l){
			    	 //m.set("del_flag", 1).update();
			    	 m.delete();
			     }
			     if(update.size()>0){
			    	 //子表将company_id移除掉
						 for (BaseProjectModel m : update) {
							 m.remove("id");
							 insert.add(m);
						 }
			     }
			     //新增
				 if(insert.size()>0){
					 try { 
						 
						 Db.batchSave(insert, insert.size());
					 }catch (Exception e){
						 e.printStackTrace();
						 if(e.getMessage().contains("Duplicate")){
							 if(isMainTable) {
								 for (BaseProjectModel m : insert) {
									 List<BaseProjectModel> ms = m.findByWhere(" where del_flag=0 and company_id=?", entrys.get(0).get("company_id"));
									 if (ms.size() > 0) {
										 m.set("id", ms.get(0).getInt("id"));
									 }
								 }
								 Db.batchUpdate(insert, insert.size());
							 }
						 }
					 }
				 }
			 }else{
				 //新增
				 if(insert.size()>0){
					 try { 
						 
						 Db.batchSave(insert, insert.size());
					 }catch (Exception e){
						 e.printStackTrace();
						 if(e.getMessage().contains("Duplicate")){
							 if(!isMainTable) {
								 for (BaseProjectModel m : insert) {
									 List<BaseProjectModel> ms = m.findByWhere(" where del_flag=0 and company_id=?", entrys.get(0).get("company_id"));
									 if (ms.size() > 0) {
										 m.set("id", ms.get(0).getInt("id"));
									 }
								 }
								 Db.batchUpdate(insert, insert.size());
							 }
						 }
					 }
				 }
				 if(update.size()>0){
					 //子表将company_id移除掉
					 if(isMainTable) {
						 for (BaseProjectModel m : update) {
							 m.remove("company_id");
						 }
					 }
					 Db.batchUpdate(update, update.size());
				 }
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
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        for(int i=0;i<jsonArray.size();i++){
            Map<String,Object> map = jsonArray.getJSONObject(i);
            Map<Object,Object> mapParse = jsonArray.getJSONObject(i);
            for (String key : map.keySet()) {
                mapParse.put(key.replace("锟斤拷锟斤拷之锟斤拷锟窖э拷锟", ":").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷", ",").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷1", "}").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷2", "{").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷3", "]")
                        , map.get(key).toString().replace("锟斤拷锟斤拷之锟斤拷锟窖э拷锟", ":").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷", ",").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷1", "}").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷2", "{").replace("锟э窖拷锟锟斤拷锟斤拷*锟斤拷3", "]"));
            }
            list.add(mapParse);
        }
        return list;
	}

	/**
	 * 识别传入的参数从而将字典id转化为自然语言
	 * @param rows
	 * @param selectInfoMap
	 * @param className
	 */
	public static void dictIdToString(List<BaseProjectModel> rows,List<Map<Object,Object>>  selectInfoMap,String className){
		for (Map<Object, Object> entry : selectInfoMap) {
			for (Object key : entry.keySet()) {
				//将字典id转化
				String selectSource = (""+key).trim();
				String columnName = (""+(entry.get(key))).trim();
				String type = selectSource.substring(selectSource.indexOf("?type=")+6,selectSource.indexOf("$selectedId=")).trim();
				String disPalyCol = selectSource.substring(selectSource.indexOf("$disPalyCol=")+12).trim();
				for (BaseProjectModel model : rows) {
					/*if(model.get("get_source")==null) {
						continue;
					} */
					if("country".equals(type)) {
						String value ="";;
						String flagStr = "name";
						if("detail_name_en".equals(disPalyCol)) {
							flagStr += "_en";
						}
						
						value = Db.queryStr("select "+flagStr+" from credit_country where del_flag=0 and id="+  model.get(columnName) +"");
						 if(StrUtils.isEmpty(value)) {
							 columnName =countryColumn.get(className);
							 value = Db.queryStr("select "+flagStr+" from credit_country where del_flag=0 and id="+  model.get(columnName) +"");
						 }
						model.put(columnName,value==null?"":value);
					}else {
						if(columnName.contains("id_type")){
							System.out.println("id_type");
						}
						String targetValue =  model.get(columnName)!=null? model.get(columnName)+"" : "";
						if(!StrUtils.isEmpty(targetValue)) {
							String a = "";
							String[] tempStrs = targetValue.split("\\$");
							 if(tempStrs!=null) {
								 for (String tempStr : tempStrs) {
									if(!StrUtils.isEmpty(tempStr)) {
										String finalValue = DictCache.getValueByCode(type, tempStr, disPalyCol);
										if(finalValue!=null)
										a += ","+finalValue;
									}
								 }
							 }
							 model.put(columnName,a.length()>0?a.substring(1,a.length()):"");
						}else {
							model.put(columnName,"");
						}
					}
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
	
	/**
	 * 根据报告类型判断财务字典类型
	 *//*
	public Integer getFinanceDictByReportType(String reportType) {
		List<Integer> list =   Db.query("select financial_type from credit_report_type where id=?",Arrays.asList(new String[] {reportType}).toArray());
		return list.size()==0?null:list.get(0);
	}*/
}