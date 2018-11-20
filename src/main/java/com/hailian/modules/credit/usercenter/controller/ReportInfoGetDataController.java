package com.hailian.modules.credit.usercenter.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.front.template.TemplateDictService;
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
		CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
		String getSource = confModel.getStr("get_source");
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


}
