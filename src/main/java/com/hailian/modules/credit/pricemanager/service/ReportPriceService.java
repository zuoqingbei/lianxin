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

	//	/**
	//	 * 
	//	 * @time   2018年8月23日 下午5:27:06
	//	 * @author dyc
	//	 * @todo   根据id查询报告价格信息
	//	 * @return_type   ReportPrice
	//	 */
	//
	//	public ReportPrice selectById(String id, BaseProjectController c) {
	//		return ReportPrice.dao.findById(id, c);
	//	}

	/**
	 * 
	 * @time   2018年8月23日 下午7:21:15
	 * @author dyc
	 * @todo   分页查询报告价格信息
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
	 * @time   2018年9月7日 上午9:22:46
	 * @author dyc
	 * @todo  根据id查询单条报告价格信息
	 * @return_type   ReportPrice
	 */
	public ReportPrice getId(int id, BaseProjectController c) {
		ReportPrice reportPrice = ReportPrice.dao.getId(id, c);
		return reportPrice;

	}
}
