package com.hailian.modules.credit.mail.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.mail.service.MailService;
import com.jfinal.plugin.activerecord.Page;
@ControllerBind(controllerKey = "/credit/mail")
public class MailController extends BaseProjectController {
	private static final String path = "/pages/credit/mail/mail_";
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
		MailModel attr = getModelByAttr(MailModel.class);
		String keyword = getPara("keyword");
		String orderBy = getBaseForm().getOrderBy();
		Page<MailModel> page = MailService.service.getPage(getPaginator(),orderBy,keyword,this);
		System.out.println(page.getList());
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
//		MailModel item = MailModel.dao.findById(getParaToInt());
		MailModel item=MailModel.dao.getMailById(getParaToInt());
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
		MailModel attr = getModel(MailModel.class);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("model", attr);
		setAttr("company", company);
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
		MailModel model = MailModel.dao.findById(paraToInt);
		setAttr("model", model);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("company", company);
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
		MailService.service.delete(id,userid);//记录上传信息
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
		MailModel model = getModel(MailModel.class);
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
