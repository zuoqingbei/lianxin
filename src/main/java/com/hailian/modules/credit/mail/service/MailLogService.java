package com.hailian.modules.credit.mail.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.mail.model.MailLogModel;
import com.hailian.modules.credit.mail.model.MailModel;
import com.jfinal.plugin.activerecord.Page;

public class MailLogService {
	public static MailLogService service=new MailLogService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param send_result 
	 * @param mail_log_id 
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<MailLogModel> getPage(Paginator paginator,  String orderBy,String module_id,String mail_log_id, String send_result, BaseProjectController c){
		Page<MailLogModel> page = MailLogModel.dao.getPage(paginator,orderBy,module_id,mail_log_id,send_result,c);
		return page;
	}
	/**
	 * 添加邮件日志
	* @author doushuihai  
	* @date 2018年9月18日下午4:53:38  
	* @TODO
	 */
	public boolean save(Integer userid,String mail_address,String copy_mail_address,String mail_id,String send_result){
		return MailLogModel.dao.save(userid, mail_address, copy_mail_address, mail_id, send_result);
		
	}
	public  MailModel getMailLogById(String paraToInt){
		return MailLogModel.dao.getMailLogById(paraToInt);
	}
}
