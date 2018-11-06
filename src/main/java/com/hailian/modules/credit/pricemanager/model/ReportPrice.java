package com.hailian.modules.credit.pricemanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年8月23日 下午4:41:58
* @todo
*/
@ModelBind(table = "credit_report_price", key = "id")
public class ReportPrice extends BaseProjectModel<ReportPrice> {
	private static final long serialVersionUID = 1L;
	public static final ReportPrice dao = new ReportPrice();
	private static List<String> columnnNames = new ArrayList<>();
	static {
		columnnNames.add("report_type");
		columnnNames.add("order_speed");
		columnnNames.add("order_type");
		columnnNames.add("country_type");
		columnnNames.add("usabled");

	}
	private String orderType;
	private String orderSpeed;
	private String reportType;
	private String countryName;
	private String realname;
	private String usabledName;

	public String getUsabledName() {
		return get("usabledName");
	}

	public void setUsabledName(String usabledName) {
		set("usabledName", usabledName);
	}

	public String getOrderType() {
		return get("orderType");
	}

	public void setOrderType(String orderType) {
		set("orderType", orderType);
	}

	public String getOrderSpeed() {
		return get("orderSpeed");
	}

	public void setOrderSpeed(String orderSpeed) {
		set("orderSpeed", orderSpeed);
	}

	public String getReportType() {
		return get("reportType");
	}

	public void setReportType(String reportType) {
		set("reportType", reportType);
	}

	public String getCountryName() {
		return get("countryName");
	}

	public void setCountryName(String countryName) {
		set("countryName", countryName);
	}

	public String getRealname() {
		return get("realname");
	}

	public void setRealname(String realname) {
		set("realname", realname);
	}

	/**
	 * 
	 * @time   2018年8月23日 下午7:18:17
	 * @author dyc
	 * @todo   根据id查询报告价格
	 * @return_type   ReportPrice
	 */

	public ReportPrice selectId(String id, BaseProjectController c) {
		return ReportPrice.dao.findById(id);
	}

	/**
	 * 
	 * @time   2018年8月27日 上午11:59:47
	 * @author dyc
	 * @todo   分页查询报告价格信息
	 * @return_type   Page<ReportPrice>
	 */
	public Page<ReportPrice> pagePrice(int pageNumber, int pageSize, String reporttype, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select * ");
		StringBuffer fromSql = new StringBuffer(" from credit_report_price where 1=1 and del_flag=0  ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(reporttype)) {
			fromSql.append("and report_type=?");
			params.add(reporttype);
		}

		Page<ReportPrice> pricePage = ReportPrice.dao.paginate(new Paginator(pageNumber, pageSize),
				selectSql.toString(), fromSql.toString(), params.toArray());

		return pricePage;

	}

	/**
	 * 
	 * @time   2018年8月27日 上午11:42:38
	 * @author dyc
	 * @todo   根据id删除报告价格信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		ReportPrice price = ReportPrice.dao.findById(id);
		if (price != null) {
			return price.set("del_flag", 1).update();
		}
		return false;
	}

	/**
	 * 
	 * @time   2018年9月7日 下午9:30:14
	 * @author dyc
	 * @todo  根据id查询报告价格信息
	 * @return_type   Page<ReportPrice>
	 */
	public ReportPrice getId(int id, BaseProjectController c) {
		StringBuffer sql = new StringBuffer(
				"select os.detail_name as orderSpeed,rt.`name` as reportType,ot.detail_name as orderType,c.detail_name as countryName,u.realname,detail.detail_name as usabledName, t.*");
		sql.append("   from  credit_report_price t ");
		sql.append("   LEFT JOIN sys_dict_detail ot on ot.detail_id=t.order_type  ");
		sql.append("   LEFT JOIN sys_dict_detail os on os.detail_id=t.order_speed");
		sql.append("   LEFT JOIN credit_report_type rt on rt.id=t.report_type");
		sql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=t.country_type");
		sql.append("   LEFT JOIN sys_user u on u.userid=t.create_by");
		sql.append("   LEFT JOIN sys_dict_detail detail on t.usabled=detail.detail_id");
		sql.append("   where t.del_flag=0  and t.id=?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return (ReportPrice) ReportPrice.dao.findFirst(sql.toString(), params.toArray());

	}

	/**
	 * 
	 * @time   2018年9月3日 下午2:22:14
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   Page<ReportPrice>
	 */
	public Page<ReportPrice> getPage(Paginator paginator, ReportPrice attr, String orderby, BaseProjectController c) {
		StringBuffer sql = new StringBuffer(" from  credit_report_price t ");
		sql.append("   LEFT JOIN sys_dict_detail ot on ot.detail_id=t.order_type  ");
		sql.append("   LEFT JOIN sys_dict_detail os on os.detail_id=t.order_speed");
		sql.append("   LEFT JOIN credit_report_type rt on rt.id=t.report_type");
		sql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=t.country_type");
		sql.append("   LEFT JOIN sys_user u on u.userid=t.create_by");
		sql.append("   LEFT JOIN credit_country cc on cc.id=t.country_id");
		sql.append("   LEFT JOIN sys_dict_detail detail on t.usabled=detail.detail_id");
		sql.append("   where t.del_flag=0 and ot.del_flag=0 and os.del_flag=0 and rt.del_flag=0 and c.del_flag=0");
		List<Object> params = new ArrayList<Object>();
		String reportType = attr.getStr("report_type");
		String speed = attr.getStr("order_speed");
		String order = attr.getStr("order_type");
		String country = attr.getStr("country_type");
		String usable = attr.getStr("usabled");
		if (StringUtils.isNotEmpty(reportType)) {
			sql.append(" AND t.report_type = ?");
			params.add(reportType);
		}
		if (StringUtils.isNotEmpty(speed)) {
			sql.append(" AND t.order_speed = ?");
			params.add(speed);
		}
		if (StringUtils.isNotEmpty(order)) {
			sql.append(" AND t.order_type = ?");
			params.add(order);
		}
		if (StringUtils.isNotEmpty(country)) {
			sql.append(" AND t.country_type = ?");
			params.add(country);
		}
		if (StringUtils.isNotEmpty(usable)) {
			sql.append(" AND t.usabled = ?");
			params.add(usable);
		}
		if (StrUtils.isEmpty(orderby)) {
			sql.append(" order by t.create_date desc");
		} else {
			sql.append(" order by ").append(orderby);
		}
		return ReportPrice.dao
				.paginate(
						paginator,
						"select cc.name as countryName, os.detail_name as orderSpeed,rt.`name` as reportType,ot.detail_name as orderType,c.detail_name as countryName,u.realname,detail.detail_name as usabledName, t.*",
						sql.toString(), params.toArray());
	}

}
