package com.hailian.modules.credit.mail.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.mail.service.MailService;
import com.jfinal.plugin.activerecord.Page;
/**
 * 邮件列表管理
* @author doushuihai  
* @date 2018年9月18日下午1:57:21  
* @TODO
 */
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
	 * 设置邮件禁用
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:16  
	* @TODO
	 */
	public void enabled() {
		Integer pid = getParaToInt();
		// 日志添加
		Integer userid = getSessionUser().getUserid();
		if (pid != null && pid > 0) { // 更新
			MailService.service.toEnabled(pid, userid);
		}
		list();
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
		MailModel model = MailModel.dao.findById(paraToInt);
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
		int result = MailService.service.delete(id,userid);//记录上传信息
		if(result != -1){
			setAttr("result", result);
			list();
		}else{
			renderText("操作失败");
		}
		
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
		MailModel model = getModel(MailModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_by", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("mail_id");
			model.put("create_by", userid);
			model.put("create_date", now);
			model.save();
		}
		renderMessage("保存成功");
	}
}