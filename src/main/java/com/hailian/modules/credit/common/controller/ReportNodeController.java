package com.hailian.modules.credit.common.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditReportModuleParentNodesDict;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @todo 报告类型
 * @time   2018年9月3日 上午9:10:00
 * @author lzg
 */



@Api( tag = "报告模板父节点维护", description = "操作报告父节点字段" )
@ControllerBind(controllerKey = "/credit/reportNodes")
public class ReportNodeController extends BaseProjectController {
	private static final String path = "/pages/credit/reportNodes/reportNodes_";
	
	/**
	 * @todo   模板字段
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	public void list() {
		String reportType = getPara("reportType");
		if(StrUtils.isEmpty(reportType)) {renderMessage("缺少必要参数reportType!"); return;}
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//模糊搜索或者精确搜索
		String keyword = getPara("keyWord");
		//分页查询
        List<Object> params = new ArrayList<>();
        //params.add(getPara("pid"));
        params.add(reportType); 
		Page<CreditReportModuleConf> pager = CreditReportModuleConf.dao.page(pageNumber, pageSize, keyword, orderBy, params);
        setAttr("page", pager);
        setAttr("reportType", reportType);
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
        Page<CreditReportModuleParentNodesDict> pager = CreditReportModuleParentNodesDict.dao.page(pageNumber, pageSize, keyword, orderBy, params);
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
        //params.add(1);
        Page<CreditReportModuleParentNodesDict> pager = CreditReportModuleParentNodesDict.dao.page(pageNumber, pageSize, "", orderBy, params);
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
        CreditReportModuleConf model = getModel(CreditReportModuleConf.class);
        if(StrUtils.isEmpty(model.get("id")+"")) {
        	renderMessage("实体id不能为空!");
        }
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
	    model.update();
	    renderMessage("修改成功");
	}

    /**
     * 选择字段
     */
    public void addNodes(){
    	int result = -1;
        String reportType = getPara("reportType");
        if(StrUtils.isEmpty(reportType)) {renderMessage("缺少必要参数reportType!");return;}
        String[] columnIds = getParaValues("nodesId");
        if(columnIds!=null&&columnIds.length>0){  
        	CreditReportModuleParentNodesDict model = new CreditReportModuleParentNodesDict();
        	Integer userid = getSessionUser().getUserid();
    		String now = getNow();
    		model.set("update_by",userid);
    		model.set("update_date", now);
    		model.set("create_by", userid);
			model.set("create_date", now);
			model.set("report_type", reportType);
            for(String id:columnIds){
            	CreditReportModuleParentNodesDict model2 = new CreditReportModuleParentNodesDict();
            	model2._setAttrs(model).set("id", id);
    			model2.update();
               result =  Db.update("INSERT INTO  `credit_report_module_conf` (" + 
              		"	`parent_temp`," + 
              		"	`small_module_type`," + 
              		"	`node_level`," + 
              		"	`report_type`," + 
              		"	`float_parent`," + 
              		"	`get_source`," + 
              		"	`remove_source`," + 
              		"	`alter_source`," + 
              		"	`remark`," + 
              		"	`word_key`," + 
              		"	`word_table_type`," + 
              		"	`temp_name`," + 
              		"	`column_name`," + 
              		"	`sort`," + 
              		"	`field_type`," + 
              		"	`place_hold`," + 
              		"	`suffix`," + 
              		"	`error_msg`," + 
              		"	`reg_validation`," + 
              		"	`anchor_id`," + 
              		"	`create_by`," + 
              		"	`update_by`," + 
              		"	`update_date`," + 
              		"	`create_date`," + 
              		"	`del_flag`," + 
              		"	`is_merger_next`" + 

              		") " + 
              		"	SELECT" + 
              		"		 " + 
              		"			`parent_temp`," + 
              		"			`small_module_type`," + 
              		"			`node_level`," + 
              		"			`report_type`," + 
              		"			`float_parent`," + 
              		"			`get_source`," + 
              		"			`remove_source`," + 
              		"			`alter_source`," + 
              		"			`remark`," + 
              		"			`word_key`," + 
              		"			`word_table_type`," + 
              		"			`temp_name`," + 
              		"			`column_name`," + 
              		"			`sort`," + 
              		"			`field_type`," + 
              		"			`place_hold`," + 
              		"			`suffix`," + 
              		"			`error_msg`," + 
              		"			`reg_validation`," + 
              		"			`anchor_id`," + 
              		"			`create_by`," + 
              		"			`update_by`," + 
              		"			`update_date`," + 
              		"			`create_date`," + 
              		"			`del_flag`," + 
              		"			`is_merger_next`" + 
              		" " + 
              		"	FROM" + 
              		"		credit_report_module_parent_nodes_dict" + 
              		"	WHERE" + 
              		"		id= " + 
              		id);
             

            }
        }
        renderMessage(result>0?"保存成功":"保存失败") ;
    }
	
}
