package com.hailian.modules.credit.order.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.order.model.ReportPrice;
import com.hailian.modules.credit.order.service.ReportPriceService;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @version 2018年8月23日 下午5:29:22
* @todo
*/
@Api(tag = "报告订单", description = "报告订单下拉框")
@ControllerBind(controllerKey = "/credit/price")
public class ReportPriceController extends BaseProjectController {

	/**
	 * 
	 * @time   2018年8月23日 下午5:39:02
	 * @author dyc
	 * @todo   根据id查询报告价格信息
	 * @return_type   void
	 */

	public void list() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 8);
		Page<ReportPrice> page = ReportPriceService.service
				.pagePrice(pageNumber, pageSize, getPara("reporttype"), this);
		setAttr("pager", page);
		keepPara();
		render("/pages/system/dict/dict_list.html");
	}

	@Params(value = { @Param(name = "pageNumber", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String") })
	@ApiOperation(url = "/credit/price/listjson", httpMethod = "get", description = "获取报告价格列表")
	public void listjson() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 5);
		Page<ReportPrice> page = ReportPriceService.service
				.pagePrice(pageNumber, pageSize, getPara("reporttype"), this);
		renderJson(page);

	}

	/**
	 * 
	 * @time   2018年8月23日 下午7:35:55
	 * @author dyc
	 * @todo   单条报告价格查询
	 * @return_type   void
	 */
	public void getOne() {
		ReportPrice model = ReportPrice.dao.findById(getPara("id"), this);
		setAttr("model", model);
		render("/pages/system/dict/dict_list.html");

	}
}
