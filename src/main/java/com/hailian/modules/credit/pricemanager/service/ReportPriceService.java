package com.hailian.modules.credit.pricemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditReportType;
import com.hailian.modules.credit.order.model.TbOrder;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.StrUtils;
import com.hailian.util.extend.UuidUtils;
import com.jfinal.plugin.activerecord.Db;
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
//		reportprice.set("price", 200);
		reportprice.save();
		return reportprice;
	}

	/**
	 * 
	 * @time   2018年8月27日 上午9:42:12
	 * @author dyc
	 * @todo   根据id删除报告价格表信息
	 * @return_type   ReportPrice
	 */
	public boolean updateDelFlagById(String id) {
		return ReportPrice.dao.updateDelFlagById(id);
	}
/**
 * 
 * @time   2018年9月3日 下午5:19:25
 * @author dyc
 * @todo   报告类型下拉框
 * @return_type   List<CreditReportType>
 */
	public List<CreditReportType> getReportType(String type) {
		StringBuffer sql = new StringBuffer("from credit_report_type r where r.del_flag=0");
		if (StringUtils.isNotEmpty(type)) {
			sql.append(" AND r.report_type = '").append(type).append("'");
		}
		List<CreditReportType> list = CreditReportType.dao.getReportType();

		return list;
	}

	/**
	 * 
	 * @time   2018年9月3日 下午2:22:14
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   Page<ReportPrice>
	 */
	public Page<ReportPrice> getPage(Paginator paginator, String speed, String order, String country, String usable,
			BaseProjectController c) {
		StringBuffer sql = new StringBuffer(" from  credit_report_price t "
				+ " LEFT JOIN sys_dict_detail ot on ot.detail_id=t.order_type "
				+ " LEFT JOIN sys_dict_detail os on os.detail_id=t.order_speed"
				+ " LEFT JOIN credit_report_type rt on rt.id=t.report_type"
				+ " LEFT JOIN credit_country c on c.id=t.country_type"
				+ " LEFT JOIN sys_user u on u.userid=t.create_by"
				+ " where t.del_flag=0 and ot.del_flag=0 and os.del_flag=0 and rt.del_flag=0 and c.del_flag=0");

		if (StringUtils.isNotEmpty(speed)) {
			sql.append(" AND t.order_speed = '").append(speed).append("'");
			if (StringUtils.isNotEmpty(order)) {
				sql.append(" AND t.order_type = '").append(order).append("'");
				if (StringUtils.isNotEmpty(country)) {
					sql.append(" AND t.country_type = '").append(country).append("'");
					if (StringUtils.isNotEmpty(usable)) {
						sql.append(" AND t.usabled = '").append(usable).append("'");

					}

				}
			}
		}

		return ReportPrice.dao
				.paginate(
						paginator,
						"select os.detail_name as orderSpeed,rt.`name` as reportType,ot.detail_name as orderType,c.`name` as countryName,u.realname, t.*",
						sql.toString());
	}
}
