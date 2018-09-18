package com.hailian.modules.credit.mail.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.mail.model.MailLogModel;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.mail.service.MailLogService;
import com.jfinal.plugin.activerecord.Page;
/**
 * 邮件日志管理
* @author doushuihai  
* @date 2018年9月18日下午1:57:21  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/maillog")
public class MailLogController extends BaseProjectController {
	private static final String path = "/pages/credit/maillog/maillog_";
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
		String module_id = getPara("module_id");//检索条件——模板标识
		String mail_log_id = getPara("mail_log_id");//检索条件——ID
		String send_result = getPara("attr.send_result");//检索条件——模板标识
		String orderBy = getBaseForm().getOrderBy();
		Page<MailLogModel> page = MailLogService.service.getPage(getPaginator(),orderBy,module_id,mail_log_id,send_result,this);
		System.out.println(page.getList());
		setAttr("page",page);
		render(path+"list.html");
	}
	public void view() {
		String mail_log_id = getPara("mail_log_id");//检索条件——ID
		MailModel mailLogById = MailLogService.service.getMailLogById(mail_log_id);
		setAttr("item", mailLogById);
		render(path + "view.html");
	}
}
