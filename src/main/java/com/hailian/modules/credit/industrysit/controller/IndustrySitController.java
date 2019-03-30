package com.hailian.modules.credit.industrysit.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyIndustrySituationDict;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyIndustrySituationTitleDict;

import com.hailian.modules.credit.industrysituation.service.IndustrySitService;
import com.hailian.modules.credit.industrysituation.service.IndustrySitTitleService;
import com.jfinal.plugin.activerecord.Page;
@ControllerBind(controllerKey = "/credit/industrysit")
public class IndustrySitController extends BaseProjectController {
	private static final String path = "/pages/credit/industrysit/industrysit__";
	public void index() {
		list();
	}
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:32:30  
	* @TODO
	 */
	public void list() {
		CreditCompanyIndustrySituationDict attr = getModelByAttr(CreditCompanyIndustrySituationDict.class);
		String keyword = getPara("keyword");
		String orderBy = getBaseForm().getOrderBy();
		Page<CreditCompanyIndustrySituationDict> page = IndustrySitService.service.getPage(getPaginator(),orderBy,attr,this);
		List<CreditCompanyIndustrySituationTitleDict> allIndustryTitle = IndustrySitTitleService.service.getAllIndustrySit();	
		setAttr("allIndustryTitle",allIndustryTitle);
		setAttr("page",page);
		render(path+"list.html");
	}
	/**
	 * 查看
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:16  
	* @TODO
	 */
	public void view() {
		CreditCompanyIndustrySituationDict item=IndustrySitService.service.getIndustrySit(getParaToInt());
		setAttr("item", item);
		render(path + "view.html");
	}
	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:28  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		CreditCompanyIndustrySituationDict attr = getModel(CreditCompanyIndustrySituationDict.class);
		setAttr("model", attr);
		render(path + "add.html");
	}
	/**
	 * 编辑跳转
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:44  
	* @TODO
	 */
	public void edit() {
		Integer paraToInt = getParaToInt();
		CreditCompanyIndustrySituationDict model = CreditCompanyIndustrySituationDict.dao.findById(paraToInt);
		List<CreditCompanyIndustrySituationTitleDict> allIndustryTitle = IndustrySitTitleService.service.getAllIndustrySit();	
		setAttr("allIndustryTitle",allIndustryTitle);
		setAttr("model", model);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除
	* @author doushuihai  
	* @date 2018年9月4日下午2:46:04  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		IndustrySitService.service.delete(id,userid);//记录上传信息
		list();
	}
	/**
	 * 保存
	* @author doushuihai  
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		CreditCompanyIndustrySituationDict model = getModel(CreditCompanyIndustrySituationDict.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.set("update_by", userid);
			model.set("update_date", now);
			model.save();
		}
		renderMessage("保存成功");
	}
	


	

	
}
