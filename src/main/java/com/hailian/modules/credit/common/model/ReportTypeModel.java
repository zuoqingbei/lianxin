package com.hailian.modules.credit.common.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.utils.DataAuthorUtils;
import com.hailian.util.StrUtils;
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
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("name");
		columnnNames.add("name_en");
		columnnNames.add("name_trad");
		columnnNames.add("tpl_path");
		columnnNames.add("remarks");
		columnnNames.add("order_no");
		columnnNames.add("create_date");
	}
	
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

	public Page<ReportTypeModel> pagerReportType(int pageNumber, int pagerSize, String keyWord,String orderBy,String searchType,BaseProjectController c) {
		String authorSql = DataAuthorUtils.getAuthorByUser(c);
		StringBuffer selectSql = new StringBuffer(" select *,u.realname ");
		StringBuffer fromSql = new StringBuffer(" from credit_report_type t LEFT JOIN sys_user u ON u.userid = t.create_by WHERE t.del_flag=0 ");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		if (StringUtil.isNotEmpty(keyWord)) {
			fromSql.append(" and ");
			for (int i = 0; i < columnnNames.size(); i++) {
				if("create_date".equals(columnnNames.get(i))){
					continue;
				}
				if(i!=0){
					fromSql.append(" || ");
				}
				//搜索类型
				if("0".equals(searchType)){
					fromSql.append(columnnNames.get(i)+" like concat('%',?,'%')");
				}else{
					fromSql.append(columnnNames.get(i)+" = ? ");
				}
				params.add(keyWord);//传入的参数
			}
		}
		//权限区分
		if(!c.isAdmin(c.getSessionUser())){
			fromSql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		//排序
		if (StrUtils.isEmpty(orderBy)) {
			fromSql.append(" order by t.create_date desc");
		} else {
			fromSql.append(" order by ").append(orderBy);
		}
		
		Page<ReportTypeModel> reportTypePage = ReportTypeModel.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),
				fromSql.toString(), params.toArray());
		return reportTypePage;
	}
	/**
	 * @todo   获取全部报告类型
	 * @time   2018年9月4日 下午7:17:02
	 * @author zuoqb
	 * @params
	 */
	public List<ReportTypeModel> getReportType() {
		return dao.find("select t.* from credit_report_type t where t.del_flag='0' order by order_no ");
	}
	/*
	 * 根据报告类型名获取报告
	 */
	public static List<ReportTypeModel> getReportTypeByName(String name) {
		String sql="select t.* from credit_report_type t where t.del_flag='0' ";
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(name)){
			sql+=" and t.name=? or t.name_en=? ";
			params.add(name);
			params.add(name);
		}
		return ReportTypeModel.dao.find(sql,params.toArray());
	}


}
