package com.hailian.modules.credit.reportmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Page;
@ModelBind(table = "credit_report_module_conf")
public class CreditReportModuleConf extends BaseProjectModel<CreditReportModuleConf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditReportModuleConf dao = new CreditReportModuleConf();//名字都叫dao，统一命名
	public Page<CreditReportModuleConf> findTemps(CreditReportModuleConf temp, Paginator pageinator,
			BaseProjectController c) {
		StringBuffer sql = new StringBuffer();
		//报告类型
		String report = temp.getStr("report_type");
		//模板名称
		String temp_name = temp.getStr("temp_name");
		//创建者
		String create = temp.getStr("create");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_report_module_conf t ");
		sql.append(" left join credit_report_type c on c.id=t.report_type ");
		sql.append(" left join sys_user s on s.userid=t.create ");
		sql.append(" left join sys_user s1 on s1.userid=t.update ");
		sql.append(" left join credit_report_module_conf t1 on t1.id=t.parent_temp ");
		sql.append(" where t.del_flag=0 and 1=1 ");
		if (!c.isAdmin(c.getSessionUser())) {
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}
		if (StringUtils.isNotBlank(report)) {
			sql.append(" and t.report_type=?");
			params.add(report);
		}
		if (StringUtils.isNotBlank(temp_name)) {
			sql.append(" and t.temp_name like concat('%',?,'%')");
			params.add(temp_name);
		}
		if (StringUtils.isNotBlank(create)) {
			sql.append(" and t.create=?");
			params.add(create);
		}
		Page<CreditReportModuleConf> page = dao
				.paginate(
						pageinator,
						"select t.*,c.name as reportName,s.username as createName,"
						+ "s1.username as updateName,t1.temp_name as parentTempName ",
						sql.toString(), params.toArray());
		return page;
	}
	public List<CreditReportModuleConf> z(String parent_temp, String report) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report_type=? ";
		return dao.find(sql, parent_temp,report);
	}
	
	public List<CreditReportModuleConf> findReportNodes(String report) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.report_type=? ";
		return dao.find(sql,report);
	}
	public List<CreditReportModuleConf> getAllTemp() {
		
		 return dao.find("select t.* from credit_report_module_conf t where t.del_flag='0'  ");
	}
	/**
	 * 
	 * @time   2018年10月25日 下午1:04:15
	 * @author yangdong
	 * @todo   TODO 根据报告类型和节点类型查找父模板
	 * @param  @param report
	 * @param  @return
	 * @return_type   List<CreditReportTempConf>
	 */
	public List<CreditReportModuleConf> findByReport(String report) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.node_level=1 and t.report_type=? order by sort,id";
		return dao.find(sql,report);
	}
	public List<CreditReportModuleConf> findReportType() {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.parent_temp=-9999999 order by sort,id";
		return dao.find(sql);
	}
	public CreditReportModuleConf findReportModuleById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from credit_report_module_conf t ");
		sql.append(" left join credit_report_type c on c.id=t.report_type ");
		sql.append(" left join sys_user s on s.userid=t.create ");
		sql.append(" left join sys_user s1 on s1.userid=t.update ");
		sql.append(" left join credit_report_module_conf t1 on t1.id=t.parent_temp ");
		sql.append(" where t.del_flag=0 and 1=1 and t.id=? order by sort,id");
		return dao.findFirst("select t.*,c.name as reportName,s.username as createName,s1.username as updateName"
				+ ",t1.temp_name as parentTempName "+sql.toString(),id);
	}
	/**
	 * 默认模块
	 * @return
	 */
	public List<CreditReportModuleConf> getDefaultModule() {
		String sql = "select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.id=1 or parent_temp=1 order by sort,id";
		return dao.find(sql);
	}
	
}
