package com.hailian.modules.credit.common.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.utils.DataAuthorUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo 报告类型持久层处理
 * @author lzg
 * @time 2018/09/03 9:50
 */
@ModelBind(table = "credit_report_type")
public class ReportTypeModel  extends BaseProjectModel<ReportTypeModel>{
	private static final long serialVersionUID = 1L;
	public static final ReportTypeModel dao = new ReportTypeModel();//名字都叫dao，统一命名
	
	/**
	 * @todo   根据Id查询报告类型
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   ReportTypeModel
	 */
	public ReportTypeModel getReportType(String orderId, BaseProjectController c) {
		return ReportTypeModel.dao.findById(orderId);
	}
	
	
	/*public ReportTypeModel getReportTypeByAttrName(String reportName, BaseProjectController c) {
		//参数集合
		List<Object> paras = new ArrayList<Object>();
		if (StringUtils.isNotBlank(reportName)) {
				paras.add(reportName);
			}
		return ReportTypeModel.dao.findByWhereAndColumns("where ", "name", paras.toArray());
	}*/
	/**
	 * @todo   分页查询报告类型
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @params pageNumber：当前页码 pagerSize：每页条数 reportName：报告名称  BaseProjectController-当前controller  必须传 以后做数据权限使用
	 */

	public Page<ReportTypeModel> pagerReportType(int pageNumber, int pagerSize, String reportName, BaseProjectController c) {
		String authorSql = DataAuthorUtils.getAuthorByUser(c);
		StringBuffer selectSql = new StringBuffer(" select * ");
		StringBuffer fromSql = new StringBuffer(" from credit_report_type where del_flag=0");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(reportName)) {
			fromSql.append(" and name like concat('%',?,'%') ");
			params.add(reportName);
		}
		Page<ReportTypeModel> reportTypePage = ReportTypeModel.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),
				fromSql.toString(), params.toArray());
		return reportTypePage;
	}
}
