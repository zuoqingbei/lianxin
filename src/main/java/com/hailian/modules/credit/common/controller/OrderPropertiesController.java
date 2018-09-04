package com.hailian.modules.credit.common.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.system.dict.SysDictDetail;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @todo   订单类型与订单速度
 * @time   2018年9月4日 上午11:39:52
 * @author zuoqb
 */


@Api( tag = "订单类型与订单速度", description = "订单类型与订单速度" )
@ControllerBind(controllerKey = "/credit/orderproperties")
public class OrderPropertiesController extends BaseProjectController {
	private static final String path = "/pages/credit/orderproperties/orderproperties_";
	/**
	 * @todo   TODO
	 * @time   2018年9月4日 下午4:08:26
	 * @author zuoqb
	 * @params
	 */
	public void list() {
		Page<SysDictDetail> pager = getPagerList();
		setAttr("page", pager);
		setCommonTitle();
		render(path+"list.html");
	}
	
	/**
	 * 
	 * @todo   报告类型列表集
	 * @time   2018年9月4日 下午4:10:06
	 * @author zuoqb
	 * @params
	 */
	@ApiOperation(url = "/credit/orderproperties/listJson",httpMethod="get", 
	description = "获取订单类型与订单速度",response=Page.class)
	public void listJson() {
		Page<SysDictDetail> pager = getPagerList();
		renderJson(pager);
	}
	/**
	 * @todo   获取数据
	 * @time   2018年9月4日 下午4:11:56
	 * @author zuoqb
	 * @params
	 */
	protected Page<SysDictDetail> getPagerList() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		String dictType=getPara("dictType");
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//分页查询
		Page<SysDictDetail> pager = SysDictDetail.dao.pagerSysDictDetail(pageNumber, pageSize, getPara("keyWord"), orderBy,dictType, this);
		setCommonTitle();
		return pager;
	}
	public void view() {
		SysDictDetail model = SysDictDetail.dao.findById(getPara("detail_id"));
		setAttr("model", model);
		setCommonTitle();
		render(path+"view.html");
	}
	/**
	 * 
	 * @todo   公用标题
	 * @time   2018年9月4日 下午5:24:49
	 * @author zuoqb
	 * @params
	 */
	protected void setCommonTitle() {
		String dictType=getPara("dictType");
		String title="";
		String menuKey="";
		if("ordertype".equals(dictType)){
			title="订单类型";
			menuKey="page_order_type";
		}else if("orderspeed".equals(dictType)){
			title="速度配置";
			menuKey="page_order_speed";
		}else if("language".equals(dictType)){
			title="语言类型";
			menuKey="page_language_type";
		}else if("country".equals(dictType)){
			title="国家类型";
			menuKey="page_country_type";
		};
		setAttr("title", title);
		setAttr("menuKey", menuKey);
		keepPara();
	}
	/**
	 * 
	 * @todo   TODO
	 * @time   2018年9月4日 下午5:25:04
	 * @author zuoqb
	 * @params
	 */
	public void add() {
		setCommonTitle();
		render(path + "add.html");
	}
	/**
	 * 
	 * @todo   TODO
	 * @time   2018年9月4日 下午5:25:07
	 * @author zuoqb
	 * @params
	 */
	public void del() {
		SysDictDetail model = getModel(SysDictDetail.class);
		model.set("detail_id",getPara("detail_id"));
		model.set("del_flag", 1);
		model.update();
		list();
	}
	/**
	 * 
	 * @todo   TODO
	 * @time   2018年9月4日 下午5:25:10
	 * @author zuoqb
	 * @params
	 */
	public void edit() {
		SysDictDetail model = SysDictDetail.dao.findById(getParaToInt("detail_id"));
		setCommonTitle();
		setAttr("model", model);
		render(path + "edit.html");
	}
	/**
	 * 
	 * @todo   TODO
	 * @time   2018年9月4日 下午5:25:13
	 * @author zuoqb
	 * @params
	 */
	@Params(value = { 
			@Param(name = "detail_id", description = "detail_id", required = true, dataType = "int"),
			@Param(name = "dict_type", description = "类型", required = true, dataType = "String"),
			@Param(name = "detail_name", description = "名称", required = true, dataType = "String"),
			@Param(name = "detail_name_en", description = "英文名称", required = true, dataType = "String"),
			@Param(name = "detail_sort", description = "排序", required = false, dataType = "String"),
			@Param(name = "remarks", description = "备注", required = false, dataType = "String"),
			})
	public void save() {
		Integer pid = getParaToInt("detail_id");
		SysDictDetail model = getModel(SysDictDetail.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("del_flag", 0);
		model.set("detail_code", model.get("detail_code"));
		if (pid != null && pid > 0) { // 更新
			model.update();
			renderMessage("修改成功");
		} else { // 新增
			model.remove("detail_id");
			model.set("create_id", userid);
			model.set("create_time", now);
			model.save();
			renderMessage("保存成功");
		}
		
	}
	
}
