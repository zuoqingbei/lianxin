package com.hailian.modules.credit.common.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.common.service.ReportTypeService;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleColumn;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @todo 报告类型
 * @time   2018年9月3日 上午9:10:00
 * @author lzg
 */



@Api( tag = "报告模板字段维护", description = "操作报告模板字段" )
@ControllerBind(controllerKey = "/credit/reportColumn")
public class ReportColumnController extends BaseProjectController {
	private static final String path = "/pages/credit/reportColumn/column_";
	
	/**
	 * @todo   模板字段
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//模糊搜索或者精确搜索
		String keyword = getPara("keyWord");
		//分页查询
        List<Object> params = new ArrayList<>();
        params.add(getPara("pid"));
        params.add(getPara("reportType"));
		Page<CreditReportModuleConf> pager = CreditReportModuleConf.dao.findSon(pageNumber, pageSize, keyword, orderBy, params);
        setAttr("page", pager);
		keepPara();
		render(path+"list.html");
	}
	
	/**
	 * @todo   模板字段
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   json
	 */
	@Params(value = { 
			@Param(name = "pageNo", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "reportName", description = "报告名称(模糊匹配)", required = false, dataType = "String")
			})
	@ApiOperation(url = "/credit/reportColumn/listJson",httpMethod="get",
	description = "获取报告类型列表",response=Page.class)
	public void listJson() {
        int pageNumber = getParaToInt("pageNo", 1);
        int pageSize = getParaToInt("pageSize", 10);
        //从表单获取排序语句
        String orderBy = getBaseForm().getOrderBy();
        //模糊搜索或者精确搜索
        String keyword = getPara("keyWord");
        //分页查询
        List<Object> params = new ArrayList<>();
        params.add(getPara("pid"));
        params.add(getPara("reportType"));
        Page<CreditReportModuleConf> pager = CreditReportModuleConf.dao.findSon(pageNumber, pageSize, keyword, orderBy, params);
        renderJson(pager);
	}
	
	/**
	 * @todo   查看单个字段
	 * @time   2018年9月3日 下午1:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void view() {
        CreditReportModuleConf model = CreditReportModuleConf.dao.findById(getParaToInt("id"), this);
		setAttr("model", model);
		render(path+"view.html");
	}

	public void add() {
        int pageNumber = getParaToInt("pageNo", 1);
        int pageSize = getParaToInt("pageSize", 10);
        //从表单获取排序语句
        String orderBy = getBaseForm().getOrderBy();
        //分页查询
        List<Object> params = new ArrayList<>();
        params.add(1);
        Page<CreditReportModuleColumn> pager = CreditReportModuleColumn.dao.page(pageNumber, pageSize, "", orderBy, params);
        setAttr("page", pager);
        keepPara();
		render(path + "add.html");
	}
	
	/**
	 * @todo   删除单个字段
	 * @time   2018年9月4日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	public void del() {
        CreditReportModuleConf model = getModel(CreditReportModuleConf.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("id",getPara("id"));
		model.set("update_by", userid);
		model.set("update_date", now);
		model.set("del_flag", 1);
		model.update();
		list();
	}
	
	/**
	 * @todo   跳转到修改报告类型页面
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	public void edit() {
        CreditReportModuleConf model = CreditReportModuleConf.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		render(path + "edit.html");
	}
	
	/**
	 * @todo   修改或增加功能
	 * @time   2018年9月4日 下午4:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void save() {
		Integer pid = getParaToInt("id");
        CreditReportModuleConf model = getModel(CreditReportModuleConf.class);
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

    /**
     * 选择字段
     */
    public void addColumn(){
        String pid = getPara("pid");
        String[] columnIds = getParaValues("columnId");
        if(columnIds!=null&&columnIds.length>0){
            for(String id:columnIds){
                CreditReportModuleColumn column = CreditReportModuleColumn.dao.findById(id);
                CreditReportModuleConf moduleConf = CreditReportModuleConf.dao.findById(pid);
                String reportType = moduleConf.get("report_type");
                moduleConf.clear();
                moduleConf.set("parent_temp", pid);
                moduleConf.set("node_level","2");
                moduleConf.set("report_type",reportType);
                moduleConf.set("temp_name",column.get("temp_name"));
                moduleConf.set("column_name",column.get("column_name"));
                moduleConf.set("get_source",column.get("get_source"));
                moduleConf.set("remove_source",column.get("remove_source"));
                moduleConf.set("alter_source",column.get("alter_source"));
                moduleConf.set("word_key",column.get("word_key"));
                moduleConf.set("field_type",column.get("field_type"));
                moduleConf.set("place_hold",column.get("place_hold"));
                moduleConf.set("suffix",column.get("suffix"));
                moduleConf.set("error_msg",column.get("error_msg"));
                moduleConf.set("reg_validation",column.get("reg_validation"));
                moduleConf.set("remark",column.get("remark"));
                moduleConf.save();

            }
        }
        renderMessage("保存成功");
    }
	
}
