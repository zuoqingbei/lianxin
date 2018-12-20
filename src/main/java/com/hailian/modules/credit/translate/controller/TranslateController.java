package com.hailian.modules.credit.translate.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.jfinal.plugin.activerecord.Page;
/**
 * 翻译校正
* @author doushuihai  
* @date 2018年9月3日下午2:32:56  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/translatelibrary")
public class TranslateController extends BaseProjectController{
	private static final String path = "/pages/credit/translate/translate_";
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
		TranslateModel attr = getModelByAttr(TranslateModel.class);
		String error_phrase = attr.getStr("error_phrase");
		String orderBy = getBaseForm().getOrderBy();
		Page<TranslateModel> page = TranslateService.service.getPage(getPaginator(),orderBy,error_phrase,this);
		setAttr("page",page);
		render(path+"list.html");
	}
	/**
	 * 查看
	* @author doushuihai  
	* @date 2018年9月3日下午2:37:33  
	* @TODO
	 */
	public void view() {
		TranslateModel translatemodel = TranslateService.service.getTranslate(getParaToInt());
		setAttr("item", translatemodel);
		render(path + "view.html");
	}
	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月3日下午2:40:00  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		TranslateModel attr = getModel(TranslateModel.class);
		setAttr("model", attr);
		render(path + "add.html");
	}
	/**
	 * 编辑跳转
	* @author doushuihai  
	* @date 2018年9月3日下午2:45:58  
	* @TODO
	 */
	public void edit() {
		TranslateModel translatemodel = TranslateService.service.getTranslate(getParaToInt());
		setAttr("model", translatemodel);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除
	* @author doushuihai  
	* @date 2018年9月3日下午2:53:44  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		TranslateService.service.delete(id,userid);//记录上传信息
		list();
	}
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		TranslateModel model = getModel(TranslateModel.class);
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
			model.save();
		}
		renderMessage("保存成功");
	}
	public void saveTranslate(){
		String error_phrase_en=getPara("error_phrase_en");//错误的英文
		String correct_phrase_en=getPara("correct_phrase_en");//应该翻译成的正确的英文
		String correct_phrase_ch=getPara("correct_phrase_ch");//应该翻译成的正确的中文
		TranslateModel model = getModel(TranslateModel.class);
		model.set("error_phrase", error_phrase_en);
		model.set("correct_phrase", correct_phrase_en);
		model.set("correct_phrase_ch", correct_phrase_ch);
		boolean flag = model.save();
		if(flag){
			ResultType resultType = new ResultType();
			renderJson(resultType);
		}else{
			ResultType resultType = new ResultType(0,"操作失败");
			renderJson(resultType);
		}
	}
}
