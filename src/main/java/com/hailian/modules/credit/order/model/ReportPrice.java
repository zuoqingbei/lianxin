package com.hailian.modules.credit.order.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @version 2018年8月23日 下午4:41:58
* @todo
*/
@ModelBind(table = "credit_report_price")
public class ReportPrice extends BaseProjectModel<ReportPrice> {
	private static final long serialVersionUID = 1L;
	public static final ReportPrice dao = new ReportPrice();

	/**
	 * 
	 * @time   2018年8月23日 下午7:13:06
	 * @author dyc
	 * @todo   分页查询报告价格
	 * @return_type   Page<ReportPrice>
	 */
	public Page<ReportPrice> pagePrice(int pageNumber, int pageSize, String reporttype, BaseProjectController c) {
		return ReportPrice.dao.pagePrice(pageNumber, pageSize, reporttype, c);

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
	 * @time   2018年8月24日 上午11:37:46
	 * @author dyc
	 * @todo   向报告价格表新增信息
	 * @return_type   ReportPrice
	 */
	public ReportPrice addreport() {
		return ReportPrice.dao.addreport();

	}

}
