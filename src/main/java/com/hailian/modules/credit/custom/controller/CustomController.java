package com.hailian.modules.credit.custom.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.jfinal.plugin.activerecord.Page;
@ControllerBind(controllerKey = "/credit/custom")
public class CustomController extends BaseProjectController {
	private static final String path = "/pages/credit/custom/custom_";
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
		CustomInfoModel attr = getModelByAttr(CustomInfoModel.class);
		String keyword = getPara("keyword");
		String orderBy = getBaseForm().getOrderBy();
		Page<CustomInfoModel> page = CustomService.service.getPage(getPaginator(),orderBy,keyword,this);
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
		CustomInfoModel item = CustomInfoModel.dao.findById(getParaToInt());
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
		CustomInfoModel attr = getModel(CustomInfoModel.class);
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
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
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
		CustomService.service.delete(id,userid);//记录上传信息
		list();
	}
	/**
	 * 保存
	* @author doushuihai  
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() {
		Integer pid = getParaToInt("id");
		// 日志添加
		CustomInfoModel model = getModel(CustomInfoModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("detail_id");
			model.put("create_by", userid);
			model.put("create_date", now);
			model.save();
		}
		renderMessage("保存成功");
	}
}
