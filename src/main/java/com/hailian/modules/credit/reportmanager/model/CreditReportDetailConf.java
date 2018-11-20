package com.hailian.modules.credit.reportmanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table = "credit_report_detail_conf")
public class CreditReportDetailConf extends BaseProjectModel<CreditReportDetailConf> {
	private static final long serialVersionUID = 1L;
	private static final long DefaultModule = -1L;
	private static final long TabFixed = -2L;
	public static final CreditReportDetailConf dao = new CreditReportDetailConf();
	
	/**
	 * 
	* @Description: 当前报告的父节点
	* @date 2018年11月19日 下午4:11:20
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<CreditReportDetailConf> findByReport(String report,String type) {
		String sql="select t.* from credit_report_detail_conf t where"
				+ " t.del_flag=0 and t.node_level=1 and t.report_type=? ";
		if (StringUtils.isNotBlank(type)) {
			sql+=" and t.is_detail='"+type+"'";
		}
		   sql+=" and t.small_module_type not in(-1,-2) order by t.sort,t.id";
		return dao.find(sql,report);
	}
	/**
	 * 
	* @Description: 获取默认模板
	* @date 2018年11月19日 下午4:11:23
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<CreditReportDetailConf> getDefaultModule(String reportType) {
		List<Object> params = new ArrayList<>();
		String sql = " select a.*  from credit_report_detail_conf a "
				+ " where parent_temp=(SELECT id from credit_report_detail_conf where small_module_type=? and report_type=? ) "
				+ " or  small_module_type=? "
				+ " and report_type=? order by sort,id ";
		params.add(DefaultModule);
		params.add(reportType);
		params.add(DefaultModule);
		params.add(reportType);
		return dao.find(sql,params.toArray());
	}
	
	/**
	 * Author:lzg
	 * 获取带锚点模板
	 * 依据small_module_type=-2
	 * @return
	 */
	public List<CreditReportDetailConf> getTabFixed(String reportType) {
		List<Object> params = new ArrayList<>();
		String sql = "select a.*  from credit_report_detail_conf a where a.small_module_type=? and report_type=? order by sort,id  ";
		params.add(TabFixed);
		params.add(reportType);
		return dao.find(sql,params.toArray());
	}
	/**
	 * 
	* @Description: 父节点下的子节点
	* @date 2018年11月19日 下午4:33:11
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<CreditReportDetailConf> findSon(String parent_temp, String report,String type) {
		String sql="select t.* from credit_report_detail_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report_type=? ";
				
				if (StringUtils.isNotBlank(type)) {
					sql+=" and t.is_detail= '"+type+"'";
				}
				sql+= " order by t.sort,t.id ";
		return dao.find(sql, parent_temp,report);
	}
}
