package com.hailian.modules.credit.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.order.model.ReportPrice;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @version 2018年8月23日 下午5:20:42
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
		StringBuffer selectSql = new StringBuffer(" select * ");
		StringBuffer fromSql = new StringBuffer(" from credit_report_price where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(reporttype)) {
			fromSql.append("and report_type=?");
			params.add(reporttype);
		}

		Page<ReportPrice> pricePage = ReportPrice.dao.paginate(new Paginator(pageNumber, pageSize),
				selectSql.toString(), fromSql.toString(), params.toArray());

		return pricePage;

	}

}
