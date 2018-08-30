package com.hailian.modules.credit.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.order.model.ReportPrice;
import com.hailian.modules.credit.order.model.TbOrder;
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
		reportprice.set("id", UuidUtils.getUUID());
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

}
