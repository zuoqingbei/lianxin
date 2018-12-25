package com.hailian.modules.credit.common.controller;

import java.sql.SQLException;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.common.service.ReportTypeService;
import com.hailian.modules.credit.uploadfile.controller.FileUpLoadController;
import com.hailian.util.Config;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

/**
 * @todo 报告类型
 * @time   2018年9月3日 上午9:10:00
 * @author lzg
 */



@Api( tag = "报告类型", description = "操作报告类型" )
@ControllerBind(controllerKey = "/credit/reportType")
public class ReportTypeController extends BaseProjectController {
	private static final String path = "/pages/credit/reportType/reportType_";
	//文件下载使用
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("searver_port");//服务器端口
	
	/**
	 * @todo   报告类型列表集
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
		String searchType = getPara("searchType");
		if(!"1".equals(searchType)){
				searchType ="0";
		}
		//分页查询
		Page<ReportTypeModel> pager = ReportTypeService.service.pagerOrder(pageNumber, pageSize, getPara("keyWord"), orderBy, searchType, this);
		setAttr("page", pager);
		keepPara();
		render(path+"list.html");
	}
	
	/**
	 * @todo   报告类型列表
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   json
	 */
	@Params(value = { 
			@Param(name = "pageNo", description = "页码", required = false, dataType = "String"),
			@Param(name = "pageSize", description = "每页条数", required = false, dataType = "String"),
			@Param(name = "reportName", description = "报告名称(模糊匹配)", required = false, dataType = "String")
			})
	@ApiOperation(url = "/credit/reportType/listJson",httpMethod="get", 
	description = "获取报告类型列表",response=Page.class)
	public void listJson() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//模糊搜索或者精确搜索
		String searchType = getPara("searchType");
		if(!"1".equals(searchType)){
			searchType ="0";
		}
		//分页查询
		Page<ReportTypeModel> pager = ReportTypeService.service.pagerOrder(pageNumber, pageSize, getPara("keyWord"), orderBy, searchType, this);
		renderJson(pager);
	}
	
	/**
	 * @todo   查看单个报告类型
	 * @time   2018年9月3日 下午1:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void view() {
		ReportTypeModel model = ReportTypeModel.dao.getReportType(getPara("id"), this);
		setAttr("model", model);
		render(path+"view.html");
	}
	
	public void add() {
		render(path + "add.html");
	}
	
	/**
	 * @todo   删除单条报告类型
	 * @time   2018年9月4日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
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
	
	/**
	 * @todo   跳转到修改报告类型页面
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   void
	 */
	public void edit() {
		ReportTypeModel model = ReportTypeModel.dao.findById(getParaToInt("id"));
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
		String name=  getPara("name");
		String financialType = getPara("financialType");//财务类型
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		FileUpLoadController fileconController=new FileUpLoadController();
		
       UploadFile uploadFile=getFile("file_url");
       String url="";
       if (uploadFile!=null) {
   		url= fileconController.upload(pid,getFile("file_url"), name,userid);	
	    }
       ReportTypeModel model = getModel(ReportTypeModel.class);
       if (url!=null&&!"".equals(url)) {
    	   model.set("tpl_path", url);
	   }
       
       if(!StrUtils.isEmpty(financialType))
    	 
       Db.tx(new IAtom() {
		public boolean run() throws SQLException {
			//先删除
			String sql = "";
			sql += "update credit_report_module_conf  ";
			sql += "set del_flag=1";
			sql += "set update_by="+userid;
			sql += "set update_date="+now;
			sql += " where table_id=-1 and report_type= "+pid;
	    	   Db.update(sql);
	    	//再添加
			//0-无 1-中文加大数 2-英文加大数 3-只有大数
			 switch (financialType) {
				case "0": break;
				case "1":
					
					break;
				case "2":
				
				break;
				case "3":
				
				break;
				default:
					break;
				}
			return true;
		}
	});
    
       
		if (pid != null && pid > 0) { // 更新
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
	/**
	 * 
	* @Description: 获取下载的路径
	* @date 2018年12月18日 下午5:27:15
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getUrl(){
		 String id=getPara("id");
		ReportTypeModel model=ReportTypeModel.dao.findById(id);
		String url=ip+":"+port+"/"+model.get("tpl_path");
		renderJson(new Record().set("url", url));
	}
	
	/**
	 * 根据报告类型插入根实体
	 * @author lzg
	 * @param reportType
	 * @return
	 */
	public Integer insertRootEntry(String reportType) {
		                               
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
