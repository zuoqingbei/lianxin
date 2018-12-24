package com.hailian.modules.credit.pricemanager.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年8月23日 下午5:29:22
* @todo
*/
@Api(tag = "报告价格", description = "操作报告价格")
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
	 * @todo   向前台页面展示数据
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/pricemanager/list", httpMethod = "get", description = "获取报告价格列表")
	public void list() {
		ReportPrice attr = getModelByAttr(ReportPrice.class);
		String orderBy = getBaseForm().getOrderBy();
		Page<ReportPrice> pager = ReportPrice.dao.getPage(getPaginator(), attr, orderBy, this);
		List<ReportTypeModel> reportType = ReportPriceService.service.getReportType(null);
		setAttr("page", pager);
		setAttr("reporttype", reportType);
		setAttr("attr", attr);
		//keepPara();
		render(path + "list.html");
	}
	public void getVersion() {
		String version=getPara("version");
		setAttr("version",version);
		renderJson(version);
	}

	/**
	 * 
	 * @time   2018年9月4日 下午2:27:20
	 * @author dyc
	 * @param int id 
	 * @todo   单条查看报告价格信息
	 * @return_type   void
	 */
	public void view() {
		Integer id = getParaToInt();
		ReportPrice model = ReportPriceService.service.getId(id, null);
		setAttr("model", model);
		render(path + "view.html");

	}

	/**
	 * 
	 * @time   2018年8月24日 下午4:24:54
	 * @author dyc
	 * @todo   转到新增页面
	 * @return_type   void
	 */
	public void add() {
		ReportPrice model = getModelByAttr(ReportPrice.class);
		setAttr("model", model);
		setAttr("version", "请选择版本");
		render(path + "add.html");
	}

	/**
	 * 
	 * @time   2018年9月4日 下午1:44:44
	 * @author dyc
	 * @todo   转到修改报告价格信息页面
	 * @return_type   void
	 */
	public void edit() {
		Integer para = getParaToInt();
		ReportPrice model = ReportPrice.dao.findById(para);
		setAttr("model", model);
		render(path + "edit.html");

	}

	/**
	 * 
	 * @time   2018年8月27日 上午10:23:53
	 * @author dyc
	 * @todo   删除单条报告价格信息
	 * @return_type   void
	 */
	public void delete() {
		Integer id = getParaToInt();
		if (ReportPriceService.service.updateDelFlagById(id)) {
			list();
		} else {
			renderText("failure");
		}

	}

	/**
	 * 
	 * @time   2018年9月6日 上午11:06:56
	 * @author dyc
	 * @todo   修改和增加功能
	 * @return_type   void
	 */
	public void save() {
		Integer id = getParaToInt("id");
		ReportPrice model = getModel(ReportPrice.class);
		if("老系统".equals(model.getStr("versions"))) {
			model.set("order_speed", getPara("order_speed",""));
			model.set("report_type", getPara("report_type",""));
		}
		if("207".equals(model.getStr("country_type")) || "208".equals(model.getStr("country_type")) || "209".equals(model.getStr("country_type"))){
			model.set("country_type", "148");
		}
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		if (id != null && id > 0) { // 更新
			model.set("update_by", userid);
			model.set("update_date", now);
			model.update();
			renderMessage("修改成功");
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
			renderMessage("保存成功");
		}
	}
}
