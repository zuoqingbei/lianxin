package com.hailian.modules.credit.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.common.service.ReportTypeService;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo 报告类型
 * @time   2018年9月3日 上午9:10:00
 * @author lzg
 */



@Api( tag = "报告类型", description = "操作报告类型" )
@ControllerBind(controllerKey = "/credit/reportType")
public class ReportTypeController extends BaseProjectController {
	private static final String path = "/pages/credit/reportType/reportType_";
	@Params(value = { 
			@Param(name = "pageNo", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "reportName", description = "报告名称(模糊匹配)", required = false, dataType = "String")
			})
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//分页查询
		Page<ReportTypeModel> pager = ReportTypeService.service.pagerOrder(pageNumber, pageSize, getPara("keyWord"), orderBy, this);
		setAttr("page", pager);
		keepPara();
		render(path+"list.html");
	}
	
	/**
	 * @todo   报告类型列表集
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/reportType/listJson",httpMethod="get", 
	description = "获取报告类型列表",response=Page.class)
	public void listJson() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//分页查询
		Page<ReportTypeModel> pager = ReportTypeService.service.pagerOrder(pageNumber, pageSize, getPara("keyWord"), orderBy, this);
		renderJson(pager);
	}

	@Params(value = { 
			@Param(name = "id", description = "id", required = true, dataType = "int"),
			})
	public void view() {
		ReportTypeModel model = ReportTypeModel.dao.getReportType(getPara("id"), this);
		setAttr("model", model);
		render(path+"view.html");
	}
	
	public void add() {
		render(path + "add.html");
	}
	
	@Params(value = { 
			@Param(name = "id", description = "id", required = true, dataType = "int"),
			})
	public void del() {
		ReportTypeModel model = getModel(ReportTypeModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("id",getPara("id"));
		model.set("update_by", userid);
		model.set("update_date", now);
		model.set("del_flag", 1);
		model.update();
		
		list();
	}
	
	@Params(value = { 
			@Param(name = "id", description = "id", required = true, dataType = "int"),
			})
	public void edit() {
		ReportTypeModel model = ReportTypeModel.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		render(path + "edit.html");
	}
	
	@Params(value = { 
			@Param(name = "id", description = "id", required = true, dataType = "int"),
			@Param(name = "name", description = "报告名称", required = true, dataType = "String"),
			@Param(name = "name_en", description = "报告英文名称", required = true, dataType = "String"),
			@Param(name = "name_trad", description = "报告繁体名称", required = true, dataType = "String"),
			@Param(name = "tpl_path", description = "报告路径", required = true, dataType = "String"),
			@Param(name = "remarks", description = "备注", required = true, dataType = "String"),
			})
	public void save() {
		Integer pid = getParaToInt("id");
		ReportTypeModel model = getModel(ReportTypeModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		if (pid != null && pid > 0) { // 更新
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
