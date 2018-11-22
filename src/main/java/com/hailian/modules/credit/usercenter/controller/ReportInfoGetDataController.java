package com.hailian.modules.credit.usercenter.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditQualityOpintion;
import com.hailian.modules.admin.ordermanager.model.CreditQualityResult;
import com.hailian.modules.credit.reportmanager.model.CreditReportDetailConf;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.finance.ExcelModule;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Record;

@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController extends ReportInfoGetData {
	private TemplateDictService template = new TemplateDictService();
	private final static String PAKAGENAME_PRE = "com.hailian.modules.admin.ordermanager.model.";

	/**
	 * 获取form类型的数据 2018/11/12 10:13 lzg
	 */
	public void getForm() {
		getBootStrapTable();
	}
	/**
	 * 
	* @Description: 详情form
	* @date 2018年11月20日 上午11:09:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getForms() {
		getBootStrapTables();
	}

	/**
	 * 根据参数获取下拉选
	 */
	public void getSelete() {
		String selectStr = template.getSysDictDetailString3(getPara("type"), getPara("selectedId"),
				getPara("disPalyCol"));
		renderJson(new Record().set("selectStr", selectStr));
	}

	/**
	 * alterBootStrapTable
	 */
	public void alterForm() {
		alterBootStrapTable();
	}

	/**
	 * 2018/11/9 lzg 删除bootStrapTable式的数据
	 */
	public void deleteOneEntry() {
		try {
			this.deleteOneEntry(PAKAGENAME_PRE + getPara("className"), getPara("id"));
			renderJson(new ResultType(1, "删除成功!"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
	}

	/**
	 * 2018/11/8 lzg 修改或者新增bootStrapTable式的数据
	 */
	public void alterBootStrapTable() {
		if("CreditCompanySubtables".equals(getPara("className"))) {
			int a = 0;
			a++;
		}
		try {
			this.infoEntry(getPara("dataJson"), PAKAGENAME_PRE + getPara("className"), SimplifiedChinese,isCompanyMainTable());
			renderJson(new ResultType(1, "操作成功!"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
	}

	/**
	 * 获取bootstraptable类型的数据 链接形如:
	 * http://localhost:8080/credit/front/ReportGetData/getBootStrapTable?conf_id=18
	 * company_id=24&report_type=1&tableName=credit_company_his&className=CreditCompanyHis
	 * 
	 * @param isCompanyMainTable
	 */
	public void getBootStrapTable() {
		getBootStrapTable(isCompanyMainTable(), SimplifiedChinese,null);
	}
	//详情
	public void getBootStrapTables() {
		getBootStrapTables(isCompanyMainTable(), SimplifiedChinese,null);
	}


	@SuppressWarnings("unchecked")
	public void getBootStrapTable(boolean isCompanyMainTable, String sysLanguage,String companyId) {

		Record record = new Record();
		String tableName = getPara("tableName", "");
		String className = getPara("className");
		if(companyId==null||"".equals(companyId)) {
			 companyId = getPara("company_id","");
		}
		String confId = getPara("conf_id", "");
		
		// 获取关联字典表需要转义的下拉选
		String selectInfo = getPara("selectInfo");

		/*
		// 解析实体获取required参数
		CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
		String getSource = confModel.getStr("get_source");
		StringBuffer sqlSuf = new StringBuffer();
		
		if ((tableName != null && tableName.contains("_dict"))) {
			sqlSuf.append(" 1=1 and ");
		}else if ((!("".equals(getSource) || getSource == null)) && getSource.contains("*")) {
			String[] requireds = getSource.split("\\*");
			String[] required = requireds[1].split("\\$");
			for (String str : required) {
				sqlSuf.append(str.trim() + "=" + getPara(str).trim() + " and ");
			}
		} else {
			sqlSuf.append(" company_id=" + companyId.trim() + " ");

		}
		
		if (sqlSuf.length() < 1) {
			renderJson(record.set("rows", null));
			return;
		}
		
		// 如果是公司主表,将company_id改为id
		if (isCompanyMainTable) {
			String sqlSuf2 = sqlSuf + "";
			sqlSuf2 = sqlSuf2.replace("company_id", "id");
			sqlSuf = new StringBuffer(sqlSuf2);
		}
		List rows = null;
		try {
			Class<?> table = Class.forName(PAKAGENAME_PRE + className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find(
					"select * from " + tableName + " where del_flag=0 and " + sqlSuf + " 1=1 and sys_language in(?)",
					Arrays.asList(new String[] { sysLanguage }).toArray());
			if (!("".equals(selectInfo) || selectInfo == null)) {
				
				// 解析前端传入的字符串
				List<Map<Object, Object>> selectInfoMap = parseJsonArray(selectInfo);
				
				// 将id转化为字典表中对应的字符串
				dictIdToString(rows, selectInfoMap);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}*/

        List rows = getTableData(isCompanyMainTable,sysLanguage,companyId,tableName,className,confId,selectInfo);
		renderJson(record.set("rows", rows).set("total", rows!=null?rows.size():null));
	}

   //详情模式
	public void getBootStrapTables(boolean isCompanyMainTable, String sysLanguage,String companyId) {
		Record record = new Record();
		String tableName = getPara("tableName", "");
		String className = getPara("className");
		if(companyId==null||"".equals(companyId)) {
			 companyId = getPara("company_id","");
		}
		String confId = getPara("conf_id", "");
		 String orderId = getPara("orderId");
		  List rows = getTableDatas(isCompanyMainTable,sysLanguage,companyId,tableName,className,confId,orderId);
			renderJson(record.set("rows", rows).set("total", rows!=null?rows.size():null));
		}

    /**
     * 反向映射数据
     * @param sysLanguage
     * @param companyId
     * @param tableName
     * @param className
     * @param confId
     * @param selectInfo
     * @return
     */
    public List getTableData(String sysLanguage,String companyId,String tableName,String className,String confId,String selectInfo){
        boolean isCompanyMainTable = tableName.equals("credit_company_info")||className.equals("CreditCompanyInfo");
        return getTableData(isCompanyMainTable,sysLanguage,companyId,tableName,className,confId,selectInfo);
    }


      

	
    /**
     * 反向映射数据
     * @param isCompanyMainTable 是否是主表
     * @param sysLanguage 语言
     * @param companyId 公司id
     * @param tableName 表名
     * @param className 反向映射的类名
     * @param confId 父节点id
     * @param selectInfo  下拉框（把id转value）
     * @return
     */
    public List getTableData(boolean isCompanyMainTable, String sysLanguage,String companyId,String tableName,String className,String confId,String selectInfo){
		// 解析实体获取required参数
      String type=	getPara("type");
      String getSource="";
    	if (type!=null) {
			CreditReportDetailConf confModel=CreditReportDetailConf.dao.findById(confId);
			 getSource = confModel.getStr("data_source");
    	}else{
			CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
			 getSource = confModel.getStr("get_source");
		}
		
		StringBuffer sqlSuf = new StringBuffer();

		if ((tableName != null && tableName.contains("_dict"))) {
			sqlSuf.append(" 1=1 and ");
		}else if ((!("".equals(getSource) || getSource == null)) && getSource.contains("*")) {
			String[] requireds = getSource.split("\\*");
			String[] required = requireds[1].split("\\$");
			for (String str : required) {
                if(str.equals("company_id")){
                    sqlSuf.append(str.trim() + "=" + companyId + " and ");
                }else{
                    sqlSuf.append(str.trim() + "=" + getPara(str).trim() + " and ");
                }

			}
		} else {
			sqlSuf.append(" company_id=" + companyId.trim() + " ");

		}

		if (sqlSuf.length() < 1) {
			//renderJson(record.set("rows", null));
			return null;
		}

		// 如果是公司主表,将company_id改为id
		if (isCompanyMainTable) {
			String sqlSuf2 = sqlSuf + "";
			sqlSuf2 = sqlSuf2.replace("company_id", "id");
			sqlSuf = new StringBuffer(sqlSuf2);
		}
		List rows = null;
		try {
			Class<?> table = Class.forName(PAKAGENAME_PRE + className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find(
					"select * from " + tableName + " where del_flag=0 and " + sqlSuf + " 1=1 and sys_language in(?)",
					Arrays.asList(new String[] { sysLanguage }).toArray());
			if (!("".equals(selectInfo) || selectInfo == null)) {

				// 解析前端传入的字符串
				List<Map<Object, Object>> selectInfoMap = parseJsonArray(selectInfo);

				// 将id转化为字典表中对应的字符串
				dictIdToString(rows, selectInfoMap);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
        return rows;
    }
    //详情
    public List getTableDatas(boolean isCompanyMainTable, String sysLanguage,String companyId,String tableName,String className,String confId,String orderId){
        // 获取关联字典表需要转义的下拉选
		String selectInfo = getPara("selectInfo");
		List rows = null;
		try {
			Class<?> table = Class.forName(PAKAGENAME_PRE + className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find(
					"select info.*,cu.`name` as name,de.detail_name as report_language ,det.detail_name as country from credit_order_info  info "
					+ " LEFT JOIN credit_custom_info cu on info.custom_id=cu.id "
					+ " LEFT JOIN sys_dict_detail de on de.detail_id=info.report_language"
					+ " LEFT JOIN sys_dict_detail det on det.detail_id=info.country"
					+ " where info.id=?",
					orderId);
			if (!("".equals(selectInfo) || selectInfo == null)) {

				// 解析前端传入的字符串
				List<Map<Object, Object>> selectInfoMap = parseJsonArray(selectInfo);

				// 将id转化为字典表中对应的字符串
				dictIdToString(rows, selectInfoMap);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
        return rows;
    }
    
    /**
     * 根据语言参数下载财务模板
     */
	public void getFinanceExcelExport() {
		String sysLanguage = getPara("sys_language");
		if(StrUtils.isEmpty(sysLanguage)) {
			renderJson(new ResultType(0,"参数错误!"));
			return;
		}
		OutputStream ops = null;
		HttpServletResponse response = this.getResponse();
		try {
			response.reset();
			ops = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExcelModule.exportExcel(response,ops, sysLanguage);
	}
	
  /**
   * 
  * @Description: 新增/查询质检意见表
  * @date 2018年11月21日 下午3:15:50
  * @author: lxy
  * @version V1.0
  * @return
   */
    public void getOrsaveOpintion(){//质检意见新增
    	Integer userId = getSessionUser().getUserid();
		String now = getNow();
      String orderId = 	getPara("orderId");
      String id =   getPara("id"); 
      String opintion =  getPara("quality_opinion");
      String  type =   getPara("quality_type");
      String reportType =  getPara("report_type");
      String moduleId= getPara("report_module_id");
      String grade= getPara("grade");
   if (StringUtils.isBlank(id)) {
	//id为空新增
	   CreditQualityOpintion model= new CreditQualityOpintion();
	   model.set("quality_opinion", opintion);
	   model.set("quality_type", type);
	   model.set("order_id", orderId);
	   model.set("report_type", reportType);
	   model.set("grade", grade);
	   model.set("create_by", userId);
	   model.set("create_date", now);
	   model.set("update_by", userId);
	   model.set("update_date", now);
       model.save();
   }else {
	//查询
	CreditQualityOpintion opintion2=   CreditQualityOpintion.dao.findFirst("SELECT * from credit_quality_opintion where  id=?  and order_id=? and quality_type=?",id,orderId,type);
   renderJson(opintion2);
    }
      
  }

   /** 
    * 
   * @Description: 质检结果的查询与新增
   * @date 2018年11月21日 下午3:30:55
   * @author: lxy
   * @version V1.0
   * @return
    */
    public  void getOrsaveResult(){
    	Integer userId = getSessionUser().getUserid();
		String now = getNow();
    	 String orderId = 	getPara("orderId");
         String id =   getPara("id"); 
         String result =  getPara("quality_result");
         String  type =   getPara("quality_type");
         String moduleId= getPara("report_module_id");
         if (StringUtils.isBlank(id)) {
			//新增
        CreditQualityOpintion opintion2=   CreditQualityOpintion.dao.findFirst("SELECT * from credit_quality_opintion where  id=?  and order_id=? and quality_type=?",id,orderId,type);
	   CreditQualityResult model=new CreditQualityResult();
	   model.set("quality_result", result);
	   model.set("parent_id", opintion2.get("id"));
	   model.set("report_model_id", moduleId);
	   model.set("create_by", userId);
	   model.set("create_date", now);
	   model.set("update_by", userId);
	   model.set("update_date", now);
       model.save();
		}else {
	List<CreditQualityResult>	results=	CreditQualityResult.dao.find("SELECT re.*  from credit_quality_result re "
					+ " LEFT JOIN credit_quality_opintion o on o.id=re.parent_id "
					+ " where o.order_id=? and o.quality_type=?  order BY re.report_model_id",orderId,type);
		renderJson(results);
		}
    }
    /**
     * 
    * @Description: 订单流程进度
    * @date 2018年11月22日 上午10:34:09
    * @author: lxy
    * @version V1.0
    * @return
     */
    public void getflow(){
    String order_num=	getPara("order_num");
     List<CreditOrderFlow> flows=   CreditOrderFlow.dao.find("select d.detail_name as order_state,u.username as create_oper,f.create_time from credit_order_flow f "
    		+ "LEFT JOIN sys_dict_detail d on d.detail_id=f.order_state "
    		+ "LEFT JOIN sys_user u on u.userid=f.create_oper "
    		+ "where  f.order_num=?",order_num);
    renderJson(flows);
    	
    	
    }
}
