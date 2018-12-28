package com.hailian.modules.credit.common.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditReportModuleParentNodesDict;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleColumn;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        if(StrUtils.isEmpty(getPara("id"))) {
        	renderMessage("缺少ID!");return;
        }
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("id",getPara("id"));
		model.set("update_by", userid);
		model.set("update_date", now);
		model.set("del_flag", 1);
		model.update();
		String findSonsSql = " select * from credit_report_module_conf where del_flag=0 and parent_temp=? ";
        List<CreditReportModuleConf> sonConfModels = CreditReportModuleConf.dao.find(findSonsSql,Arrays.asList(new String[] {getPara("id")+""}).toArray());
        for (CreditReportModuleConf son : sonConfModels) {
			son.set("update_by", userid).set("update_date", now).set("del_flag", 1);
			son.update();
		}
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
    	try {
    		//int result = -1;
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
        			model2.update();//操作信息的更新
                   CreditReportModuleParentNodesDict nodeModel = CreditReportModuleParentNodesDict.dao.findById(id);
                   CreditReportModuleConf confRootModel = new CreditReportModuleConf();
                   Map<String,Object> entryMap = nodeModel.getAttrs();
                   for (String key : entryMap.keySet()) {
                	   if("id".equals(key)) {continue;}
                	   confRootModel.set(key, entryMap.get(key));
                   }
                   confRootModel.set("table_id",  nodeModel.get("id"));
                   confRootModel.set("is_detail", 0);
                   confRootModel.set("update_by",userid).set("update_date", now).set("create_by", userid).set("create_date", now).set("report_type", reportType);
                   //插入父节点
                   confRootModel.save();
                   //获取父节点ID
                   String confRootId =  confRootModel.get("id")+"";
                   //插入子节点 
                   String findSonsSql = " select * from credit_report_module_column where del_flag=0 and table_id=? ";
                   List<CreditReportModuleColumn> columnModels = CreditReportModuleColumn.dao.find(findSonsSql,Arrays.asList(new String[] {nodeModel.get("id")+""}).toArray());
                   for (CreditReportModuleColumn columnModel : columnModels) {
                	   CreditReportModuleConf confsonModel = new CreditReportModuleConf();
                       Map<String,Object> columnEntryMap = columnModel.getAttrs();
                       for (String key : columnEntryMap.keySet()) {
                    	   if("id".equals(key)||"table_name".equals(key)) {continue;}
                    	   confsonModel.set(key, columnEntryMap.get(key));
                       }
                       confsonModel.set("is_detail", 0).set("parent_temp", confRootId)
                	   .set("update_by",userid).set("update_date", now).set("create_by", userid).set("create_date", now).set("report_type", reportType);
                       confsonModel.save();
                   }
                   
                }
            }
            
        	 
    		 
		} catch (Exception e) {
			e.printStackTrace();
			renderMessage( " 保存失败!") ;
		}
    
        renderMessage( "保存成功!" ) ;
    }
	
    
    
    
    
    
    
    
}
