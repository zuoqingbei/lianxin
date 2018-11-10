package com.hailian.modules.credit.usercenter.controller;

import java.util.List;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.front.template.TemplateDictService;
import com.jfinal.plugin.activerecord.Record;
@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController  extends ReportInfoGetData {
	  private TemplateDictService template = new TemplateDictService();
	  private final static String PAKAGENAME_PRE = "com.hailian.modules.admin.ordermanager.model.";
	 /**
	  * 获取bootstraptable类型的数据
	  * 链接形如: http://localhost:8080/credit/front/ReportGetData/getBootStrapTable?conf_id=18
	  * company_id=24&report_type=1&tableName=credit_company_his&className=CreditCompanyHis
	 */
	  public void getBootStrapTable() {
		Record record = new Record();
		String tableName = getPara("tableName","");
		String className = getPara("className");
		String confId = getPara("conf_id","");
		//解析实体获取required参数
		CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
		String getSource = confModel.getStr("get_source");
		String[] requireds = getSource.split("\\*");
		String[] required = requireds[1].split("\\$");
		StringBuffer sqlSuf = new StringBuffer();
		for (String str : required) {
			sqlSuf.append(str.trim()+"="+getPara(str).trim()+" and ");
		}
		if(sqlSuf.length()<1){
			renderJson(record.set("rows", null));
			return;
		}
		List rows = null;
		try {
			Class<?> table = Class.forName(PAKAGENAME_PRE+className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find("select * from "+tableName+" where del_flag=0 and "+sqlSuf+" 1=1");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		renderJson(record.set("rows", rows));
		renderJson(record.set("total", rows.size()));
	}
	 /**
	  * 2018/11/9 lzg
	 * 删除bootStrapTable式的数据
	  */
	  public void deleteOneEntry() {
			try {
				this.deleteOneEntry(PAKAGENAME_PRE+getPara("className"),getPara("id"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	/**
	 * 2018/11/8 lzg
	 * 修改或者新增bootStrapTable式的数据
	 */
	public void alterBootStrapTable() {
		try {
			this.infoEntry(getPara("dataJson"), PAKAGENAME_PRE+getPara("className"));
			renderJson(new ResultType(0,"操作成功!"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(1,"类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(1,"实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(1,"非法存取异常!"));
			e.printStackTrace();
		}
	}
	
	@Override
	public void getForm() {
		// TODO Auto-generated method stub

	}

	public void getSelete() {
	 String selectStr = template.getSysDictDetailString3(getPara("type"), getPara("selectedId"), getPara("disPalyCol"));
	 renderJson(new Record().set("selectStr", selectStr));
	}
	


	
}
