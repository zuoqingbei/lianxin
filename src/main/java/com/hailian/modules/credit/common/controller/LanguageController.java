package com.hailian.modules.credit.common.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.LanguageModel;
import com.jfinal.plugin.activerecord.Page;
/**
 * @todo   语言配置
 * @time   2018年9月4日 下午5:30
 * @author lzg
 * @return_type   void
 */
@Api( tag = "配置中心", description = "语言配置" )
@ControllerBind(controllerKey = "/credit/language")
public class LanguageController extends BaseProjectController {
	private static final String PATH = "/pages/credit/language/language_";
	//存储关键词字段名
	public static List<Object> columnNames = new ArrayList<>();
	//搜索类型
	public static String searchById = "-1";
	public static String fuzzySearch = "-2";
	public static String refineSearch = "-3";
	static{
		columnNames.add("country_id");
		columnNames.add("report_id");
		columnNames.add("usabled");
	}
		
	/**
	 * @todo   语言配置列表集
	 * @time   2018年9月5日 上午9:10:00
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
		if(!refineSearch.equals(searchType)){
			searchType = fuzzySearch;
		}
		//获取前台关键词
		List<Object> keywords = new LinkedList<>();
		for (Object columnName : columnNames) {
			if(StringUtil.isEmpty((String)getPara((String)columnName))){
				keywords.add("");
			}else{
				keywords.add(getPara((String)columnName));
			}
		}
		
		//分页查询
		Page<LanguageModel> pager = LanguageModel.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType, this);
		setAttr("page", pager);
		keepPara();
		render(PATH+"list.html");
	}
	
	/**
	 * @todo   查看单个语言配置信息
	 * @time   2018年9月5日 上午11:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void view() {
		List<Object> keywords = new ArrayList<>();
		keywords.add(getPara("id"));
		LanguageModel model = LanguageModel.dao.pagerOrder(1, 1, keywords,"",searchById,this).getList().get(0);
		setAttr("model", model);
		keepPara();
		render(PATH+"view.html");
	}
	
	/**
	 * @todo   跳转到增加页面
	 * @time   2018年9月5日 上午11:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void add() {
		render(PATH + "add.html");
	}
	/**
	 * @todo   删除单条语言配置
	 * @time   2018年9月5日 上午12:00:00
	 * @author lzg
	 * @return_type   void
	 */
	public void del() {
		LanguageModel model = getModel(LanguageModel.class);
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
	 * @todo   跳转到修改页面
	 * @time   2018年9月5日 下午2:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void edit() {
		LanguageModel model = LanguageModel.dao.findById(getPara("id"));
		setAttr("model", model);
		render(PATH + "edit.html");
	}
	
	/**
	 * @todo   修改或增加功能
	 * @time   2018年9月4日 下午4:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void save() {
		Integer pid = getParaToInt("id");
		LanguageModel model = getModel(LanguageModel.class);
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

