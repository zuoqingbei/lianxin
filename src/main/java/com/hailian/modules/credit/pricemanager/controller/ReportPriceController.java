package com.hailian.modules.credit.pricemanager.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditReportType;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.hailian.system.dict.SysDictDetail;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年8月23日 下午5:29:22
* @todo
*/
@Api(tag = "报告价格", description = "报告价格下拉框")
@ControllerBind(controllerKey = "/credit/pricemanager")
public class ReportPriceController extends BaseProjectController {
	private static final String path = "/pages/credit/pricemanager/price_";
	
	public void index() {
		list();
	}
	/**
	 * 
	 * @time   2018年8月23日 下午5:39:02
	 * @author dyc
	 * @todo   分页查询报告价格信息
	 * @return_type   void
	 */
	@Params(value = { @Param(name = "pageNumber", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String") })
		@ApiOperation(url = "/credit/pricemanager/list", httpMethod = "get", description = "获取报告价格列表")
	public void list() {
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 5);
		Page<ReportPrice> page = ReportPriceService.service
				.pagePrice(pageNumber, pageSize, getPara("reporttype"), this);
		ReportPrice attr = getModelByAttr(ReportPrice.class);
		String type = attr.getStr("reporttype");
		Page<ReportPrice> pager = ReportPriceService.service
				.getPage(getPaginator(), type, this);
		List<CreditReportType> reportType=ReportPriceService.service.getReportType();
		setAttr("pager",pager);
		setAttr("reporttype",reportType);
		render(path+"list.html");
	}

//	@Params(value = { @Param(name = "pageNumber", description = "页码", required = false, dataType = "String"),
//		@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
//		@Param(name = "reporttype", description = "报告类型", required = false, dataType = "String") })
//	@ApiOperation(url = "/credit/pricemanager/listjson", httpMethod = "get", description = "获取报告价格列表")
//	public void listjson() {
//	int pageNumber = getParaToInt("pageNumber", 1);
//	int pageSize = getParaToInt("pageSize", 5);
//	Page<ReportPrice> page = ReportPriceService.service
//		.pagePrice(pageNumber, pageSize, getPara("reporttype"), this);
//	renderJson(page);
//
//	}

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

	/**
	 * 
	 * @time   2018年8月24日 下午4:24:54
	 * @author dyc
	 * @todo   向报告价格表里新增信息
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/pricemanager/addReport", httpMethod = "get", description = "报告价格表新增信息")
	public void addReport() {
		ReportPrice model = getModelByAttr(ReportPrice.class);
		ReportPriceService.service.add(model);
		setAttr("model", model);
		list();
	}
	/**
	 * 
	 * @time   2018年8月27日 上午10:23:53
	 * @author dyc
	 * @todo   根据id删除报告价格表信息
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/pricemanager/deleteReport", httpMethod = "get", description = "删除报告价格表里的信息")
	@Params(value = {
			@Param(name = "id", description = "id", required = false, dataType = "String"),
			 })
	public void deleteReport(){
		String id=getPara("id");
		if(ReportPriceService.service.updateDelFlagById(id)){
			//success
			//redirect("/credit/price/list");
			renderText("success");
		}else{
			//redirect("/credit/price/list");
			renderText("failure");
		};	
	}
}
