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
@ModelBind(table = "credit_report_temp_conf")
public class CreditReportTempConf extends BaseProjectModel<CreditReportTempConf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditReportTempConf dao = new CreditReportTempConf();//名字都叫dao，统一命名
	public Page<CreditReportTempConf> findTemps(CreditReportTempConf temp, Paginator pageinator,
			BaseProjectController c) {
		StringBuffer sql = new StringBuffer();
		//报告类型
		String report = temp.getStr("report");
		//模板名称
		String temp_name = temp.getStr("temp_name");
		//创建者
		String create = temp.getStr("create");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_report_temp_conf t ");
		sql.append(" left join credit_report_type c on c.id=t.report ");
		sql.append(" left join sys_user s on s.userid=t.create ");
		sql.append(" left join sys_user s1 on s1.userid=t.update ");
		sql.append(" left join credit_report_temp_conf t1 on t1.id=t.parent_temp ");
		sql.append(" where t.del_flag=0 and 1=1 ");
		if (!c.isAdmin(c.getSessionUser())) {
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}
		if (StringUtils.isNotBlank(report)) {
			sql.append(" and t.report=?");
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
		Page<CreditReportTempConf> page = dao
				.paginate(
						pageinator,
						"select t.*,c.name as reportName,s.username as createName,"
						+ "s1.username as updateName,t1.temp_name as parentTempName ",
						sql.toString(), params.toArray());
		return page;
	}
	public List<CreditReportTempConf> findParentNodes(String parent_temp, String report) {
		String sql="select t.* from credit_report_temp_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report=? ";
		return dao.find(sql, parent_temp,report);
	}
	public List<CreditReportTempConf> getAllTemp() {
		
		 return dao.find("select t.* from credit_report_temp_conf t where t.del_flag='0'  ");
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
	public List<CreditReportTempConf> findByReport(String report) {
		String sql="select t.* from credit_report_temp_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report=? ";
		return dao.find(sql, parent_temp,report);
	}

}
