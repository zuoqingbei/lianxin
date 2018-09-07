package com.hailian.modules.credit.pricemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年8月23日 下午5:20:42
* @todo  报告价格处理业务
*/
public class ReportPriceService {
	public static ReportPriceService service = new ReportPriceService();

	/**
	 * 
	 * @time   2018年8月23日 下午5:27:06
	 * @author dyc
	 * @todo   根据id查询报告价格信息
	 * @return_type   ReportPrice
	 */

	public ReportPrice selectById(String id, BaseProjectController c) {
		return ReportPrice.dao.findById(id, c);
	}

	/**
	 * 
	 * @time   2018年8月23日 下午7:21:15
	 * @author dyc
	 * @todo   分页查询订单
	 * @return_type   Page<ReportPrice>
	 */
	public Page<ReportPrice> pagePrice(int pageNumber, int pageSize, String reporttype, BaseProjectController c) {
		return ReportPrice.dao.pagePrice(pageNumber, pageSize, reporttype, c);

	}

	/**
	 * 
	 * @time   2018年8月24日 下午4:14:43
	 * @author dyc
	 * @todo   向报告价格表新增信息
	 * @return_type   void
	 */
	public ReportPrice add(ReportPrice reportprice) {
		return reportprice;
	}

	/**
	 * 
	 * @time   2018年8月27日 上午9:42:12
	 * @author dyc
	 * @todo   根据id删除报告价格表信息
	 * @return_type   ReportPrice
	 */
	public boolean updateDelFlagById(Integer id) {
		return ReportPrice.dao.updateDelFlagById(id);
	}

	/**
	 * 
	 * @time   2018年9月3日 下午5:19:25
	 * @author dyc
	 * @todo   报告类型下拉框
	 * @return_type   List<CreditReportType>
	 */
	public List<ReportTypeModel> getReportType(String type) {
		List<ReportTypeModel> list = ReportTypeModel.dao.getReportType();
		return list;
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
		sql.append("  LEFT JOIN sys_dict_detail ot on ot.detail_id=t.order_type  ");
		sql.append("   LEFT JOIN sys_dict_detail os on os.detail_id=t.order_speed");
		sql.append("   LEFT JOIN credit_report_type rt on rt.id=t.report_type");
		sql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=t.country_type");
		sql.append("   LEFT JOIN sys_user u on u.userid=t.create_by");
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
						"select os.detail_name as orderSpeed,rt.`name` as reportType,ot.detail_name as orderType,c.detail_name as countryName,u.realname,detail.detail_name as usabledName, t.*",
						sql.toString(), params.toArray());
	}
}
